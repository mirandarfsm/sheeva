package br.com.sheeva.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sheeva.dao.InstanciaDao;
import br.com.sheeva.dominio.Instancia;
import br.com.sheeva.dominio.Servidor;
import br.com.sheeva.service.InstanciaService;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

@Service("instanciaService")
public class InstanciaServiceImpl implements InstanciaService {

	private static final String ENCODING = "UTF-8";
	private static final int TIMEOUT = 5000;

	@Autowired
	private JSch jsch;

	@Autowired
	private InstanciaDao instanciaDao;

	public void salvar(Instancia instancia) {
		instanciaDao.save(instancia);
	}

	public void remover(Integer id) {
		instanciaDao.remove(id);
	}

	public List<Instancia> listarTodos() {
		return instanciaDao.getAll();
	}

	public Instancia buscarPeloId(Integer id) {
		return instanciaDao.getById(id);
	}

	public Instancia buscarPeloNome(String nome) {
		return instanciaDao.getByNome(nome);
	}

	public String getArquivoConfiguracao(Servidor servidor, Instancia instancia) {
		String command = "cat " + instancia.getDiretorioPrincipal()
				+ instancia.getArquivoConfiguracao();

		Channel channel = getCanal(servidor, command);

		InputStream in = null;

		try {
			in = channel.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			channel.connect();
		} catch (JSchException e) {
			e.printStackTrace();
		}

		try {
			FileUtils.copyInputStreamToFile(
					in,
					new File(".sheeva/" + servidor.getNome() + "/"
							+ instancia.getNome() + "/"
							+ instancia.getArquivoConfiguracao()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return streamToString(in);
	}

	public void setArquivoConfiguracao(Servidor servidor, Instancia instancia,
			String configuracao) {

		gerarBackup(servidor, instancia);

		StringBuilder configuracaoSheeva = new StringBuilder();
		configuracaoSheeva
				.append("### Arquivo gerado por SHEEVA em DD/MM/YYY HH:MM ###\n");
		configuracaoSheeva.append(configuracao);

		String command = "printf '" + configuracaoSheeva + "'>"
				+ instancia.getDiretorioPrincipal()
				+ instancia.getArquivoConfiguracao();

		Channel channel = getCanal(servidor, command);

		OutputStream ou = null;

		try {
			ou = channel.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			channel.connect();
		} catch (JSchException e) {
			e.printStackTrace();
		}
		System.out.println("Arquivo de Configuracao: "
				+ instancia.getArquivoConfiguracao() + " do servidor: "
				+ servidor.getNome() + "Atualizado");
	}

	private Session getSessao(Servidor servidor) {
		Session sessao = null;
		try {
			sessao = jsch.getSession(servidor.getLogin(),
					servidor.getEndereco(), servidor.getPorta());
			sessao.setConfig("StrictHostKeyChecking", "no");
			sessao.setPassword(servidor.getSenha());
			sessao.connect(TIMEOUT);
		} catch (JSchException e) {
			e.printStackTrace();
		}
		return sessao;
	}

	private Channel getCanal(Servidor servidor, String command) {
		Channel channel = null;
		Session sessao = getSessao(servidor);
		try {
			channel = sessao.openChannel("exec");
		} catch (JSchException e) {
			e.printStackTrace();
		}

		try {
			((ChannelExec) channel).setCommand(command.getBytes(ENCODING));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return channel;

	}

	private static String streamToString(InputStream is) {
		Scanner s = new Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

	private void gerarBackup(Servidor servidor, Instancia instancia) {
		Calendar date = new GregorianCalendar();
		File configuracao = new File(".sheeva/" + servidor.getNome() + "/"
				+ instancia.getNome() + "/"
				+ instancia.getArquivoConfiguracao());
		configuracao.renameTo(new File(".sheeva/" + servidor.getNome() + "/"
				+ instancia.getNome() + "/"
				+ instancia.getArquivoConfiguracao() + "." + date));
	}

}

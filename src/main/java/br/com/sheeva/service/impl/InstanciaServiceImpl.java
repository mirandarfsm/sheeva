package br.com.sheeva.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
	private static final int TIMEOUT = 60000;

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

	public String getArquivoConfiguracao(Servidor servidor, Instancia instancia)
			throws IOException, JSchException {
		String command = "cat " + instancia.getDiretorioPrincipal()
				+ instancia.getArquivoConfiguracao();

		Channel channel = getCanal(servidor, command);

		InputStream in = null;

		in = channel.getInputStream();

		channel.connect();

		String result = streamToString(in);

		FileUtils
				.writeStringToFile(
						new File(".sheeva/" + servidor.getNome() + "/"
								+ instancia.getNome() + "/"
								+ instancia.getArquivoConfiguracao()), result,
						ENCODING);

		return result;
	}

	public void setArquivoConfiguracao(Servidor servidor, Instancia instancia,
			String configuracao) throws IOException, JSchException {

		gerarBackup(servidor, instancia);

		Calendar date = Calendar.getInstance();
		StringBuilder configuracaoSheeva = new StringBuilder();
		configuracaoSheeva.append("### Arquivo gerado por SHEEVA em "
				+ date.getTime() + " ###\n");
		configuracaoSheeva.append(configuracao);

		String command = "printf '" + configuracaoSheeva + "'>"
				+ instancia.getDiretorioPrincipal()
				+ instancia.getArquivoConfiguracao();

		Channel channel = getCanal(servidor, command);

		OutputStream ou = null;

		ou = channel.getOutputStream();

		channel.connect();

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
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmm");
		Date date = new Date();
		try {
			File configuracao = new File(".sheeva/" + servidor.getNome() + "/"
					+ instancia.getNome() + "/"
					+ instancia.getArquivoConfiguracao());
			configuracao
					.renameTo(new File(".sheeva/" + servidor.getNome() + "/"
							+ instancia.getNome() + "/"
							+ instancia.getArquivoConfiguracao() + "."
							+ sdf.format(date)));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

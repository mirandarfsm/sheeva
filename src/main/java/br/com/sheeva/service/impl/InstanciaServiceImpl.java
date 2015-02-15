package br.com.sheeva.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sheeva.dao.InstanciaDao;
import br.com.sheeva.dominio.Instancia;
import br.com.sheeva.dominio.Servidor;
import br.com.sheeva.dominio.Sistema;
import br.com.sheeva.service.InstanciaService;
import br.com.sheeva.utils.LinuxUtil;

@Service("instanciaService")
public class InstanciaServiceImpl implements InstanciaService {

	private static final String ENCODING = "UTF-8";

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
			throws IOException {
		String command = "cat " + instancia.getDiretorioPrincipal()
				+ instancia.getArquivoConfiguracao();

		String result = LinuxUtil.executarBashRemoto(command, servidor);
		FileUtils
				.writeStringToFile(
						new File(".sheeva/" + servidor.getNome() + "/"
								+ instancia.getNome() + "/"
								+ instancia.getArquivoConfiguracao()), result,
						ENCODING);
		return result;
	}

	public void setArquivoConfiguracao(Servidor servidor, Instancia instancia,
			String configuracao) throws IOException {

		gerarBackup(servidor, instancia);

		Calendar date = Calendar.getInstance();
		StringBuilder configuracaoSheeva = new StringBuilder();
		configuracaoSheeva.append("### Arquivo gerado por SHEEVA em "
				+ date.getTime() + " ###\n");
		configuracaoSheeva.append(configuracao);

		String command = "printf '" + configuracaoSheeva + "'>"
				+ instancia.getDiretorioPrincipal()
				+ instancia.getArquivoConfiguracao();

		LinuxUtil.executarBashRemoto(command, servidor);

		System.out.println("Arquivo de Configuracao: "
				+ instancia.getArquivoConfiguracao() + " do servidor: "
				+ servidor.getNome() + "Atualizado");
	}

	public void atulizarInstancia(Sistema sistema, Servidor servidor) {
		//TODO: Compactar arquivos de sistemas e enviar para o servidor (tar | scp)
		//TODO: extrair arquivos no servidor remoto (tar)
		//TODO: Executar atualizações até versao do sistema (For)
	}

	private void gerarBackup(Servidor servidor, Instancia instancia) {
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmm");
		Date date = new Date();
		try {
			File configuracao = new File(".sheeva/" + servidor.getNome() + "/"
					+ instancia.getNome() + "/"
					+ instancia.getArquivoConfiguracao());
			configuracao.renameTo(new File(".sheeva/" + servidor.getNome()
					+ "/" + instancia.getNome() + "/"
					+ instancia.getArquivoConfiguracao() + "."
					+ sdf.format(date)));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

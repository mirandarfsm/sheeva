package br.com.sheeva.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sheeva.dao.SistemaDao;
import br.com.sheeva.dao.VersaoDao;
import br.com.sheeva.dominio.Arquivo;
import br.com.sheeva.dominio.Versao;
import br.com.sheeva.service.VersaoService;

@Service("versaoService")
public class VersaoServiceImpl implements VersaoService {

	@Autowired
	private VersaoDao versaoDao;
	
	@Autowired
	private SistemaDao sistemaDao;

	public void salvar(Versao versao) {
		versaoDao.save(versao);
	}

	public void remover(Integer id) {
		versaoDao.remove(id);
	}

	public List<Versao> listarTodos() {
		return versaoDao.getAll();
	}

	public Versao buscarPeloId(Integer id) {
		return versaoDao.getById(id);
	}

	public Versao buscarPelaVersao(String versao) {
		return versaoDao.getByVersion(versao);
	}

	public void salvarArquivos(Versao versao, List<UploadedFile> arquivos)
			throws IOException {
		for (UploadedFile arquivo : arquivos) {
			// salvarArquivo(versao, arquivo);
		}
	}

	public void salvarArquivo(Versao versao, InputStream arquivo, String nome)
			throws IOException {
		File folder = new File(versao.getFolder());
		if (!folder.exists()) {
			folder.mkdirs();
		}
		FileOutputStream out = new FileOutputStream(folder.getPath() + "/"
				+ nome);
		IOUtils.copy(arquivo, out);
	}
	
	public List<Versao> obterVersoesPeloSistema(Integer idInstancia){
		int idSistema = sistemaDao.getIdSystemByInstance(idInstancia);
		List<Versao> versoes = versaoDao.getVersionBySystem(idSistema);
		return versoes;
	}

	public void baixarArquivo(Versao versao, Arquivo arquivo) {
		// TODO implementar servico para download
	}

	public void prepararVersao(Versao antiga, Versao nova) {
		// TODO Criar zip para as versões
	}

}

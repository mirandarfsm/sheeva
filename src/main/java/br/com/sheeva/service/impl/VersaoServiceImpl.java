package br.com.sheeva.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sheeva.dao.VersaoDao;
import br.com.sheeva.dominio.Arquivo;
import br.com.sheeva.dominio.Versao;
import br.com.sheeva.service.VersaoService;

@Service("versaoService")
public class VersaoServiceImpl implements VersaoService {

	@Autowired
	private VersaoDao versaoDao;

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

	public void salvarArquivos(Versao versao, List<Arquivo> arquivos)
			throws IOException {
		File folder = new File(versao.getArquivos());
		if (!folder.exists()) {
			folder.mkdirs();
		}

		for (Arquivo arquivo : arquivos) {
			FileOutputStream out = new FileOutputStream(folder.getPath()
					+ arquivo.getNome());
			IOUtils.copy(arquivo.getArquivo(), out);
		}

	}

}

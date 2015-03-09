package br.com.sheeva.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.primefaces.model.UploadedFile;

import br.com.sheeva.dominio.Versao;

public interface VersaoService {

	public void salvar(Versao versao);

	public void remover(Integer id);

	public List<Versao> listarTodos();

	public Versao buscarPeloId(Integer id);

	public Versao buscarPelaVersao(String versao);

	void salvarArquivo(Versao versao, InputStream arquivo, String nome)
			throws IOException;

	void salvarArquivos(Versao versao, List<UploadedFile> arquivos)
			throws IOException;

	void prepararVersao(Versao antiga, Versao nova);
}

package br.com.sheeva.service;

import java.io.IOException;
import java.util.List;

import br.com.sheeva.dominio.Arquivo;
import br.com.sheeva.dominio.Versao;

public interface VersaoService {

	public void salvar(Versao versao);

	public void remover(Integer id);

	public List<Versao> listarTodos();

	public Versao buscarPeloId(Integer id);

	public Versao buscarPelaVersao(String versao);


	void salvarArquivos(Versao versao, List<Arquivo> arquivos) throws IOException;
	
	void prepararVersao(Versao antiga, Versao nova);
}

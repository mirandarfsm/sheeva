package br.com.sheeva.service;

import java.util.List;

import br.com.sheeva.dominio.Instancia;
import br.com.sheeva.dominio.Servidor;
import br.com.sheeva.dominio.Versao;

public interface ServidorService {

	public void salvar(Servidor servidor);

	public void remover(Integer id);

	public List<Servidor> listarTodos();

	public Servidor buscarPeloId(Integer id);
	
	void atualizarInstancia(Servidor servidor, Versao versao);
	
	void alterarArquivoConfiguracao(Servidor servidor, Versao versao, String arquivoConfiguracao);
	
	String pegarArquivoConfiguracao(Servidor servidor, Versao versao);
	
	void reiniciarServidorWeb(Servidor servidor, boolean work);
	
	void reiniciarAplicacao(Servidor servidor,Instancia instancia);
	
	void pegarConfiguracaoServidor(Servidor servidor);
	

}

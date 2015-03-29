package br.com.sheeva.service;

import br.com.sheeva.dominio.Instancia;
import br.com.sheeva.dominio.Servidor;
import br.com.sheeva.dominio.Versao;

public interface AtualizacaoService {

	void atualizarInstancias(Servidor servidor, Versao versao);

	void atualizarInstancia(Servidor servidor, Versao versao,Instancia instancia);
	
	void atualizarVersaoDaInstancia(Servidor servidor, Versao versao,Instancia instancia);
	
	void alterarArquivoConfiguracao(Servidor servidor, Versao versao, String arquivoConfiguracao);
	
	String pegarArquivoConfiguracao(Servidor servidor, Versao versao);
	
}

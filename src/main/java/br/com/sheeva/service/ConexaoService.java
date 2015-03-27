package br.com.sheeva.service;

import java.util.Map;

import br.com.sheeva.dominio.Servidor;

public interface ConexaoService<T> {
	
	public T abrirConexao(Servidor servidor);
	
	public Map<String, String> executarComandoRemoto(Servidor servidor, String comando);
	
	public Map<String, String> enviarArquivos(Servidor servidor, String diretorio);
	
	public Map<String, String> enviarArquivo(Servidor servidor, String arquivo);

}

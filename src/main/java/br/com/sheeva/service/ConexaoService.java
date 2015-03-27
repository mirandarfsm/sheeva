package br.com.sheeva.service;

import java.util.Map;

import br.com.sheeva.dominio.Servidor;

public interface ConexaoService<T> {
	
	public T abrirConexao(Servidor servidor);
	
	public Map<String, String> executarComando(Servidor servidor, String comando);

}

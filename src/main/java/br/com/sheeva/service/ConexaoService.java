package br.com.sheeva.service;

import br.com.sheeva.dominio.Servidor;

public interface ConexaoService<T> {
	
	public T abrirConexao(Servidor servidor);

}

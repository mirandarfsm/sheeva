package br.com.sheeva.service;

import java.util.List;

import br.com.sheeva.dominio.Servidor;

public interface ServidorService {

	public void salvar(Servidor servidor);
	
	public void remover(Integer id);
	
	public List<Servidor> listarTodos();
	
	public Servidor buscarPeloId(Integer id);
	
	public String getConfiguracaoServidor();
	
	public String getLog();
	
	public void gerenciarServico();
	
}

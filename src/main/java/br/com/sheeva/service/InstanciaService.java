package br.com.sheeva.service;

import java.io.IOException;
import java.util.List;

import com.jcraft.jsch.JSchException;

import br.com.sheeva.dominio.Instancia;
import br.com.sheeva.dominio.Servidor;

public interface InstanciaService {
	public void salvar(Instancia instancia);

	public void remover(Integer id);

	public List<Instancia> listarTodos();

	public Instancia buscarPeloId(Integer id);

	public Instancia buscarPeloNome(String nome);
	
	public String getArquivoConfiguracao(Servidor servidor,Instancia instancia) throws IOException, JSchException;

	public void setArquivoConfiguracao(Servidor servidor, Instancia instancia,String configuracao)throws IOException, JSchException;
}

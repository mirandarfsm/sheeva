package br.com.sheeva.service;

import java.util.Map;

import br.com.sheeva.conexao.ConexaoSocket;
import br.com.sheeva.dominio.Servidor;
import br.com.sheeva.dto.PacoteAtualizacaoDTO;

public interface ConexaoService<T> {
	
	public T abrirConexao(Servidor servidor);
	
	public Map<String, String> executarComandoRemoto(Servidor servidor, String comando);
	
	public Map<String, String> enviarArquivos(Servidor servidor, String diretorio);
	
	public Map<String, String> enviarArquivo(Servidor servidor, String arquivo);
	
	public void acompanharAtualizacao(ConexaoSocket conexaoSocket, PacoteAtualizacaoDTO pacoteAtualizacaoDTO);

}

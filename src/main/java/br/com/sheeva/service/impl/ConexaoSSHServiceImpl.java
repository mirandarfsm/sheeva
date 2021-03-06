package br.com.sheeva.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.sheeva.conexao.ConexaoSocket;
import br.com.sheeva.dominio.Servidor;
import br.com.sheeva.dto.PacoteAtualizacaoDTO;
import br.com.sheeva.service.ConexaoService;
import br.com.sheeva.utils.LinuxUtil;

import com.jcraft.jsch.Session;

@Service("conexaoSSHService")
public class ConexaoSSHServiceImpl implements ConexaoService<Session>{
	
	private final String NAO_EXECUTADO = "O comando não foi executado.";

	public Map<String, String> executarComandoRemoto(Servidor servidor, String comando) {
		Map<String, String> saida = new HashMap<String, String>();
		try {
			saida = LinuxUtil.executarComandoRemoto(servidor, comando);
		} catch (Exception e) {
			saida.put("NAO_EXECUTADO", NAO_EXECUTADO);
			e.printStackTrace();
		}
		return saida;
	}

	public Map<String, String> enviarArquivos(Servidor servidor, String diretorio) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, String> enviarArquivo(Servidor servidor, String arquivo) {
		Map<String, String> saida = new HashMap<String, String>();
		try {
			LinuxUtil.enviarArquivo(servidor, arquivo);
		} catch (Exception e) {
			saida.put("NAO_EXECUTADO", NAO_EXECUTADO);
			e.printStackTrace();
		}
		return saida;
	}

	public Session abrirConexao(Servidor servidor) {
		// TODO Auto-generated method stub
		return null;
	}

	public void acompanharAtualizacao(PacoteAtualizacaoDTO pacoteAtualizacaoDTO) {
		// TODO Auto-generated method stub
		
	}

	public void enviarArquivosJson(PacoteAtualizacaoDTO pacoteAtualizacaoDTO) {
		// TODO Auto-generated method stub
		
	}


}

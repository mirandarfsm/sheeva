package br.com.sheeva.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.sheeva.dominio.Servidor;
import br.com.sheeva.service.ConexaoService;
import br.com.sheeva.utils.LinuxUtil;

import com.jcraft.jsch.Session;

@Service("conexaoSSHService")
public class ConexaoSSHServiceImpl implements ConexaoService<Session>{

	public Session abrirConexao(Servidor servidor) {
		

	    return null;
	}

	public Map<String, String> executarComando(Servidor servidor, String comando) {
		Map<String, String> saida = LinuxUtil.executarComandoRemoto(servidor, comando);
		return saida;
	}

}

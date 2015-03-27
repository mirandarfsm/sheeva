package br.com.sheeva.service.impl;

import org.springframework.stereotype.Service;

import br.com.sheeva.dominio.Servidor;
import br.com.sheeva.service.ConexaoService;

import com.jcraft.jsch.Session;

@Service("conexaoSSHService")
public class ConexaoSSHServiceImpl implements ConexaoService<Session>{

	public Session abrirConexao(Servidor servidor) {
		

	    return null;
	}

}

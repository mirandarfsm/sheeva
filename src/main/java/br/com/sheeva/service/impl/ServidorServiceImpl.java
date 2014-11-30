package br.com.sheeva.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sheeva.dao.ServidorDao;
import br.com.sheeva.dominio.Servidor;
import br.com.sheeva.service.ServidorService;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

@Service("servidorService")
public class ServidorServiceImpl implements ServidorService {

	private static final int TIMEOUT = 5000;
	
	@Autowired
	private JSch jsch;
	
	@Autowired
	private ServidorDao servidorDao;

	public void salvar(Servidor servidor) {
		servidorDao.save(servidor);
	}

	public void remover(Integer id) {
		servidorDao.remove(id);
	}

	public List<Servidor> listarTodos() {
		return servidorDao.getAll();
	}

	public Servidor buscarPeloId(Integer id) {
		return servidorDao.getById(id);
	}

	public Channel getCanalDeComunicacao(Servidor servidor) {
		Session sessao = getSessao(servidor.getEndereco(), servidor.getLogin(), servidor.getPorta(), servidor.getSenha());
		Channel canal = abrirCanal(sessao);
		return canal;
	}
	
	private Session getSessao(String endereco, String usuario, Integer porta, String senha) {
		Session sessao = null;
		try {
			sessao = jsch.getSession(usuario, endereco, porta);
			sessao.setConfig("StrictHostKeyChecking", "no");
			sessao.setPassword(senha);
			sessao.connect(TIMEOUT);
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sessao;
	}
	
	private Channel abrirCanal(Session sessao){
		Channel canal = null;
		try {
			canal = sessao.openChannel("shell");
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return canal;
	}
}

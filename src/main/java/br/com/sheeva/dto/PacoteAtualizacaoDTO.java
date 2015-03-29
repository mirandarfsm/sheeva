package br.com.sheeva.dto;

import br.com.sheeva.dominio.Instancia;
import br.com.sheeva.dominio.Servidor;
import br.com.sheeva.dominio.Sistema;
import br.com.sheeva.dominio.Versao;
import br.com.sheeva.utils.PainelAtualizacao;

public class PacoteAtualizacaoDTO {

	private Servidor servidor;
	private Versao versao;
	private Sistema sistema;
	private PainelAtualizacao painelAtualizacao;
	private Instancia instancia;

	private PacoteAtualizacaoDTO() {

	}

	public PacoteAtualizacaoDTO(Servidor servidor, Versao versao, Sistema sistema, PainelAtualizacao painelAtualizacao, Instancia instancia) {
		super();
		this.servidor = servidor;
		this.versao = versao;
		this.sistema = sistema;
		this.instancia = instancia;
		this.painelAtualizacao = painelAtualizacao;
	}

	public Servidor getServidor() {
		return servidor;
	}

	public Versao getVersao() {
		return versao;
	}

	public Sistema getSistema() {
		return sistema;
	}

	public Instancia getInstancia() {
		return instancia;
	}

	public PainelAtualizacao getPainelAtualizacao() {
		return painelAtualizacao;
	}

}

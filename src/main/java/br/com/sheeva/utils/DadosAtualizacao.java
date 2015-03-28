package br.com.sheeva.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import org.springframework.stereotype.Component;

import br.com.sheeva.enu.EstadoAtualizacao;

@Component("dadosAtualizacao")
public class DadosAtualizacao extends Observable {


	private EstadoAtualizacao estadoAtualizacao;
	private List<String> listaDadosAtualizacao;
	
	public DadosAtualizacao() {
		super();
		this.estadoAtualizacao = EstadoAtualizacao.NAO_INICIADA;
		this.listaDadosAtualizacao = new ArrayList<String>();
	}

	public EstadoAtualizacao getEstadoAtualizacao() {
		return estadoAtualizacao;
	}

	public void setEstadoAtualizacao(EstadoAtualizacao estadoAtualizacao) {
		this.estadoAtualizacao = estadoAtualizacao;
		setChanged();
		notifyObservers();
	}

	public List<String> getListaDadosAtualizacao() {
		return listaDadosAtualizacao;
	}

	public void setListaDadosAtualizacao(List<String> listaDadosAtualizacao) {
		this.listaDadosAtualizacao = listaDadosAtualizacao;
		setChanged();
		notifyObservers();
	}
	
}

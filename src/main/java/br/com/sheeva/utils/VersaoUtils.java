package br.com.sheeva.utils;

import java.util.ArrayList;
import java.util.List;

import br.com.sheeva.dominio.Versao;

public class VersaoUtils {
	
	public static boolean isVersaoFormatoValido(String versao) {
		String stringsVersao[] = versao.trim().split("\\.");
		if (stringsVersao.length != 4) {
			return false;
		}
		for (String string : stringsVersao) {
			if (Integer.valueOf(string) < 0) {
				return false;
			} 
		}
		return true;
	}
	
	public static List<Versao> obterVersoesDisponveisAtualizacao(List<Versao> versoes, Versao versao) {
		versoes = removerVersoesAntigas(versoes, versao);
		versoes = removerRelesesAntigas(versoes, versao);
		versoes = removerSprintsAntigas(versoes, versao);
		versoes = removerBugsAntigas(versoes, versao);
		return versoes;
	}
	
	private static List<Versao> removerVersoesAntigas(List<Versao> versoes, Versao versao) {
		List<Versao> versoesDisponveisAtualizacao = new ArrayList<Versao>();
		for (Versao versaoVerificar : versoes) {
			if (versaoVerificar.getVersion() >= versao.getVersion()) {
				versoesDisponveisAtualizacao.add(versaoVerificar);
			}
		}
		return versoesDisponveisAtualizacao;
	}
	
	private static List<Versao> removerRelesesAntigas(List<Versao> versoes, Versao versao) {
		List<Versao> versoesDisponveisAtualizacao = new ArrayList<Versao>();
		for (Versao versaoVerificar : versoes) {
			if (!((versaoVerificar.getVersion() == versao.getVersion()) && (versaoVerificar.getRelease() < versao.getRelease()))) {
				versoesDisponveisAtualizacao.add(versaoVerificar);
			}
		}
		return versoesDisponveisAtualizacao;
	}
	
	private static List<Versao> removerSprintsAntigas(List<Versao> versoes, Versao versao) {
		List<Versao> versoesDisponveisAtualizacao = new ArrayList<Versao>();
		for (Versao versaoVerificar : versoes) {
			if (!((versaoVerificar.getVersion() == versao.getVersion()) && 
				  (versaoVerificar.getRelease() == versao.getRelease()) && 
				  (versaoVerificar.getSprint() < versao.getSprint()))) {
				versoesDisponveisAtualizacao.add(versaoVerificar);
			}
		}
		return versoesDisponveisAtualizacao;
	}
	
	private static List<Versao> removerBugsAntigas(List<Versao> versoes, Versao versao) {
		List<Versao> versoesDisponveisAtualizacao = new ArrayList<Versao>();
		for (Versao versaoVerificar : versoes) {
			if (!((versaoVerificar.getVersion() == versao.getVersion()) && 
				  (versaoVerificar.getRelease() == versao.getRelease()) && 
				  (versaoVerificar.getSprint() == versao.getSprint()) && 
				  (versaoVerificar.getBug() <= versao.getBug()))) {
				versoesDisponveisAtualizacao.add(versaoVerificar);
			}
		}
		return versoesDisponveisAtualizacao;
	}
	
}

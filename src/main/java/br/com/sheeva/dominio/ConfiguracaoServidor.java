package br.com.sheeva.dominio;

import java.text.NumberFormat;
import java.util.Locale;

public class ConfiguracaoServidor {

	String sistemaOperacional;
	String arquitetura;
	Integer numeroProcessadores;
	Long memoriaFisicaTotal;
	Long memoriaSwapTotal;

	public String getSistemaOperacional() {
		return sistemaOperacional;
	}

	public void setSistemaOperacional(String sistemaOperacional) {
		this.sistemaOperacional = sistemaOperacional;
	}

	public String getArquitetura() {
		return arquitetura;
	}

	public void setArquitetura(String arquitetura) {
		this.arquitetura = arquitetura;
	}

	public Integer getNumeroProcessadores() {
		return numeroProcessadores;
	}

	public void setNumeroProcessadores(Integer numeroProcessadores) {
		this.numeroProcessadores = numeroProcessadores;
	}

	public Long getMemoriaFisicaTotal() {
		return memoriaFisicaTotal;
	}

	public void setMemoriaFisicaTotal(Long memoriaFisicaTotal) {
		this.memoriaFisicaTotal = memoriaFisicaTotal;
	}

	public Long getMemoriaSwapTotal() {
		return memoriaSwapTotal;
	}

	public void setMemoriaSwapTotal(Long memoriaSwapTotal) {
		this.memoriaSwapTotal = memoriaSwapTotal;
	}
	
	public String getMemoriaFisicaTotalFormatadaKbytes() {
		return getMemoriaKbytes(memoriaFisicaTotal) + " Kbytes";
	}
	
	public String getMemoriaSwapTotalFormatadaKbytes() {
		return getMemoriaKbytes(memoriaSwapTotal) + " Kbytes";
	}
	
	private String getMemoriaKbytes(Long valorMemoria){
		Long valorMemoriaKbytes = valorMemoria /1024;
		NumberFormat memoriaKbytesFormatada = NumberFormat.getIntegerInstance(new Locale("pt", "BR"));  
		return memoriaKbytesFormatada.format(valorMemoriaKbytes);
	}

}

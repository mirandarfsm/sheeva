package br.com.sheeva.dominio;

import java.text.NumberFormat;
import java.util.Locale;

public class ConfiguracaoServidor {

	String sistemaOperacional;
	String arquitetura;
	Integer numeroProcessadores;
	Long memoriaFisicaTotal;
	Long memoriaFisicaLivre;
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
	
	public Long getMemoriaFisicaLivre() {
		return memoriaFisicaLivre;
	}

	public void setMemoriaFisicaLivre(Long memoriaFisicaLivre) {
		this.memoriaFisicaLivre = memoriaFisicaLivre;
	}

	public Long getMemoriaUtilizada() {
		return memoriaFisicaTotal - memoriaFisicaLivre;
	}
	
	public String getMemoriaUtilizadaFormatadaKbytes() {
		return getMemoriaKbytesString(getMemoriaUtilizada()) + " Kbytes";
	}
	
	public String getMemoriaFisicaTotalFormatadaKbytes() {
		return getMemoriaKbytesString(memoriaFisicaTotal) + " Kbytes";
	}
	
	public String getMemoriaSwapTotalFormatadaKbytes() {
		return getMemoriaKbytesString(memoriaSwapTotal) + " Kbytes";
	}
	
	private String getMemoriaKbytesString(Long valorMemoria){
		valorMemoria = valorMemoria /1024;
		NumberFormat memoriaKbytesFormatada = NumberFormat.getIntegerInstance(new Locale("pt", "BR"));  
		return memoriaKbytesFormatada.format(valorMemoria);
	}
	
	private Long getMemoriaMbytes(Long valorMemoria){
		valorMemoria = valorMemoria /1024;
		valorMemoria = valorMemoria /1024;
		return valorMemoria;
	}

	public Long getMemoriaUtilizadaMbytes(){
		return getMemoriaMbytes(getMemoriaUtilizada());
	}
}

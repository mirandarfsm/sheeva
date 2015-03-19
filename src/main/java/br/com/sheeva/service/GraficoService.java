package br.com.sheeva.service;

import org.primefaces.model.chart.LineChartModel;

import br.com.sheeva.dominio.Servidor;

public interface GraficoService {
	
	public LineChartModel obterDadosGraficoMemoria(Servidor servidor);

}

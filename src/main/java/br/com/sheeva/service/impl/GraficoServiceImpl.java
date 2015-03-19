package br.com.sheeva.service.impl;

import org.primefaces.model.chart.LineChartModel;
import org.springframework.stereotype.Service;

import br.com.sheeva.dominio.Servidor;
import br.com.sheeva.service.GraficoService;

@Service("graficoService")
public class GraficoServiceImpl implements GraficoService {

	
	public LineChartModel obterDadosGraficoMemoria(Servidor servidor) {
		return null;
	}

}

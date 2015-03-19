package br.com.sheeva.grafico;

import java.io.Serializable;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("graficoLinhaBean")
@Scope(value = "view")
public class GraficoLinhaBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private LineChartModel model;
	private int contador1 = 2;
	private int contador2 = 3;
	LineChartSeries series1 = new LineChartSeries();

	@PostConstruct
	public void init() {
		criarGrafico();
	}
	
	public void criarGrafico() {
		model = new LineChartModel();
		Random r = new Random();  
		series1.setLabel("Series 1");
		contador1 = r.nextInt(10); 
		contador2 = r.nextInt(10); 
		series1.set(contador1, contador2);
		
		LineChartSeries series2 = new LineChartSeries();
		series2.setLabel("Series 2");
		series2.set(1, 6);
		series2.set(2, 3);
		series2.set(3, 2);
		series2.set(4, 7);
		series2.set(5, 9);
		model.addSeries(series1);
		model.addSeries(series2);
		model.setTitle("Linear Chart");
		model.setLegendPosition("e");
		Axis yAxis = model.getAxis(AxisType.Y);
		yAxis.setMin(0);
		yAxis.setMax(10);
	}

	public LineChartModel getModel() {
		return model;
	}
}
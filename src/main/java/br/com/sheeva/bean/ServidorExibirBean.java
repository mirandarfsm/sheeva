package br.com.sheeva.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.sheeva.dominio.ConfiguracaoServidor;
import br.com.sheeva.dominio.Instancia;
import br.com.sheeva.dominio.Servidor;
import br.com.sheeva.dominio.Versao;
import br.com.sheeva.service.GraficoService;
import br.com.sheeva.service.InstanciaService;
import br.com.sheeva.service.ServidorService;
import br.com.sheeva.service.VersaoService;
import br.com.sheeva.utils.ManagedBeanUtils;
import br.com.sheeva.utils.VersaoUtils;

@Service("servidorExibirBean")
@Scope(value = "view")
public class ServidorExibirBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Servidor servidor;
	private Instancia instancia;
	private Versao versao;
	private List<Versao> versoes;
	private ConfiguracaoServidor configuracaoServidor;
	private CartesianChartModel dadosGrafico;
	LineChartSeries series = new LineChartSeries();
	private Object eixoX = 2;
	private int eixoY = 3;

	@Autowired
	private ServidorService servidorService;
	@Autowired
	private InstanciaService instanciaService;
	@Autowired
	private VersaoService versaoService;
	@Autowired
	private GraficoService graficoService;

	@PostConstruct
	public void init() {
		servidor = obterServidor();
		criarGrafico();
		versoes = versaoService.listarTodos();
	}

	public Servidor obterServidor() {
		String id = ManagedBeanUtils.obterParametroRequest("id");
		return "novo".equals(id) ? new Servidor() : servidorService.buscarPeloId(Integer.valueOf(id));
	}

	public void atualizar() {
		instancia = instanciaService.buscarPeloId(instancia.getId());
		versao = versaoService.buscarPeloId(versao.getId());
		servidorService.atualizarInstancia(servidor, versao, instancia);
	}

	public void abrirTerminal() {
		ManagedBeanUtils.redirecionarUrlExterna(obterUrlSsh());
	}

	public String obterUrlSsh() {
		StringBuffer stringBuffer = new StringBuffer();
		return stringBuffer.append("ssh://").append(servidor.getLogin()).append(":").append(servidor.getSenha()).append("@")
				.append(servidor.getEndereco()).append(":").append(servidor.getPorta()).toString();
	}

	public Servidor getServidor() {
		return servidor;
	}

	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}

	public Instancia getInstancia() {
		return instancia;
	}

	public void setInstancia(Instancia instancia) {
		this.instancia = instancia;
	}

	public Versao getVersao() {
		return versao;
	}

	public void setVersao(Versao versao) {
		this.versao = versao;
	}

	public List<Versao> getVersoes() {
		return versoes;
	}

	public void setVersoes(List<Versao> versoes) {
		this.versoes = versoes;
	}

	public ConfiguracaoServidor getConfiguracaoServidor() {
		return configuracaoServidor;
	}

	public void setConfiguracaoServidor(ConfiguracaoServidor configuracaoServidor) {
		this.configuracaoServidor = configuracaoServidor;
	}

	public CartesianChartModel getModel() {
		criarGrafico();
		return dadosGrafico;
	}

	public void setModel(CartesianChartModel model) {
		this.dadosGrafico = model;
	}

	public LineChartSeries getSeries() {
		return series;
	}

	public void setSeries(LineChartSeries series) {
		this.series = series;
	}

	public boolean isPossuiConexaoMonitoramento() {
		configuracaoServidor = servidorService.obterConfiguracaoServidor(servidor);
		return configuracaoServidor == null ? false : true;
	}

	public boolean isServidorPossuiInstancia(){
		return !servidor.getInstancias().isEmpty();
	}
	
	public List<Versao> obterVersoesPeloSistema(Instancia instancia) {
		versoes = versaoService.obterVersoesPeloSistema(instancia.getId());
		Versao versaoDaInstancia = instanciaService.buscarPeloId(instancia.getId()).getVersao();
		versoes = VersaoUtils.obterVersoesDisponveisAtualizacao(versoes, versaoDaInstancia);
		return versoes;
	}
	
	public void criarGrafico() {
		dadosGrafico = new LineChartModel();
		Random r = new Random();  
		series.setLabel("Series 1");
		configuracaoServidor = servidorService.obterConfiguracaoServidor(servidor);
		eixoY = configuracaoServidor.getMemoriaUtilizadaMbytes().intValue();
		eixoX = getHora();
		series.set(eixoX, eixoY);
		
		dadosGrafico.addSeries(series);
		dadosGrafico.setTitle("Gráfico da Memória");
		Axis yAxis = dadosGrafico.getAxis(AxisType.Y);
		yAxis.setLabel("Memória (Mbytes)");
		yAxis.setMin(0);
		
        DateAxis xAxis = new DateAxis("Hora");
        xAxis.setLabel("Hora");
        xAxis.setTickAngle(-50);
        xAxis.setMax(getHoraMaxima());
        xAxis.setTickFormat("%I:%M:%S");
        
        dadosGrafico.getAxes().put(AxisType.X, xAxis);
        
	}
	
	private String getHora() {
		String hora = "hh:mm:ss";
		SimpleDateFormat formata = new SimpleDateFormat(hora);  
		return formata.format(new Date());                            
	}
	
	private String getHoraMaxima() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.SECOND, 3);
		String hora = "hh:mm:ss";
		SimpleDateFormat formata = new SimpleDateFormat(hora);  
		return formata.format(cal.getTime());                            
	}
}

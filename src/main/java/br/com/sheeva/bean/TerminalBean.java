package br.com.sheeva.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.fusesource.jansi.AnsiConsole;
import org.fusesource.jansi.AnsiString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.sheeva.dominio.Servidor;
import br.com.sheeva.service.ServidorService;

import com.jcraft.jsch.Channel;

@Service("terminalBean")
@Scope(value = "session")
public class TerminalBean {
	
	@Autowired
	private ServidorService servidorService;
	
	@Autowired
	private ServidorBean servidorBean;
	
	private BufferedReader fromChannel;
	private PrintWriter toChannel;
	private Channel channel;
	private Servidor servidor;
	private final String ENCODING = "UTF-8";

	@PostConstruct
	public void init(){
		servidor = servidorBean.getServidor();
		try {
			channel = servidorService.getCanalDeComunicacao(servidor);
			InputStream inStream = channel.getInputStream();
			fromChannel = new BufferedReader(new InputStreamReader(inStream,ENCODING));
			OutputStream outStream = channel.getOutputStream();
			toChannel = new PrintWriter(new OutputStreamWriter(outStream,ENCODING));
			channel.connect();
			
			StringBuilder result = new StringBuilder();
			boolean stop = false;
			while (!stop) {
				char c = (char) fromChannel.read();
				result.append(c);
				System.out.print(c);
				if (result.toString().endsWith("$")) {
					System.out.print("<<<");
					stop = true;
				}
			}
		} catch (Exception e) {

		}
	}
	
	public String handleCommand(String command, String[] params) throws IOException {
		command = command + " " + StringUtils.join(params, " ");
		toChannel.println(command);
		toChannel.flush();
		StringBuilder result = new StringBuilder();
		AnsiConsole.systemInstall();
		while (true) {
			char c = (char) fromChannel.read();
			result.append(c);
			if (c == '$') {
				AnsiString as = new AnsiString(result.toString());
				String s = as.getPlain().toString();
				s = s.replaceAll("\n", "<br>");
				AnsiConsole.systemUninstall();
				return s;
			}
		}
	}

	public Servidor getServidor() {
		return servidor;
	}

	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}
	
}

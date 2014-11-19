package br.com.sheeva.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Mensagem {
	
	public static void msgInformacao(String msg){
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						msg, null));
	}
	
	public static void msgAlerta(String msg){
	FacesContext.getCurrentInstance().addMessage(
			null,
			new FacesMessage(FacesMessage.SEVERITY_WARN,
					msg, null));
	}
	
	
	public static void msgErro(String msg){
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR,
						msg, null));
	}
	
	public static void msgErroFatal(String msg){
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_FATAL,
						msg, null));
	}

}

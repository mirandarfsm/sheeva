package br.com.sheeva.utils;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

public class ManagedBeanUtils {
	
	private static final String PF_DIALOG_FUNCTION = "PF(':nome')";
	
	public static void redirecionar(String url){
		try {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect(context.encodeResourceURL(context.getRequestContextPath() + url));
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public static void redirecionarUrlExterna(String url){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public static void showDialog(String nome){
		String function = PF_DIALOG_FUNCTION + ".show()";
		RequestContext.getCurrentInstance().execute(function.replace(":nome", nome));
	}
	
	public static void hideDialog(String nome){
		String function = PF_DIALOG_FUNCTION + ".hide()";
		RequestContext.getCurrentInstance().execute(function.replace(":nome", nome));
	}
	
}

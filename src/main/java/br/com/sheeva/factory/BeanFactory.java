package br.com.sheeva.factory;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class BeanFactory {
	
	private static BeanFactory beanFactory;
	private WebApplicationContext context = WebApplicationContextUtils
			.getRequiredWebApplicationContext((ServletContext) FacesContext
					.getCurrentInstance().getExternalContext().getContext());
	
	public static BeanFactory getInstance(){
		if (beanFactory == null) {
			beanFactory = new BeanFactory();
		}
		return beanFactory;
	}
	
	public Object getBean(String bean){
		return context.getBean(bean);
	}
	
	

}

package br.com.sheeva.utils;

import br.com.sheeva.factory.BeanFactory;


public class LayoutIndexManager {

	public static void atualizarIndice(int index){
		LayoutBean layoutBean = (LayoutBean) BeanFactory.getInstance().getBean("layoutBean");
		layoutBean.setSelectedIndex(index);
	}
}

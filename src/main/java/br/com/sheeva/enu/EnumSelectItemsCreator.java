package br.com.sheeva.enu;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.beanutils.BeanUtils;


public class EnumSelectItemsCreator {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static SelectItem[] items(Class enumClass, String labelProperty){
		
		try {
			List<SelectItem> lista = new ArrayList<SelectItem>();
	
			Enum[] enums = (Enum[]) enumClass.getMethod("values").invoke(null);
			
			for(Enum enun : enums){
				lista.add(new SelectItem(enun, BeanUtils.getProperty(enun, labelProperty)));
			}
			
			return lista.toArray(new SelectItem[0]);
			
		}catch(Exception e){
			e.printStackTrace();
			return new SelectItem[0];
		}
		
		
	}
}

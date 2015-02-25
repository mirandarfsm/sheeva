package br.com.sheeva.converter;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import br.com.sheeva.enu.TipoInstancia;

@FacesConverter(value="tipoInstanciaConverter")
public class TipoInstanciaConverter extends EnumConverter {

	public TipoInstanciaConverter(){
		super(TipoInstancia.class);
	}

}

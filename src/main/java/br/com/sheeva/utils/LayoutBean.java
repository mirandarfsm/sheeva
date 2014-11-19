package br.com.sheeva.utils;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("layoutBean")
@Scope("session")
public class LayoutBean implements Serializable{
	private static final long serialVersionUID = 6103223083008127178L;
	
	private boolean topCollapsed = false;
	private boolean menuCollapsed = false;
	private int selectedIndex;
	
	public void setTopCollapsed(boolean topCollapsed) {
		this.topCollapsed = topCollapsed;
	}

	public boolean isTopCollapsed() {
		return topCollapsed;
	}

	public void setMenuCollapsed(boolean menuCollapsed) {
		this.menuCollapsed = menuCollapsed;
	}

	public boolean isMenuCollapsed() {
		return menuCollapsed;
	}

	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	public int getSelectedIndex() {
		FacesContext.getCurrentInstance().getAttributes();
		return selectedIndex;
	}
}

package tr.com.agem.arya.interpreter.components.base;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.zkoss.zul.Div;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;

import tr.com.agem.arya.metadata.interpreter.IAryaComponent;

public class AryaMain {

	private Set<IAryaComponent> aryaWindowComponents;
	private ArrayList<IAryaComponent> aryaNavBarComponents;
	private Div componentContainer;
	private Div menuContainer;
	private Label message;
	private Include login;

	public AryaMain(Div componentContainer, Div menuContainer, Include login, Label message) {
		
		super();
		this.menuContainer = menuContainer;
		this.componentContainer = componentContainer;
		this.login = login;
		this.aryaWindowComponents = new HashSet<IAryaComponent>();
		this.aryaNavBarComponents = new ArrayList<IAryaComponent>();
		this.message = message;
	}

	public Div getComponentContainer() {
		return componentContainer;
	}

	public void setComponentContainer(Div componentContainer) {
		this.componentContainer = componentContainer;
	}

	public Div getMenuContainer() {
		return menuContainer;
	}

	public void setMenuContainer(Div menuContainer) {
		this.menuContainer = menuContainer;
	}

	public Include getLogin() {
		return login;
	}

	public void setLogin(Include login) {
		this.login = login;
	}

	public Label getMessage() {
		return message;
	}

	public void setMessage(Label message) {
		this.message = message;
	}

	public Set<IAryaComponent> getAryaWindowComponents() {
		return aryaWindowComponents;
	}

	public void setAryaWindowComponents(Set<IAryaComponent> aryaWindowComponents) {
		this.aryaWindowComponents = aryaWindowComponents;
	}

	public ArrayList<IAryaComponent> getAryaNavBarComponents() {
		return aryaNavBarComponents;
	}

	public void setAryaNavBarComponents(ArrayList<IAryaComponent> aryaNavBarComponents) {
		this.aryaNavBarComponents = aryaNavBarComponents;
	}

}

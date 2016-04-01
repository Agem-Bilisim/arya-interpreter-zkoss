package tr.com.agem.arya.interpreter.utils;

import java.awt.Button;
import java.io.IOException;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.tools.shell.Global;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Textbox;

import tr.com.agem.arya.interpreter.script.ElementFunctions;

@SuppressWarnings("rawtypes")
public class LoginController extends GenericForwardComposer {

	private static final long serialVersionUID = 8866650311533378984L;
	
	private Textbox username;
	private Textbox password;
	private Button loginButton;

	public LoginController() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		init();
	}

	private void init() throws IOException {
		
		if(username==null)
			username=new Textbox();
				
		if(password==null)
			password=new Textbox();
		
		if(loginButton==null)
			loginButton=new Button();
		 
	}
	
	public void onClick$loginButton() {
	    // called when onClick is received on the component of id loginButton.
		
		Context context = ContextFactory.getGlobal().enterContext();
		context.setOptimizationLevel(-1);
		
		Scriptable scope = new Global();
		((Global) scope).init(context);
		
		ElementFunctions element = new ElementFunctions(context, scope, BaseController.getMain());
		
		String jsonString = "{\"username\":\"admin\",\"password\":\"a9em@5466\"}";
				
		element.post("login","LOGIN", jsonString, "ASYA", null, null);
				
	}

	public Textbox getUsername() {
		return username;
	}

	public void setUsername(Textbox username) {
		this.username = username;
	}

	public Textbox getPassword() {
		return password;
	}

	public void setPassword(Textbox password) {
		this.password = password;
	}

	public Button getLoginButton() {
		return loginButton;
	}

	public void setLoginButton(Button loginButton) {
		this.loginButton = loginButton;
	}
}

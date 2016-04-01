package tr.com.agem.arya.interpreter.script;

import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import tr.com.agem.arya.interpreter.component.AryaScript;
import tr.com.agem.arya.interpreter.components.base.AryaMain;
import tr.com.agem.arya.metadata.interpreter.IAryaComponent;
import tr.com.agem.core.utils.AryaUtils;

public class ScriptHelper {
	
	private static final Logger logger = Logger.getLogger(ScriptHelper.class.getName());

	public static Object executeScript(String functionName, HashMap<Object, Object> params, AryaMain main) {
		StringBuilder script = new StringBuilder(); //TODO performansı arttırmak için; jsrun bir kere çağırılıp context.evaluateString tekrarlı çağırılmalı(volkan)
		AryaScript scriptObj = getScriptComponent(main);
		
		if (AryaUtils.isNotEmpty(scriptObj)) {
			script.append(scriptObj.getScript()).append(getFunctionName(functionName));
			logger.log(Level.FINE, "Script: {0}", script);
			return JsRunner.jsRun(scriptObj.getSrcList(), script.toString(), main);
		}

		return null;
	}

	private static AryaScript getScriptComponent(AryaMain main) {
		AryaScript scriptObj = null;
		Set<IAryaComponent> comps = main.getAryaWindowComponents();
		for (IAryaComponent comp : comps) {
			if (comp instanceof AryaScript) {
				scriptObj = (AryaScript) comp;
				logger.log(Level.FINE, "AryaScript instance found.");
				
				if(scriptObj.getScript() != null)
					return scriptObj;
			}
		}
		return scriptObj;
	}

	private static StringBuilder getFunctionName(String functionName) {
		StringBuilder fName = new StringBuilder(functionName);
		if (!functionName.endsWith(")")) {
			fName.append("()");
		}
		return fName.append(";");
	}

}

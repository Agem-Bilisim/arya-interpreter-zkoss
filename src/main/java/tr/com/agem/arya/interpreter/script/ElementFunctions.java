package tr.com.agem.arya.interpreter.script;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeFunction;
import org.mozilla.javascript.NativeJSON;
import org.mozilla.javascript.Scriptable;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.ChartModel;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.SimpleCategoryModel;
import org.zkoss.zul.SimplePieModel;
import org.zkoss.zul.Tab;

import tr.com.agem.arya.interpreter.component.AryaFlashchart;
import tr.com.agem.arya.interpreter.component.AryaListbox;
import tr.com.agem.arya.interpreter.component.AryaTabpanel;
import tr.com.agem.arya.interpreter.component.AryaTabs;
import tr.com.agem.arya.interpreter.component.AryaTextbox;
import tr.com.agem.arya.interpreter.components.base.AryaMain;
import tr.com.agem.arya.interpreter.utils.AryaException;
import tr.com.agem.arya.interpreter.utils.AryaInterpreterHelper;
import tr.com.agem.arya.interpreter.utils.BaseController;
import tr.com.agem.arya.metadata.interpreter.IAryaComponent;
import tr.com.agem.core.gateway.model.AryaResponse;
import tr.com.agem.core.property.reader.PropertyReader;
import tr.com.agem.core.utils.AryaUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ElementFunctions extends AnnotatedScriptableObject {

	private static final long serialVersionUID = 2251889177219110859L;
	
	private static final Logger logger = Logger.getLogger(ElementFunctions.class.getName());
	
	private Context context;
	private Scriptable scope;
	private AryaMain main;
	private static NativeArray comps;
	private static ArrayList<String> values;

	public ElementFunctions(Context context, Scriptable scope, AryaMain main) {
		this.context = context;
		this.scope = scope;
		this.main = main;
	}
	
	@AryaJsFunction
	public void populate(String data) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode rootNode = mapper.readTree(data);
			if (rootNode != null) {
				Iterator<Entry<String, JsonNode>> fields = rootNode.fields();
				if (fields != null) {
					while (fields.hasNext()) {
						Entry<String, JsonNode> entry = fields.next();
						logger.log(Level.FINE, "JSON property: {0}:{1}", new Object[]{ entry.getKey(), entry.getValue() });
						
						IAryaComponent comp = (IAryaComponent) getElementById(entry.getKey());
						if (comp != null) {
							comp.setComponentValue(entry.getValue().asText());
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@AryaJsFunction
	public void post(String action, String requestType, Object params, String tabValue, NativeFunction onSuccess, NativeFunction onFailure) {//debug dan geçirmek gerek(volkan)

		// no javascript is used in some pages
		if(!(params instanceof String)) {
			Object jsonParam = NativeJSON.stringify(context, scope, params, null, null);
			params = jsonParam;
		}
		
		StringBuilder request = new StringBuilder("{ \"params\": ")
				.append(params)
				.append(", \"requestType\": \"")
				.append(requestType)
				.append("\", \"action\": \"")
				.append(action)
				.append("\" }");
		
		String result=null;
		AryaResponse response=null;
		try {
			result = AryaInterpreterHelper.callUrl(PropertyReader.property("gateway.base.url"), request.toString());
			
			logger.log(Level.FINE, "Post result: {0}", result);
			
			response = new AryaResponse();
			response.fromXMLString(result);
			
			AryaInterpreterHelper.interpretResponse(response, action, main, BaseController.getTabs(), BaseController.getTabpanels(), tabValue);
			
			// if the user has logged in
			if(requestType.equals("LOGIN") && AryaUtils.isNotEmpty(response.getData())) {
				
				BaseController.getMain().getMenuContainer().setVisible(true);
				BaseController.getMain().getLogin().setVisible(false);
				
				post("master","VIEW_ONLY", "{}", "Master", null, null);
				
			}
			
						
			if (onSuccess != null) {
				scope.put(onSuccess.getFunctionName(), scope, onSuccess);
				Context.call(null, onSuccess, scope, this, new Object[]{ response });
			}
			
		} catch (AryaException e) {
			if (onFailure != null) {
				scope.put(onFailure.getFunctionName(), scope, onFailure);
				Context.call(null, onFailure, scope, this, new Object[]{ response });			
			}
		}
		
		if(action.equals("logout") && requestType.equals("LOGOUT")){
			main.getMenuContainer().setVisible(false);
			main.getComponentContainer().getChildren().clear();
		}
		
	}
	
	@AryaJsFunction
	public void renderSelectedItem (String elementId, String id, String action, NativeArray comps, NativeArray values, 
			String tabValue) {
		
		JSONObject jsonObj = null;
		
		if(getElementById(elementId) instanceof AryaListbox) {
			jsonObj = ((AryaListbox)getElementById(elementId)).getSelectedItem().getValue();
		
			String params = "{\"id\":\""+ splitId(id, jsonObj)+"\"}"; 
			
			for (int i = 0; i < comps.size(); i++) {
				
				String value = splitId(values.get(i).toString(), jsonObj);
				String comp = (String) comps.get(i);			
				((IAryaComponent)getElementById(comp)).setComponentValue(value);
			} 
	
			if(!action.isEmpty())
				post(action, "ALL", params, tabValue, null, null);
		}
	}
	
	//for two listbox at same page
	@AryaJsFunction
	public void renderAtSamePage (String elementId, String id, String action, String tabValue) {  
		
		JSONObject jsonObj = null;
		
		if(getElementById(elementId) instanceof AryaListbox) {
			jsonObj = ((AryaListbox)getElementById(elementId)).getSelectedItem().getValue();

			 if(!action.isEmpty() && action.endsWith("list")) {
				 
				//TODO isyeriIdParam -> idParam!!!!!
				 String params = "{\"isyeriIdParam\":\""+ splitId(id, jsonObj)+"\",\"id\":\""+ splitId(id, jsonObj)+"\"}";
				 post(action, "DATA_ONLY", params, tabValue, null, null);
			}
		}
	}
	
	@AryaJsFunction
	public void setChartModel(String chartId){
		
		IAryaComponent comp = getElementById(chartId);
		
		if(comp instanceof AryaFlashchart){
			AryaFlashchart chart = (AryaFlashchart) comp;
			ChartModel model = null;
			
			if(chart.getType() != null){
				
				if(chart.getType().equals("pie")){
					model = new SimplePieModel();
				}
				else if(chart.getType().equals("stackbar") || chart.getType().equals("bar") || 
						chart.getType().equals("line") || chart.getType().equals("column")){
					
					model = new SimpleCategoryModel();
				}
				chart.setModel(model);
			}
		}
	}
	
	@AryaJsFunction
	public void setChartValue(String chartId, String category, String secondCategory, Double value){
		
		IAryaComponent comp = getElementById(chartId);
		
		if(comp instanceof AryaFlashchart) {
			AryaFlashchart chart = (AryaFlashchart) comp;
			
			if(chart.getType() != null){
				
				if(chart.getType().equals("pie")){
					SimplePieModel model = (SimplePieModel) chart.getModel();
					model.setValue(category, value);
				}
				else if(chart.getType().equals("stackbar") || chart.getType().equals("bar") || 
						chart.getType().equals("line") || chart.getType().equals("column")){
					
					SimpleCategoryModel model = (SimpleCategoryModel) chart.getModel();
					model.setValue(category, secondCategory, value);
				}
			}
		}
	}
	
	@AryaJsFunction
	public void clean(String parentComp){
		
		Component obj = (Component) getElementById(parentComp);
		while (!(obj instanceof AryaTabpanel)) {
			obj = obj.getParent(); 
		}
		AryaTabpanel tabpanel = (AryaTabpanel) obj;
		Collection<String> components = tabpanel.getComponents();
		
		for (String id : components) {
			IAryaComponent c = getElementById(id);
			if (AryaInterpreterHelper.isInputElement(c)) {
				
				if(c.getComponentValue() != null)
					c.setComponentValue("");
			}
		}
	}
	
	@AryaJsFunction
	public void setParams (NativeArray values) {
		
		ArrayList<String> temp = new ArrayList<String>();
		
		for (int i = 0; i < values.size(); i++) {
			
			temp.add(((AryaTextbox)getElementById((String) values.get(i))).getText());
		}		
		setValues(temp);
	}
		
	
	@AryaJsFunction
	public IAryaComponent getElementById(String id) { //only on window not menu

		Set<IAryaComponent> comps = main.getAryaWindowComponents();
		for (IAryaComponent comp : comps) {

			if (comp.getComponentId() != null && comp.getComponentId().endsWith(id)) {
					return comp;
			}
		
		}
		return null;
	}

	@AryaJsFunction
	public Object[] getElementsByName(String name) {//only on window not menu

		List<IAryaComponent> objList = new ArrayList<IAryaComponent>();
		Set<IAryaComponent> comps = main.getAryaWindowComponents();
		for (IAryaComponent comp : comps) {
			if (name.equalsIgnoreCase(
					comp.getClass().toString().replace("class tr.com.agem.arya.interpreter.component.Arya", ""))) {
				objList.add(comp);
			}
		}
		return objList.toArray(new Object[objList.size()]);
	}

	@AryaJsFunction
	public Object[] getElementsByClass(String className) {//only on window not menu

		List<IAryaComponent> objList = new ArrayList<IAryaComponent>();
		Set<IAryaComponent> comps = main.getAryaWindowComponents();
		for (IAryaComponent comp : comps) {

			if (className.equalsIgnoreCase(comp.getComponentClassName())) {
				objList.add(comp);
			}
		}
		return objList.toArray(new Object[objList.size()]);
	}

	@AryaJsFunction
	public String serializeForm() {//only on window not menu
		String strSerialize = "";
		Set<IAryaComponent> comps = main.getAryaWindowComponents();
		for (IAryaComponent comp : comps) {
			strSerialize += ",\"" + comp.getComponentId() + "\":"
					+ (comp.getComponentValue() == null ? null : "\"" + comp.getComponentValue() + "\"");
		}

		if (strSerialize.length() > 0)
			return "{" + strSerialize.substring(1, strSerialize.length()) + "}";
		return "{}";
	}
	
	@AryaJsFunction
	public void send(String action, String requestType, String parentObjectId, String objectIdProp, String tabName) throws JsonProcessingException {
		//TODO searchIsyeri'nde database isimleri doğru olmadığı için (şube dışında) sonuç doğru gelmiyor????
		Component obj = (Component) getElementById(parentObjectId);
		while (!(obj instanceof AryaTabpanel)) {
			obj = obj.getParent(); 
		}
		AryaTabpanel tabpanel = (AryaTabpanel) obj;
		Collection<String> components = tabpanel.getComponents();
		Map<String, Object> m = new HashMap<String, Object>();
		for (String id : components) {
			IAryaComponent c = getElementById(id);
			if (!(c instanceof Listbox) && AryaInterpreterHelper.isInputElement(c)) {
				
				String v = c.getComponentValue();
				String d = c.getDatabase();
				
				if(d != null)
					m.put(d, v);
			}
		}
		ObjectMapper mapper = new ObjectMapper(); 
		String json = mapper.writeValueAsString(m);
		if ("undefined".equals(tabName)) {
			tabName = null;
		}
		post(action, requestType, json, AryaUtils.isEmpty(tabName) ?  tabpanel.getTab().getLabel() : tabName, null, null);
	}

	@AryaJsFunction
	public void tabCloseFunction(String tabId) throws JsonProcessingException {
		Component comp = (Component)getElementById("tab" + tabId);
		AryaInterpreterHelper.removeElement(main, comp.getParent(), comp);
		comp = (Component)getElementById("tabpanel" + tabId);
		AryaInterpreterHelper.removeElement(main, comp.getParent(), comp);

		AryaTabs tabs = BaseController.getTabs();
		if(tabs.getChildren() != null && tabs.getChildren().size() > 1)	{
			BaseController.getTabbox().setSelectedTab((Tab)tabs.getChildren().get(tabs.getChildren().size()-1));
		}
	}
	
	public static String splitId(String id, JSONObject jsonObj) {

		String retVal = null;
		JSONObject obj = jsonObj;
						
		if (jsonObj != null) {
			
			String[] spl;
			
			if(id.contains("-")) {
				
				String[] temp = id.split("-");
				spl = temp[1].split("\\.");
			}
			else {
				spl = id.split("\\.");
			}
			
			for (int i = 0; i < spl.length - 1; i++)
				obj = (JSONObject) obj.get(spl[i]);
			
			Object ret;
			
			if (obj != null)
				ret = obj.get(spl[spl.length - 1]);
			else    
				ret = jsonObj.get(spl[0]);
			if (!ret.equals(JSONObject.NULL)) {
				retVal = ret.toString();
			}
		}  
		return retVal;
	}
	
	@AryaJsFunction
	public static NativeArray getComps() {
		return comps;
	}

	@AryaJsFunction
	public static void setComps(NativeArray comps) {
		ElementFunctions.comps = comps;
	}

	public static ArrayList<String> getValues() {
		return values;
	}

	public static void setValues(ArrayList<String> values) {
		ElementFunctions.values = values;
	}
	
}


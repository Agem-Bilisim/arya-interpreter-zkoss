package tr.com.agem.arya.interpreter.utils;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.impl.InputElement;

import tr.com.agem.arya.interpreter.command.AryaFill;
import tr.com.agem.arya.interpreter.component.AryaAuxHead;
import tr.com.agem.arya.interpreter.component.AryaComboItem;
import tr.com.agem.arya.interpreter.component.AryaCombobox;
import tr.com.agem.arya.interpreter.component.AryaGrid;
import tr.com.agem.arya.interpreter.component.AryaListCell;
import tr.com.agem.arya.interpreter.component.AryaListHead;
import tr.com.agem.arya.interpreter.component.AryaListItem;
import tr.com.agem.arya.interpreter.component.AryaListbox;
import tr.com.agem.arya.interpreter.component.AryaRow;
import tr.com.agem.arya.interpreter.component.AryaRows;
import tr.com.agem.arya.interpreter.component.AryaScript;
import tr.com.agem.arya.interpreter.component.AryaTab;
import tr.com.agem.arya.interpreter.component.AryaTabpanel;
import tr.com.agem.arya.interpreter.component.AryaTabpanels;
import tr.com.agem.arya.interpreter.component.AryaTabs;
import tr.com.agem.arya.interpreter.component.AryaTemplate;
import tr.com.agem.arya.interpreter.component.ComponentFactory;
import tr.com.agem.arya.interpreter.components.base.AryaMain;
import tr.com.agem.arya.interpreter.parser.AryaMetadataParser;
import tr.com.agem.arya.interpreter.parser.AryaParserAttributes;
import tr.com.agem.arya.interpreter.script.ElementFunctions;
import tr.com.agem.arya.metadata.interpreter.IAryaComponent;
import tr.com.agem.arya.metadata.interpreter.IAryaTemplate;
import tr.com.agem.core.gateway.model.AryaRequest;
import tr.com.agem.core.gateway.model.AryaResponse;
import tr.com.agem.core.utils.AryaUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AryaInterpreterHelper {

	private static final String MIME_TYPE = "application/json;charset=UTF-8";

	private static HttpClient httpClient = HttpClientBuilder.create().build();

	public static String callUrl(String url, AryaRequest request) throws AryaException {
		return callUrl(url, request.toJSON());
	}

	// TODO should be run on a seperate thread!
	public static String callUrl(String url, String request) throws AryaException {

		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", MIME_TYPE);
		httpPost.setHeader("Accept", MIME_TYPE);

		StringEntity se;
		String result = null;
		try {
			se = new StringEntity(request, Charset.forName("UTF-8"));

			httpPost.setEntity(se);
			HttpResponse response = httpClient.execute(httpPost);
			// TODO AryaLoginFailedException reason show the reason
			System.out.println(response.getStatusLine().getStatusCode() + "-"
					+ response.getStatusLine().getReasonPhrase() + "-" + response.getStatusLine().getStatusCode());

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new AryaException("hooo");
			}

			HttpEntity entity = response.getEntity();
			
			result = EntityUtils.toString(entity, "UTF-8");
			
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		return result;

	}

	public static void interpretResponse(AryaResponse response, String action, AryaMain main, AryaTabs tabs, AryaTabpanels tabpanels, String tabValue) {
		
		if (AryaUtils.isNotEmpty(response.getView())) {
			// Remove previous components before adding new ones!
			AryaTab tab = getCurrentTab(tabs, tabValue);
			if (tab != null) {
				AryaTabpanel panel = tab.getTabPanel();
				panel.setTab(null);
				tab.setTabPanel(null);
				removeElement(main, panel.getParent(), panel);
				removeElement(main, tab.getParent(), tab);
			} 
		
			tab = null;

			drawView(response.getView(), main, tab, tabs, tabpanels, false, tabValue);
		}
		
		if(AryaUtils.isNotEmpty(response.getAttributes())) {
			
			AryaTab tab = getCurrentTab(tabs, tabValue);
			
			Set<IAryaComponent> comps = main.getAryaWindowComponents();
			for(IAryaComponent component : comps) {
				
				if(component.getAttribute() != null) {
					if(component instanceof AryaCombobox && ((AryaCombobox)component).getChildren().isEmpty())
						populateToFill((String) response.getAttributes(), component, main, tab.getTabPanel());
				}
			}
		}

		if (AryaUtils.isNotEmpty(response.getData())) {
			
			AryaTab tab = getCurrentTab(tabs, tabValue);
			
			if(response.getView().isEmpty() && tab != null) {
				
				//to remove old listbox items at same page
				if(getElementById(action, main) instanceof AryaListbox) {
					
					AryaListbox listbox = (AryaListbox) getElementById(action, main);
					
					for (int i = 0; i < listbox.getChildren().size(); i++) {
						if(!(listbox.getChildren().get(i) instanceof AryaAuxHead) && !(listbox.getChildren().get(i) instanceof AryaListHead)){
							listbox.getChildren().remove(i);
						}
					}
				}
			}
			
			populateView(response.getData(), action, main, tab, tabValue);
		}
		
	}
	
	public static void interpretResponseMenu(AryaResponse response, AryaMain main, AryaTabs tabs, AryaTabpanels tabpanels) {
		if (AryaUtils.isNotEmpty(response.getView())) {// Remove previous
														// components before
														// adding new ones!
			if (AryaUtils.isNotEmpty(main.getMenuContainer())) {
				Div menuDiv = main.getMenuContainer();
				menuDiv.getChildren().clear();
			}
			

			drawView(response.getView(), main, null, tabs, tabpanels, true, null);
		}
	}
	
	public static void removeElement(AryaMain main, Component parent, Component component) {
		try {
			if (!(component instanceof Listbox)) {
				List<Component> childs = component.getChildren();
				
				for (int i=0; i < childs.size(); ) {
					Component c = childs.get(0);
					removeElement(main, component, c);
				}
			}
			
			if(component instanceof AryaScript){
				return;
			}
			
			parent.removeChild(component);
			Set<IAryaComponent> comps = main.getAryaWindowComponents();
			comps.remove(component);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void populateToFill(String data, IAryaComponent cmp, AryaMain main, AryaTabpanel tabpanel) {  //for fill command or session data
		
		for (int i = 0; i < getJSONArray(data).length(); i++) {
			
			JSONObject jsonObj = getJSONArray(data).getJSONObject(i);
			
			String comp = null;
			String value = null;
			
			if(cmp != null && cmp instanceof AryaFill) {
				comp = new String(((AryaFill) cmp).getTo());
				value = new String(((AryaFill) cmp).getValue());
			}
			else {
				JSONArray jsonArray = (JSONArray) jsonObj.get(cmp.getAttribute());
				
				for (int j = 0; j < jsonArray.length(); j++) {
					JSONObject jsonObject = jsonArray.getJSONObject(j);
					
					if(cmp instanceof AryaCombobox) {
						AryaCombobox combo = (AryaCombobox) cmp;
						
						String label = new String(ElementFunctions.splitId(combo.getAttributeLabel(), jsonObject));
						String id = tabpanel.getComponentId()+"-"+System.currentTimeMillis()+""+ElementFunctions.splitId("id", jsonObject);
						
						createComboItem(label, id, jsonObject, main, combo);
					}
				}

			}
			
			if(cmp instanceof AryaFill && comp != null) {
				if(getElementById(comp, main) instanceof AryaCombobox) {
					AryaCombobox combo = (AryaCombobox) getElementById(comp, main);
					
					String label = new String(ElementFunctions.splitId(value, jsonObj));
					String id = tabpanel.getComponentId()+"-"+System.currentTimeMillis()+""+ElementFunctions.splitId("id", jsonObj);
					
					createComboItem(label, id, jsonObj, main, combo);
				}
			}
		}
	}
	
	public static void createComboItem (String label, String id, JSONObject jsonObj, AryaMain main, AryaCombobox combo){
		
		AryaParserAttributes attr = new AryaParserAttributes();
		
		if(label != null) {
			attr.setValue("label", label);
			attr.setValue("id", id);
			attr.setValue("value", ElementFunctions.splitId("id", jsonObj));
			AryaComboItem comboItem = new AryaComboItem(main, attr);
			comboItem.setComponentParent(combo);
		}
	}

	private static void populateAryaTemplate(AryaMain main, IAryaTemplate masterComponent, JSONArray jsonArrayData) {
		
		AryaRows rows = null;
		
		if (masterComponent instanceof AryaGrid) {
			rows = new AryaRows(main, null);
			rows.setComponentParent(masterComponent);
		}

		for (int i = 0; i < jsonArrayData.length(); i++) {
			
			JSONObject jsonObj = jsonArrayData.getJSONObject(i);
			
			AryaListItem item = new AryaListItem(main, null);
			AryaRow row = new AryaRow(main, null);
				
			if (masterComponent instanceof AryaListbox) {
				item.setValue(jsonObj);
				item.setComponentParent(masterComponent);
			} 
			else if (masterComponent instanceof AryaGrid) {
				row.setValue(jsonObj);
				row.setComponentParent(rows);
			}
			
			for (IAryaComponent comp : ((AryaTemplate) masterComponent.getAryaTemplate()).getChildren()) {
				
				if (!(comp instanceof AryaListItem) && !(comp instanceof AryaRow)) {
					
					AryaParserAttributes attr = new AryaParserAttributes();
					attr.setValue("id", System.currentTimeMillis()+""+comp.getComponentId() + "" + (i));
					if (masterComponent instanceof AryaListbox) {
						attr.setValue("label", ElementFunctions.splitId(comp.getDatabase(), jsonObj));
						AryaListCell cell = new AryaListCell(main, attr);
						cell.setComponentParent(item);
					} else if (masterComponent instanceof AryaGrid) {
						attr.setValue("value", ElementFunctions.splitId(comp.getDatabase(), jsonObj));
						IAryaComponent compNew = ComponentFactory.getComponent(comp.getComponentTagName(), main, attr);
						compNew.setComponentParent(row);
					} 
				}
			}
		}
		ElementFunctions.setComps(null);
		ElementFunctions.setValues(new ArrayList<String>());
	}

	public static void populateView(String data, String action, AryaMain main, AryaTab tab, String tabValue) {
		try {
			JSONArray jsonArray = getJSONArray(data);
			String message = "";
			if (jsonArray.length() > 1) {
				populateAryaTemplate(main, (IAryaTemplate) getElementById(action, main), jsonArray);
			} 
			else  if (jsonArray.length() == 1){
				JSONObject jsonObj = jsonArray.getJSONObject(0);
				for (String id : tab.getTabPanel().getComponents()) {
					IAryaComponent comp = getElementById(id, main);
					if (isInputElement(comp)) {  
						
						String key = comp.getDatabase();
						comp.setComponentValue(getJSONValue(jsonObj,key).toString());
					}
				}
				message = (String) jsonObj.get("@message");
			}
			if (message != null) {
				main.getMessage().setValue(message);
			}
			else { 
				main.getMessage().setValue("");
			}
		}
		catch (Exception e ) {
			main.getMessage().setValue("");
		}
	
	}
	
	public static JSONArray getJSONArray(String data) {
		
		JSONArray jsonArray = null;
		ObjectMapper mapper = new ObjectMapper(); 
		try {
			JsonNode rootNode = mapper.readTree(data);
			if (rootNode != null) {
				Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();
				if (fields != null) {
					while (fields.hasNext()) {
						Map.Entry<String, JsonNode> entry = fields.next();
						if ("results".equals(entry.getKey().toString())) {
							jsonArray = new JSONArray(entry.getValue().toString());
						}
						else if ("session".equals(entry.getKey().toString())) {
							jsonArray = new JSONArray(entry.getValue().toString());
						}
					}
				}
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return jsonArray;
	}
	
	private static Object getJSONValue(JSONObject jsonObj, String key) {
		
		String k = StringUtils.substringBefore(key, ".");
		String x = StringUtils.substringAfter(key, ".");
		if (AryaUtils.isEmpty(x)) {
			Object v= "";
			try {
				v = jsonObj.get(k);
			}
			catch (Exception e) {
			}
			if (AryaUtils.isEmpty(v) || v.equals(null)) {
				return ""; 
			}
			return v;
		}
		Object obj = jsonObj.get(k);
		if (!(obj instanceof JSONObject)) {
			obj = null;
		}
		return obj == null ? "" : getJSONValue((JSONObject) obj, x);
	}

	private static void drawView(String view, AryaMain main, AryaTab tab, AryaTabs tabs, AryaTabpanels tabpanels, Boolean isMenu, String tabValue) {
						
		if (tab != null) {
			tab.setSelected(true);
		}
		
		AryaTabpanel tabpanel = new AryaTabpanel(main, null);
	
		if(!isMenu && tab == null) {
			final String tabId = System.currentTimeMillis() + "";
			Attributes attributes = new Attributes() {
				
				@Override
				public String getValue(String uri, String localName) {
					return null;
				}
				
				@Override
				public String getValue(String qName) {
					if ("onClose".equals(qName)) {
						return "tabCloseFunction('"+tabId+"')";
					} else if ("id".equals(qName)) {
						return "tab" + tabId;
					}
					return null;
				}
				
				@Override
				public String getValue(int index) {
					return null;
				}
				
				@Override
				public String getURI(int index) {
					return null;
				}
				
				@Override
				public String getType(String uri, String localName) {
					return null;
				}
				
				@Override
				public String getType(String qName) {
					return null;
				}
				
				@Override
				public String getType(int index) {
					return null;
				}
				
				@Override
				public String getQName(int index) {
					return null;
				}
				
				@Override
				public String getLocalName(int index) {
					return null;
				}
				
				@Override
				public int getLength() {
					return 0;
				}
				
				@Override
				public int getIndex(String uri, String localName) {
					return 0;
				}
				
				@Override
				public int getIndex(String qName) {
					return 0;
				}
			};
			
			tab = new AryaTab(main, attributes);
			tab.setParent(tabs);
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setLabel(tabValue);
			tab.setComponentId("tab" + tabId);
			main.getAryaWindowComponents().add(tab);

			tabpanel = new AryaTabpanel(main, null);
			tabpanel.setParent(tabpanels);
			tabpanel.setComponentId("tabpanel"+tabId);
			tab.setTabPanel(tabpanel);
			tabpanel.setTab(tab);
			main.getAryaWindowComponents().add(tabpanel);
			if ("Master".equals(tabValue)) {
				tab.setVisible(false);
				tabpanel.setVisible(false);
			}

		}
		

		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXParser parser = null;

		try {
			parser = saxParserFactory.newSAXParser();
			parser.parse(new InputSource(new StringReader(view)), new AryaMetadataParser(main, isMenu, tabpanel));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace(); 
		}
		
	}

	private static AryaTab getCurrentTab(AryaTabs tabs, String tabValue) {
		if(tabs.getChildren() != null)	{
			for (int i = 0; i < tabs.getChildren().size(); i++) {
				AryaTab tab = (AryaTab)tabs.getChildren().get(i);
				if(tab.getLabel().equals(tabValue)) {
					return tab; 
				}
			}
		}
		return null;
	} 

	public static IAryaComponent getElementById(String id, AryaMain main) { // only
																			// on
																			// window
																			// not
																			// menu

		Set<IAryaComponent> comps = main.getAryaWindowComponents();
		for (IAryaComponent comp : comps) {
			// ids are suffix of component ids (tabpanel2-list etc....)
			if (comp.getComponentId() != null && comp.getComponentId().endsWith(id)) {
				return comp;
			}
		}
		return null;
	}
	
	public static boolean isInputElement(IAryaComponent comp) {
		return (comp instanceof InputElement) ||
			   (comp instanceof Radiogroup) ||
			   (comp instanceof Combobox) ||
			   (comp instanceof Checkbox) ||
			   (comp instanceof Listbox);
	}
	
}

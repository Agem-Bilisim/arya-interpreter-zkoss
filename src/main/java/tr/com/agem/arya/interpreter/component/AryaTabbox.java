package tr.com.agem.arya.interpreter.component;

import org.xml.sax.Attributes;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Tabbox;

import tr.com.agem.arya.interpreter.components.base.AryaMain;
import tr.com.agem.arya.interpreter.script.ScriptHelper;
import tr.com.agem.arya.metadata.interpreter.IAryaComponent;
import tr.com.agem.arya.metadata.interpreter.IAryaTemplate;
import tr.com.agem.core.utils.AryaUtils;

public class AryaTabbox extends Tabbox implements IAryaComponent, IAryaTemplate {
	
	private static final long serialVersionUID = 1L;
	
	private String componentClassName;
	private String database;
	private AryaTemplate template;
	
	public AryaTabbox(final AryaMain main, Attributes attributes) {
		super();
	
		if (AryaUtils.isNotEmpty(attributes)) {
			this.setId(attributes.getValue("id"));
			this.componentClassName = attributes.getValue("class");
	
			this.setClass(attributes.getValue("class"));
			if (attributes.getValue("visible") != null) {
				this.setVisible(Boolean.parseBoolean(attributes.getValue("visible")));
			}

			this.setTooltip(attributes.getValue("tooltip"));
			this.setTooltiptext(attributes.getValue("tooltiptext"));
			this.setDraggable(attributes.getValue("draggable"));
			this.setDroppable(attributes.getValue("droppable"));
			if (attributes.getValue("focus") != null) {
				this.setFocus(Boolean.parseBoolean(attributes.getValue("focus")));
			}
			this.setStyle(attributes.getValue("style"));
			this.setZclass(attributes.getValue("zlass"));
			this.setSclass(attributes.getValue("sclass"));
			this.setLeft(attributes.getValue("left"));
			this.setTop(attributes.getValue("top"));
			if (attributes.getValue("zindex") != null) {
				this.setZIndex(Integer.parseInt(attributes.getValue("zindex")));
			}
			if (attributes.getValue("renderdefer") != null) {
				this.setRenderdefer(Integer.parseInt(attributes.getValue("renderdefer")));
			}
			this.setAction(attributes.getValue("action"));
			this.setHflex(attributes.getValue("hflex"));
			this.setVflex(attributes.getValue("vflex"));
			// TODO unit checking will be fixed
			this.setHeight(attributes.getValue("height"));
			this.setWidth(attributes.getValue("width"));
			
			this.setMold(attributes.getValue("mold"));
			
			this.database = attributes.getValue("database");
	
			if (AryaUtils.isNotEmpty(attributes.getValue("onDrop"))) {
				final String functionName = attributes.getValue("onDrop");
				this.addEventListener("onDrop", new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						ScriptHelper.executeScript(functionName, null, main);
					}
				});
			}
	
			if (AryaUtils.isNotEmpty(attributes.getValue("onCreate"))) {
				final String functionName = attributes.getValue("onCreate");
				this.addEventListener("onCreate", new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						ScriptHelper.executeScript(functionName, null, main);
					}
				});
			}
	
			if (AryaUtils.isNotEmpty(attributes.getValue("onSelect"))) {
				final String functionName = attributes.getValue("onSelect");
				this.addEventListener("onSelect", new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						
						ScriptHelper.executeScript(functionName, null, main);
					}
				});
			}
		}
	}

	@Override
	public void setComponentParent(Object parent) {
		this.setParent((Component) parent);
	}

	@Override
	public String validate() {
		return null;
	}

	@Override
	public String getComponentClassName() {
		return componentClassName;
	}

	@Override
	public void setComponentClassName(String componentClassName) {
		this.componentClassName = componentClassName;
	}

	@Override
	public String getComponentId() {
		return this.getId();
	}

	@Override
	public void setComponentId(String componentId) {
		this.setId(componentId);
	}

	@Override
	public String getComponentValue() {
		/*
		 * There is no componentValue variable for this component. This function
		 * was created for IAryaComponent interface.
		 */
		return null;
	}

	@Override
	public void setComponentValue(String componentValue) {
		/*
		 * There is no componentValue variable for this component. This function
		 * was created for IAryaComponent interface.
		 */
	}

	@Override
	public Object getAryaTemplate() {
		return template;
	}

	@Override
	public void setAryaTemplate(Object template) {
		this.template = (AryaTemplate) template;
	}
	
	@Override
	public Object getComponentParent() {
		return this.getComponentParent();
	}

	@Override
	public String getComponentTagName() {
		return "tabbox";
	}

	@Override
	public String getDatabase() {
		return database;
	}

	@Override
	public void setDatabase(String database) {
		this.database = database;
	}

	@Override
	public String getAttribute() {
		return null;
	}

	@Override
	public void setAttribute(String attribute) {
	}

	@Override
	public String getAttributeValue() {
		return null;
	}

	@Override
	public void setAttributeValue(String attributeValue) {
	}

	@Override
	public String getAttributeLabel() {
		return null;
	}

	@Override
	public void setAttributeLabel(String attributeLabel) {
	}

}

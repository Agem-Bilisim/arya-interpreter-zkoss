package tr.com.agem.arya.interpreter.component;

import org.xml.sax.Attributes;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Label;

import tr.com.agem.arya.interpreter.components.base.AryaMain;
import tr.com.agem.arya.interpreter.script.ScriptHelper;
import tr.com.agem.arya.metadata.interpreter.IAryaComponent;
import tr.com.agem.core.utils.AryaUtils;

public class AryaLabel extends Label implements IAryaComponent {

	private static final long serialVersionUID = 1507265134096459571L;

	private String componentClassName;
	private String database;
	private String attribute;
	private String attributeValue;
	private String attributeLabel;

	public AryaLabel(final AryaMain main, Attributes attributes) {
		super();

		if (AryaUtils.isNotEmpty(attributes)) {
			this.setId(attributes.getValue("id"));
			this.componentClassName = attributes.getValue("class");

			this.setValue(attributes.getValue("value"));
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
			
			this.database = attributes.getValue("database");
			this.attribute = attributes.getValue("attribute");
			this.attributeValue = attributes.getValue("attributeValue");
			this.attributeLabel = attributes.getValue("attributeLabel");

			/*
			 * if the dimension input format of .arya files does NOT contains
			 * the unit like -height="200px"
			 */

			if ((attributes.getValue("height") != null) && attributes.getValue("height").contains("px")) {
				this.setHeight(attributes.getValue("height"));
			} else {
				this.setHeight(attributes.getValue("height") + "px");
			}

			if ((attributes.getValue("width") != null) && attributes.getValue("width").contains("px")) {
				this.setWidth(attributes.getValue("width"));
			} else {
				this.setWidth(attributes.getValue("width") + "px");
			}

			if (AryaUtils.isNotEmpty(attributes.getValue("onClick"))) {
				final String functionName = attributes.getValue("onClick");
				this.addEventListener("onClick", new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						ScriptHelper.executeScript(functionName, null, main);
					}
				});
			}

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

			if (AryaUtils.isNotEmpty(attributes.getValue("onDoubleClick"))) {
				final String functionName = attributes.getValue("onDoubleClick");
				this.addEventListener("onDoubleClick", new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						ScriptHelper.executeScript(functionName, null, main);
					}
				});
			}
			if (AryaUtils.isNotEmpty(attributes.getValue("onRightClick"))) {
				final String functionName = attributes.getValue("onRightClick");
				this.addEventListener("onRightClick", new EventListener<Event>() {
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
		return this.getValue();
	}

	@Override
	public void setComponentValue(String componentValue) {
		this.setValue(componentValue);
	}
	
	@Override
	public Object getComponentParent() {
		return this.getComponentParent();
	}

	@Override
	public String getComponentTagName() {
		return "label";
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
		return attribute;
	}

	@Override
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	@Override
	public String getAttributeLabel() {
		return attributeLabel;
	}

	@Override
	public void setAttributeLabel(String attributeLabel) {
		this.attributeLabel = attributeLabel;
	}

}
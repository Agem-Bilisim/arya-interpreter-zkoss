package tr.com.agem.arya.interpreter.component.menu;

import org.xml.sax.Attributes;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Menubar;

import tr.com.agem.arya.interpreter.components.base.AryaMain;
import tr.com.agem.arya.interpreter.script.ScriptHelper;
import tr.com.agem.arya.metadata.interpreter.IAryaComponent;
import tr.com.agem.core.utils.AryaUtils;

public class AryaMenuBar extends Menubar implements IAryaComponent, IAryaMenu {

	private static final long serialVersionUID = 8605492923881165335L;
	
	private String componentClassName;
	private String componentId;
	private String componentValue;

	public AryaMenuBar(final AryaMain main, Attributes attributes) {

		super();

		if (AryaUtils.isNotEmpty(attributes)) {
			this.componentId = attributes.getValue("id");
			this.componentClassName = attributes.getValue("class");
			this.componentValue = attributes.getValue("value");

			this.setId(attributes.getValue("id"));
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
			
			if (attributes.getValue("orient") != null)
				this.setOrient(attributes.getValue("orient"));
			
			if (attributes.getValue("style") != null)
				this.setStyle(attributes.getValue("style"));

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
		}
	}

	@Override
	public void setComponentParent(Object parent) {
		this.setParent((Component) parent);
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
		return componentId;
	}

	@Override
	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

	@Override
	public String getComponentValue() {
		return componentValue;
	}

	@Override
	public void setComponentValue(String componentValue) {
		this.componentValue = componentValue;
	}

	@Override
	public String validate() {
		return null;
	}
	
	@Override
	public Object getComponentParent() {
		return this.getComponentParent();
	}

	@Override
	public String getComponentTagName() {
		return "menubar";
	}

	@Override
	public String getDatabase() {
		return null;
	}

	@Override
	public void setDatabase(String database) {
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

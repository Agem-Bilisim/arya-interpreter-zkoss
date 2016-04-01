package tr.com.agem.arya.interpreter.component;

import org.xml.sax.Attributes;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.West;

import tr.com.agem.arya.interpreter.components.base.AryaMain;
import tr.com.agem.arya.interpreter.script.ScriptHelper;
import tr.com.agem.arya.metadata.interpreter.IAryaComponent;
import tr.com.agem.core.utils.AryaUtils;

public class AryaWest extends West implements IAryaComponent {

	private static final long serialVersionUID = -207082899880288071L;

	private String componentClassName;

	public AryaWest(final AryaMain main, Attributes attributes) {

		super();

		this.setId(attributes.getValue("id"));
		this.componentClassName = attributes.getValue("class");

		if (attributes.getValue("visible") != null) {
			this.setVisible(Boolean.parseBoolean(attributes.getValue("visible")));
		}
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
		this.setTitle(attributes.getValue("title"));
		this.setClass(attributes.getValue("class"));
		this.setBorder(attributes.getValue("border"));
		if (attributes.getValue("size").contains("%")) {
			this.setSize(attributes.getValue("size"));
		} else {
			this.setSize(attributes.getValue("size") + "%");
		}
		if (attributes.getValue("maxsize") != null) {
			this.setMaxsize(Integer.parseInt(attributes.getValue("maxsize")));
		}
		if (attributes.getValue("collapsible") != null) {
			this.setCollapsible(Boolean.parseBoolean(attributes.getValue("collapsible")));
		}
		if (attributes.getValue("splittable") != null) {
			this.setSplittable(Boolean.parseBoolean(attributes.getValue("splittable")));
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
	public Object getComponentParent() {
		return this.getComponentParent();
	}

	@Override
	public String getComponentTagName() {
		return "west";
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
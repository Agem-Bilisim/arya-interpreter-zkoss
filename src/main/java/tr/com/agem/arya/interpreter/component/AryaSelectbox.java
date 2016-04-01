package tr.com.agem.arya.interpreter.component;

import org.xml.sax.Attributes;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Selectbox;

import tr.com.agem.arya.interpreter.components.base.AryaMain;
import tr.com.agem.arya.interpreter.script.ScriptHelper;
import tr.com.agem.arya.metadata.interpreter.IAryaComponent;
import tr.com.agem.core.utils.AryaUtils;

public class AryaSelectbox extends Selectbox implements IAryaComponent {

	private static final long serialVersionUID = 2051047315627309416L;
	
	private String componentClassName;
	private String database;
	private String attribute;
	private String attributeValue;
	private String attributeLabel;

	public AryaSelectbox(final AryaMain main, Attributes attributes) {

		super();

		if (AryaUtils.isNotEmpty(attributes)) {
			this.setId(attributes.getValue("id"));
			this.componentClassName = attributes.getValue("class");

			this.setClass(attributes.getValue("class"));
			this.setVisible(Boolean.parseBoolean(attributes.getValue("visible")));
			this.setDisabled(Boolean.parseBoolean(attributes.getValue("disabled")));
			this.setTooltiptext(attributes.getValue("tooltiptext"));
			this.setDraggable(attributes.getValue("draggable"));
			this.setDroppable(attributes.getValue("droppable"));
			this.setFocus(Boolean.parseBoolean(attributes.getValue("focus")));
			
			if(attributes.getValue("name") != null)
				this.setName(attributes.getValue("name"));

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
			
			this.database = attributes.getValue("database");
			this.attribute = attributes.getValue("attribute");
			this.attributeValue = attributes.getValue("attributeValue");
			this.attributeLabel = attributes.getValue("attributeLabel");


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
	public Object getComponentParent() {
		return this.getComponentParent();
	}

	@Override
	public String getComponentTagName() {
		return "selectbox";
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
package tr.com.agem.arya.interpreter.component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.xml.sax.Attributes;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Datebox;

import tr.com.agem.arya.interpreter.components.base.AryaMain;
import tr.com.agem.arya.interpreter.script.ScriptHelper;
import tr.com.agem.arya.metadata.interpreter.IAryaComponent;
import tr.com.agem.core.utils.AryaUtils;

public class AryaDatebox extends Datebox implements IAryaComponent {

	private static final long serialVersionUID = 5090052412080219262L;

	private String componentClassName;
	private String database;

	public AryaDatebox(final AryaMain main, Attributes attributes) {
		super();

		if (AryaUtils.isNotEmpty(attributes)) {
			this.setId(attributes.getValue("id"));
			this.componentClassName = attributes.getValue("class");

			this.setClass(attributes.getValue("class"));
			if (attributes.getValue("visible") != null) {
				this.setVisible(Boolean.parseBoolean(attributes.getValue("visible")));
			}
			if (attributes.getValue("disabled") != null) {
				this.setDisabled(Boolean.parseBoolean(attributes.getValue("disabled")));
			}
			if (attributes.getValue("readonly") != null) {
				this.setReadonly(Boolean.parseBoolean(attributes.getValue("readonly")));
			}
			if (attributes.getValue("maxlength") != null) {
				this.setMaxlength(Integer.parseInt(attributes.getValue("maxlength")));
			}
			if (attributes.getValue("tabindex") != null) {
				this.setTabindex(Integer.parseInt(attributes.getValue("tabindex")));
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
			
			if (attributes.getValue("buttonVisible") != null)
				this.setButtonVisible(Boolean.parseBoolean(attributes.getValue("buttonVisible")));
			
			if (attributes.getValue("open") != null)
				this.setOpen(Boolean.parseBoolean(attributes.getValue("open")));
			
			if (attributes.getValue("style") != null)
				this.setStyle(attributes.getValue("style"));
			
			if (attributes.getValue("lenient") != null)
				this.setLenient(Boolean.parseBoolean(attributes.getValue("lenient")));
			
			if (attributes.getValue("buttonVisible") != null)
				this.setButtonVisible(Boolean.parseBoolean(attributes.getValue("buttonVisible")));
			
			this.database = attributes.getValue("database");

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

			if (AryaUtils.isNotEmpty(attributes.getValue("onChange"))) {
				final String functionName = attributes.getValue("onChange");
				this.addEventListener("onChange", new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						ScriptHelper.executeScript(functionName, null, main);
					}
				});
			}
			if (AryaUtils.isNotEmpty(attributes.getValue("onChanging"))) {
				final String functionName = attributes.getValue("onChanging");
				this.addEventListener("onChanging", new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						ScriptHelper.executeScript(functionName, null, main);
					}
				});
			}
			if (AryaUtils.isNotEmpty(attributes.getValue("onFocus"))) {
				final String functionName = attributes.getValue("onFocus");
				this.addEventListener("onFocus", new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						ScriptHelper.executeScript(functionName, null, main);
					}
				});
			}
			if (AryaUtils.isNotEmpty(attributes.getValue("onBlur"))) {
				final String functionName = attributes.getValue("onBlur");
				this.addEventListener("onBlur", new EventListener<Event>() {
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
		
		SimpleDateFormat f = new SimpleDateFormat("dd/mm/yyyy");
		
		return f.format(this.getValue());
	}

	@Override
	public void setComponentValue(String componentValue) {
		// TODO dateformat mevzuu
		if (componentValue != null && !componentValue.equals("null") && !componentValue.equals("")) {
			SimpleDateFormat f = new SimpleDateFormat("dd/mm/yyyy");
			Date d = null;
			try {
				d = f.parse(componentValue); 
			} catch (ParseException e) {
				e.printStackTrace();
			}
			long milliseconds = d.getTime();
			this.setValue(new Date(milliseconds));
		}
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
		return "datebox";
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
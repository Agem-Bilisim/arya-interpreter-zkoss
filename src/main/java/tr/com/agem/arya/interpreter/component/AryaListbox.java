package tr.com.agem.arya.interpreter.component;

import java.util.List;

import org.xml.sax.Attributes;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import tr.com.agem.arya.interpreter.components.base.AryaMain;
import tr.com.agem.arya.interpreter.script.ScriptHelper;
import tr.com.agem.arya.metadata.interpreter.IAryaComponent;
import tr.com.agem.arya.metadata.interpreter.IAryaTemplate;
import tr.com.agem.core.utils.AryaUtils;

public class AryaListbox extends Listbox implements IAryaComponent, IAryaTemplate {

	private static final long serialVersionUID = 5336801586396485340L;

	private String componentClassName;
	private String database;
	private String attribute;
	private String attributeValue;
	private String attributeLabel;
	private AryaTemplate template;
	
	public AryaListbox(final AryaMain main, Attributes attributes) {
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

			/*if (attributes.getValue("selectedItem") != null) {
				Listitem listItem = new Listitem(attributes.getValue("selectedItem"));
				this.setSelectedItem(listItem);
			}*/

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
			// TODO unit checking will be fixed
			this.setHeight(attributes.getValue("height"));
			this.setWidth(attributes.getValue("width"));
			
			this.setMold(attributes.getValue("mold"));
			this.setMultiple(Boolean.parseBoolean(attributes.getValue("multiple")));
			this.setCheckmark(Boolean.parseBoolean(attributes.getValue("checkmark")));
			
			this.database = attributes.getValue("database");
			this.attribute = attributes.getValue("attribute");
			this.attributeValue = attributes.getValue("attributeValue");
			this.attributeLabel = attributes.getValue("attributeLabel");

			
			if(attributes.getValue("rows") != null) //TODO number value exception
				this.setRows(new Integer (attributes.getValue("rows")));
			
			if(attributes.getValue("name") != null)
				this.setName(attributes.getValue("name"));
			
			if(attributes.getValue("pagesize") != null) //TODO number value exception
				this.setPageSize(new Integer (attributes.getValue("pagesize")));
			
			if (attributes.getValue("checkmark") != null)
				this.setCheckmark(Boolean.parseBoolean(attributes.getValue("checkmark")));
			
			if(attributes.getValue("span") != null)
				this.setSpan(attributes.getValue("span"));

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
		return this.getSelectedItem().getValue();
	}

	@Override
	public void setComponentValue(String componentValue) {
		List<Listitem> items = this.getItems();
		for (Listitem item : items) {
			if (item.getValue().equals(componentValue)) {
				this.setSelectedItem(item);			
			}
		}
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
	public String getComponentTagName() {
		return "listbox";
	}

	@Override
	public Object getComponentParent() {
		return this.getComponentParent();
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

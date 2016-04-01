package tr.com.agem.arya.interpreter.command;

import org.xml.sax.Attributes;

import tr.com.agem.arya.interpreter.components.base.AryaMain;
import tr.com.agem.arya.metadata.interpreter.IAryaCommand;
import tr.com.agem.arya.metadata.interpreter.IAryaComponent;
import tr.com.agem.core.utils.AryaUtils;

public class AryaFill implements IAryaCommand, IAryaComponent {
	
	private String componentClassName;
	private String componentAttribute;
	private IAryaComponent parent;
	private String name;
	private String var;
	private String from;
	private String to;
	private String value;
	private String param;
	
	public AryaFill(final AryaMain main, Attributes attributes) {
		super();
		
		if (AryaUtils.isNotEmpty(attributes)) {
			if (AryaUtils.isNotEmpty(attributes)) {
				this.componentClassName = attributes.getValue("class");
				this.componentAttribute = attributes.getValue("attribute");
				
				this.name = attributes.getValue("name");
				this.var = attributes.getValue("var");
				
				this.from = attributes.getValue("from");
				this.to = attributes.getValue("to");
				this.value = attributes.getValue("value");
				this.param = attributes.getValue("param");

			}
		}
	}
	
	public String getComponentClassName() {
		return componentClassName;
	}

	public void setComponentClassName(String componentClassName) {
		this.componentClassName = componentClassName;
	}

	public String getComponentAttribute() {
		return componentAttribute;
	}

	public void setComponentAttribute(String componentAttribute) {
		this.componentAttribute = componentAttribute;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
	
	@Override
	public void setComponentId(String componentId) {
	}

	@Override
	public String getComponentId() {
		return null;
	}

	@Override
	public void setComponentValue(String componentValue) {
	}

	@Override
	public String getComponentValue() {
		return null;
	}

	@Override
	public String validate() {
		return null;
	}
	
	public IAryaComponent getParent() {
		return parent;
	}

	public void setParent(IAryaComponent parent) {
		this.parent = parent;
	}

	@Override
	public void setComponentParent(Object parent) {
		this.setParent((IAryaComponent)parent);
		
	}

	@Override
	public Object getComponentParent() {
		return this.getComponentParent();
	}
	
	public String getComponentTagName() {
		return "fill";
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

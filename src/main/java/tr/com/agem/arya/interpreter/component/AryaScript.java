package tr.com.agem.arya.interpreter.component;

import java.util.Arrays;
import java.util.List;

import org.xml.sax.Attributes;
import org.zkoss.zul.Script;

import tr.com.agem.arya.interpreter.components.base.AryaMain;
import tr.com.agem.arya.metadata.interpreter.IAryaComponent;
import tr.com.agem.core.utils.AryaUtils;

public class AryaScript extends Script implements IAryaComponent {

	private static final long serialVersionUID = 9120641871916430134L;

	private String script;
	private List<String> srcList;

	public AryaScript(AryaMain main, Attributes attributes) {
		// External js files can be specified by 'src' attribute
		String sources = attributes.getValue("src");
		if (AryaUtils.isNotEmpty(sources)) {
			this.srcList = Arrays.asList(sources.split(";"));
		}
	}

	@Override
	public void setComponentParent(Object parent) {
	}

	@Override
	public String validate() {
		return null;
	}

	@Override
	public void setComponentId(String componentId) {
		this.setId(componentId);
	}

	@Override
	public String getComponentId() {
		return this.getId();
	}

	@Override
	public void setComponentClassName(String componentClassName) {
	}

	@Override
	public String getComponentClassName() {
		return null;
	}

	@Override
	public void setComponentValue(String componentValue) {
	}

	@Override
	public String getComponentValue() {
		return null;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public List<String> getSrcList() {
		return srcList;
	}

	public void setSrcList(List<String> srcList) {
		this.srcList = srcList;
	}
	
	@Override
	public Object getComponentParent() {
		return this.getComponentParent();
	}

	@Override
	public String getComponentTagName() {
		return "script";
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

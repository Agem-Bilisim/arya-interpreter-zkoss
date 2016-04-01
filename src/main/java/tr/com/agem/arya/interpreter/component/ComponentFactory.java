package tr.com.agem.arya.interpreter.component;

import org.xml.sax.Attributes;

import tr.com.agem.arya.interpreter.command.AryaFill;
import tr.com.agem.arya.interpreter.component.menu.AryaMenu;
import tr.com.agem.arya.interpreter.component.menu.AryaMenuBar;
import tr.com.agem.arya.interpreter.component.menu.AryaMenuItem;
import tr.com.agem.arya.interpreter.component.menu.AryaMenuPopUp;
import tr.com.agem.arya.interpreter.components.base.AryaMain;
import tr.com.agem.arya.metadata.interpreter.IAryaComponent;

public class ComponentFactory {

	public static IAryaComponent getComponent(String tagName, AryaMain main, Attributes attributes) {

		IAryaComponent comp = null;

		if ("label".equalsIgnoreCase(tagName)) {
			comp = new AryaLabel(main, attributes);
		} else if ("textbox".equalsIgnoreCase(tagName)) {
			comp = new AryaTextbox(main, attributes);
		} else if ("checkbox".equalsIgnoreCase(tagName)) {
			comp = new AryaCheckbox(main, attributes);
		} else if ("datebox".equalsIgnoreCase(tagName)) {
			comp = new AryaDatebox(main, attributes);
		} else if ("button".equalsIgnoreCase(tagName)) {
			comp = new AryaButton(main, attributes);
		} else if ("textbox".equalsIgnoreCase(tagName)) {
			comp = new AryaTextbox(main, attributes);
		} else if ("doublebox".equalsIgnoreCase(tagName)) {
			comp = new AryaTextbox(main, attributes);
		} else if ("listbox".equalsIgnoreCase(tagName)) {
			comp = new AryaListbox(main, attributes);
		} else if ("listitem".equalsIgnoreCase(tagName)) {
			comp = new AryaListItem(main, attributes);
		} else if ("combobox".equalsIgnoreCase(tagName)) {
			comp = new AryaCombobox(main, attributes);
		} else if ("multipleCombobox".equalsIgnoreCase(tagName)) {
			comp = new AryaMultipleCombobox(main, attributes);
		} else if ("comboitem".equalsIgnoreCase(tagName)) {
			comp = new AryaComboItem(main, attributes);
		} else if ("mcomboitem".equalsIgnoreCase(tagName)) {
			comp = new AryaMultiComboItem(main, attributes);
		} else if ("script".equalsIgnoreCase(tagName)) {
			comp = new AryaScript(main, attributes);
		} else if ("listhead".equalsIgnoreCase(tagName)) {
			comp = new AryaListHead(main, attributes);
		} else if ("listheader".equalsIgnoreCase(tagName)) {
			comp = new AryaListHeader(main, attributes);
		} else if ("listcell".equalsIgnoreCase(tagName)) {
			comp = new AryaListCell(main, attributes);
		} else if ("grid".equalsIgnoreCase(tagName)) {
			comp = new AryaGrid(main, attributes);
		} else if ("columns".equalsIgnoreCase(tagName)) {
			comp = new AryaColumns(main, attributes);
		} else if ("column".equalsIgnoreCase(tagName)) {
			comp = new AryaColumn(main, attributes);
		} else if ("rows".equalsIgnoreCase(tagName)) {
			comp = new AryaRows(main, attributes);
		} else if ("row".equalsIgnoreCase(tagName)) {
			comp = new AryaRow(main, attributes);
		} else if ("menubar".equalsIgnoreCase(tagName)) {
			comp = new AryaMenuBar(main, attributes);
		} else if ("menu".equalsIgnoreCase(tagName)) {
			comp = new AryaMenu(main, attributes);
		} else if ("menupopup".equalsIgnoreCase(tagName)) {
			comp = new AryaMenuPopUp(main, attributes);
		} else if ("menuitem".equalsIgnoreCase(tagName)) {
			comp = new AryaMenuItem(main, attributes);
		} else if ("calendar".equalsIgnoreCase(tagName)) {
			comp = new AryaCalendar(main, attributes);
		} else if ("radiogroup".equalsIgnoreCase(tagName)) {
			comp = new AryaRadiogroup(main, attributes);
		} else if ("borderlayout".equalsIgnoreCase(tagName)) {
			comp = new AryaBorderlayout(main, attributes);
		} else if ("west".equalsIgnoreCase(tagName)) {
			comp = new AryaWest(main, attributes);
		} else if ("north".equalsIgnoreCase(tagName)) {
			comp = new AryaNorth(main, attributes);
		} else if ("east".equalsIgnoreCase(tagName)) {
			comp = new AryaEast(main, attributes);
		} else if ("south".equalsIgnoreCase(tagName)) {
			comp = new AryaSouth(main, attributes);
		} else if ("center".equalsIgnoreCase(tagName)) {
			comp = new AryaCenter(main, attributes);
		} else if ("spinner".equalsIgnoreCase(tagName)) {
			comp = new AryaSpinner(main, attributes);
		} else if ("radio".equalsIgnoreCase(tagName)) {
			comp = new AryaRadio(main, attributes);
		} else if ("timebox".equalsIgnoreCase(tagName)) {
			comp = new AryaTimebox(main, attributes);
		} else if ("decimalbox".equalsIgnoreCase(tagName)) {
			comp = new AryaDecimalbox(main, attributes);
		} else if ("doublebox".equalsIgnoreCase(tagName)) {
			comp = new AryaDoublebox(main, attributes);
		} else if ("doublespinner".equalsIgnoreCase(tagName)) {
			comp = new AryaDoublespinner(main, attributes);
		} else if ("slider".equalsIgnoreCase(tagName)) {
			comp = new AryaSlider(main, attributes);
		} else if ("hlayout".equalsIgnoreCase(tagName)) {
			comp = new AryaHlayout(main, attributes);
		} else if ("vlayout".equalsIgnoreCase(tagName)) {
			comp = new AryaVlayout(main, attributes);
		} else if ("progressmeter".equalsIgnoreCase(tagName)) {
			comp = new AryaProgressmeter(main, attributes);
		} else if ("selectbox".equalsIgnoreCase(tagName)) {
			comp = new AryaSelectbox(main, attributes);
		} else if ("div".equalsIgnoreCase(tagName)) {
			comp = new AryaDiv(main, attributes);
		} else if ("template".equalsIgnoreCase(tagName)) {
			comp = new AryaTemplate(main, attributes);
		} else if ("tabbox".equalsIgnoreCase(tagName)) {
			comp = new AryaTabbox(main, attributes);
		} else if ("tabs".equalsIgnoreCase(tagName)) {
			comp = new AryaTabs(main, attributes);
		} else if ("tab".equalsIgnoreCase(tagName)) {
			comp = new AryaTab(main, attributes);
		} else if ("tabpanels".equalsIgnoreCase(tagName)) {
			comp = new AryaTabpanels(main, attributes);
		} else if ("tabpanel".equalsIgnoreCase(tagName)) {
			comp = new AryaTabpanel(main, attributes);
		} else if ("intbox".equalsIgnoreCase(tagName)) {
			comp = new AryaIntbox(main, attributes);
		} else if ("fill".equalsIgnoreCase(tagName)) {
			comp = new AryaFill(main, attributes);
		} else if ("chart".equalsIgnoreCase(tagName)) {
			comp = new AryaFlashchart(main,attributes);
		} else if ("auxhead".equalsIgnoreCase(tagName)) {
			comp = new AryaAuxHead(main,attributes);
		} else if ("auxheader".equalsIgnoreCase(tagName)) {
			comp = new AryaAuxHeader(main,attributes);
		} else if ("foot".equalsIgnoreCase(tagName)) {
			comp = new AryaFoot(main,attributes);
		} else if ("footer".equalsIgnoreCase(tagName)) {
			comp = new AryaFooter(main,attributes);
		} else if ("image".equalsIgnoreCase(tagName)) {
			comp = new AryaImage(main,attributes);
		}
		
		return comp;
	}

}

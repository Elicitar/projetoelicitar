package xmlStruct;

import org.w3c.dom.Element;

public class XmlControlComponent extends XmlControlBase{
	Element raiz = null;
	public Element getRaiz() {
		return raiz;
	}

	public XmlControlComponent() {
		super();
	}
	
	public Element createNewForm() {		
		raiz = super.createNewDocumentFrom("Formulario");
		return raiz;
	}
	
	
}

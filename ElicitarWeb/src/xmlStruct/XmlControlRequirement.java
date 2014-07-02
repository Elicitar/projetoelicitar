package xmlStruct;

import org.w3c.dom.Element;

public class XmlControlRequirement extends XmlControlBase{
	
	public XmlControlRequirement() {
		super();
	}

	public Element createNewDocumentFrom() {		
		raiz = super.createNewDocumentFrom("Requisitos");
		return raiz;
	}
	
	public Element addNewSection(Element nopai, String nome){
		Element el = super.addNewSection(nopai, nome);
		return el;
		
	}

	public Element CreateNewRequirement(String nome, Element raiz) {
		Element el = super.addNewSection(raiz, "requisito");
		super.addSingleNodeText(el, "nome", nome);
		return el;
	}
	
	public void addType(Element requisito, String tipoDeRequisito){
		super.addSingleNodeText(requisito, "TipoDeRequisito", tipoDeRequisito);	}
	
	public Element CreateDescriptionList(Element requisito) {
		Element el = super.addNewSection(requisito, "descricoes");	
		return el;
	}
	
	
	public void addDescription(Element descricoes, String descricao){
		super.addSingleNodeText(descricoes, "descricao", descricao);	
	}
	
	public void addComponentType(Element requisito, String componentType){
		super.addSingleNodeText(requisito, "tipocomponente", componentType);
	}

	
	public void addObrigatorio(Element requisito, boolean isObr){
		if(isObr){
			super.addSingleNodeText(requisito, "obrigatorio", "sim");
		}else{
			super.addSingleNodeText(requisito, "obrigatorio", "nao");
		}
	}
	
}

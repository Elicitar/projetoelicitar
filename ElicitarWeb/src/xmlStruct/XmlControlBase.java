package xmlStruct;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import exceptions.TratadaorDeExcecao;

public class XmlControlBase {

	
	Element raiz = null;
	private Document documento;

	public Element getRaiz() {
		return raiz;
	}

	public XmlControlBase() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			documento = builder.newDocument();
		} catch (ParserConfigurationException e) {
			TratadaorDeExcecao.vaiParaExcecao("Erro inicializando o controller XML. Detalhe: "+e.getMessage(), e);
		}

	}

	public Element createNewDocumentFrom(String nomeRaiz) {		
		raiz = (Element) documento.createElement(nomeRaiz);
		documento.appendChild(raiz);
		return raiz;
	}
	
	public Element addNewSection(Element nopai, String nome){
		Element el = (Element) documento.createElement(nome);
		nopai.appendChild(el);
		return el;
		
	}

	public void addSingleNodeText(Element noPai, String nome, String valor){
		Element el = (Element) documento.createElement(nome);
		el.appendChild(documento.createTextNode(valor));
		noPai.appendChild( el );
	}
	
	public void geraXML(String nomeArquivo) throws Exception {
        Transformer transXML = TransformerFactory.newInstance().newTransformer();
        transXML.setOutputProperty("indent", "yes");
        /**
         * Atenção.. a linha abaixo faz com que este código funcione em Unix e não em Windows... Quando usamos iso-8859-1 funciona em windows,
         * mas ao utilizar o servidor LInux irá¡ aparecer caracteres "estranhos" por causa da acentuação. É necesário descobrir futuramente como resolver isto.
         * Estou colocando em UTF-8 por estar utilizando servidor da Amazon Webservices para publicar para usuários.
         * */
        
        transXML.setOutputProperty("encoding", "UTF-8");
        transXML.transform(new DOMSource( documento ), new StreamResult(new FileWriter(nomeArquivo)));
    }
	
	public void loadXmlFromFile(String file) throws ParserConfigurationException, SAXException, IOException{
		File fXmlFile = new File(file);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document documento = dBuilder.parse(fXmlFile);
	 
		//optional, but recommended
		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		documento.getDocumentElement().normalize();
	 
	}
}

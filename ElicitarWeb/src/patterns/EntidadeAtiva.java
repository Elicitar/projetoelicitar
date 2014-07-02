package patterns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import requirements.RequisitoBase;

public class EntidadeAtiva extends AbstractPattern{
	
	public Map<Integer, AbstractPattern> getSequence() {
		return sequence;
	}

	ArrayList<TipoDeDado> tiposDeDados = new ArrayList<TipoDeDado>();
	ArrayList<EstruturaDeDado> estruturaDeDados = new ArrayList<EstruturaDeDado>();
	Map<Integer, AbstractPattern> sequence = new HashMap<Integer, AbstractPattern>();

	public EntidadeAtiva(RequisitoBase req) {
		super(req);
		// TODO Auto-generated constructor stub
	}

	public boolean usaTipoDeDado(TipoDeDado td) {
		Pattern padrao = Pattern.compile(td.getRequisito().getNome(), Pattern.CASE_INSENSITIVE);  
		Matcher pesquisa = padrao.matcher(getRequisito().getFullDesc()); 
		if (pesquisa.find()) 
			return true;
		
		return false;
	}
	
	public ArrayList<TipoDeDado> getTiposDeDados() {
		return tiposDeDados;
	}

	public ArrayList<EstruturaDeDado> getEstruturaDeDados() {
		return estruturaDeDados;
	}

	public boolean usaEstruturaDeDado(EstruturaDeDado ed) {
		String busca = ed.getRequisito().getNome();
		String texto = getRequisito().getFullDesc();
		return internalUsaPattern(busca, texto);		
	}

	private boolean internalUsaPattern(String busca, String texto){
		Pattern padrao = Pattern.compile(busca, Pattern.CASE_INSENSITIVE);  
		Matcher pesquisa = padrao.matcher(texto); 
		if (pesquisa.find()) 
			return true;
		
		return false;
	}
	
	
	public void addTipoDeDado(TipoDeDado td) {
		String desc;
		RequisitoBase req;
		tiposDeDados.add(td);		
		req = getRequisito();
		//identifica a posição do requisito na descrição; Necessário para que os campos sejam gerados na sequencia descrita
		//for(int i = 0; i < req.getDescriptionSize(); i++){
			desc = req.getFullDesc();
			if(internalUsaPattern(td.getRequisito().getNome(), desc)){
				sequence.put(desc.indexOf(td.getRequisito().getNome()), td);
			}
		//}
	}
	
	public void addEstruturaDeDado(EstruturaDeDado ed) {
		String desc;
		RequisitoBase req;
		
		estruturaDeDados.add(ed);		
		req = getRequisito();
		//identifica a posição do requisito na descrição; Necessário para que os campos sejam gerados na sequencia descrita
		//for(int i = 0; i < req.getDescriptionSize(); i++){
			desc = req.getFullDesc();
			if(internalUsaPattern(ed.getRequisito().getNome(), desc)){
				sequence.put(desc.indexOf(ed.getRequisito().getNome()), ed);
			}
		//}
	}
}

package patterns;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import requirements.RequisitoBase;

public class EstruturaDeDado extends AbstractPattern implements IPattern{

	ArrayList<TipoDeDado> tiposDeDados = new ArrayList<TipoDeDado>();
	
	public EstruturaDeDado(RequisitoBase req) {
		super(req);
		// TODO Auto-generated constructor stub
	}
	
	public void addTipoDeDado(TipoDeDado tipodado){
		tiposDeDados.add(tipodado);
	}

	public boolean usaTipoDeDado(TipoDeDado td) {
		String morfName = td.getMorfologicalSubj();
		String morfDesc = getMorfologicalFullDescriptions();
		
		if(morfName == null){
			System.out.println(td.getRequisito().getNome()+" gera nome morfológico nulo ");
		}else{
		Pattern padrao = Pattern.compile(morfName, Pattern.CASE_INSENSITIVE);  
		
		Matcher pesquisa = padrao.matcher(morfDesc); 
		if (pesquisa.find()) 
			return true;
		}
		return false;
	}

	public ArrayList<TipoDeDado> getTiposDeDados() {
		return tiposDeDados;
	}

}

package requirements;
/**Classe para objetos do tipo Requisitos, onde serão contidos, valores e métodos para o mesmo.

 * @author Leandro Vilson Battisti

 * @version 1.00

 * @since Release inicial

 */


	
import java.util.ArrayList;

import patterns.ExtractMorfologicalInfo;

public class RequisitoBase implements IRequisito {
	

	private String nome = "";
	private String objetivo = "";
	private ArrayList<String> decricao = new ArrayList<String>();
	private String tipoRequisitoStr;
	private int globalId;
	private String morfName;
	private String morfDesc;
	
	/** M�todo para retorno morfol�gico do nome
	 * @return String nome morfol�gico do requisito
	 * */
	public String getMorfName() {
		return morfName;
	}

	/** M�todo para retorno morfol�gica da descri��o
	 * @return String descri��o morfol�gica do requisito
	 * */
	
	public String getMorfDesc() {
		return morfDesc;
	}

	public void setMorfName(String morfName) {
		this.morfName = morfName;
	}

	public ArrayList<String> getDecricao() {
		return decricao;
	}
	

	public void setDecricao(ArrayList<String> decricao) {
		this.decricao = decricao;
	}
	
	public int getGlobalId() {
		return globalId;
	}

	public String getTipoRequisitoStr() {
		return tipoRequisitoStr;
	}

	public void setTipoRequisitoStr(String texto) {
		this.tipoRequisitoStr = texto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
		morfName = ExtractMorfologicalInfo.getInstance().getMorfCaracteristicasFromText(nome);
	}

	public RequisitoBase(String nome, int globalid) {
		this.nome = nome;
	}

	public RequisitoBase() {
		// TODO Auto-generated constructor stub
	}

	public void setGlobalId(int globalId) {
		this.globalId = globalId;
	}

	@Override
	public String toString() {
		String res;
		res = "Nome: -> " + nome + "\n";
		res += "Tipo: -> " + tipoRequisitoStr + "\n";
		res = "Objetivo: -> " + objetivo + "\n";
		res += "Descri��o: -> " + decricao + "\n";
		res += "**********************************************************************";
		return res; 
	}
	
	public String getFullDesc(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i<decricao.size();i++){
			sb.append(decricao.get(i) + " ");
		}
		String ret = sb.toString();
		if(morfDesc == ""){
			morfDesc = ExtractMorfologicalInfo.getInstance().getMorfCaracteristicasFromText(ret);
		}
		return ret;
	}

	public void addDecricao(String texto) {
		decricao.add(texto);
		morfDesc = "";//limpa pois tem que for�ar a regerar um novo texto morfol�gico quando alterar a descri��o
	}

	public String getDecricaoByIndex(int index) {
		return decricao.get(index);
	}
	
	public int getDescriptionSize(){
		return decricao.size();
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

}

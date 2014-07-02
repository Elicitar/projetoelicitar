package beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import requirementExtract.StaticBase;
import requirements.ConstsRequisitos;
import requirements.RequisitoBase;
import requirements.TipoDeRequisito;
import db.ReqDB;
import exceptions.TipoDeReqisitoInvalidoException;
import exceptions.TratadaorDeExcecao;

@ManagedBean
public class CadastrarRequisitoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	RequisitoBase actualReq = new RequisitoBase();
	String iniDesc;
	private RequisitoBase[] selectedReqs;
	private String tipoSelecionado;
	
	public String getDesc() {
		return this.actualReq.getFullDesc();
	}

	public RequisitoBase getActualReq() {
		return this.actualReq;
	}

	public void setActualReq(RequisitoBase actualReq) {
		RequisitoBase r = StaticBase.tratadorDeArquivo.getRequisitoByName(actualReq.getNome());		
		this.actualReq = r;
	}

	public String getIniDesc() {
		return iniDesc;
	}

	public void setIniDesc(String iniDesc) {
		this.iniDesc = iniDesc;
	}

	public String getTipoSelecionado() {
		return this.actualReq.getTipoRequisitoStr();
	}

	public void setTipoSelecionado(String tipoSelecionado) {
		this.tipoSelecionado = tipoSelecionado;
 		this.actualReq.setTipoRequisitoStr(tipoSelecionado);
	}

	public ArrayList<RequisitoBase> getRequisitos() {
		return StaticBase.tratadorDeArquivo.getRequisitos();
	}

	public String[] getTipoRequisitos() {
		return TipoDeRequisito.getNames();
	}

	public CadastrarRequisitoBean() {
		// actualReq = new RequisitoBase();
	}

	
	public void onChangeTipo(){
		TipoDeRequisito tipo = null;
		try {
			tipo = TipoDeRequisito.getRequirementType(tipoSelecionado);
		} catch (TipoDeReqisitoInvalidoException e1) {
			TratadaorDeExcecao.vaiParaExcecao(String.format(
					"Não foi possível identificar o tipo %s definido", tipoSelecionado),
					e1);
			e1.printStackTrace();
		}
		if ((tipo != TipoDeRequisito.tdrTipoDeDado)
				&& (tipo != TipoDeRequisito.tdrEstruturaDeDado)
				&& (tipo != TipoDeRequisito.tdrEntidadeAtiva)) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Erro na seleção de tipo", tipoSelecionado
									+ " é um tipo não suportado"));
		}
		this.actualReq.setTipoRequisitoStr(tipoSelecionado);
		if (this.actualReq.getFullDesc().isEmpty()) {
			this.actualReq.getDecricao()
					.add(ConstsRequisitos.getTemplate(tipo));
		}

	}

	public void setDesc(String str) {
		actualReq.addDecricao(str);
	}

	public String salvar() throws Exception {
		if(actualReq.getNome().isEmpty()){
			throw new Exception("Nome deve ser informado");
		}
		
		String name = actualReq.getNome();
		RequisitoBase r = null;
		if (name != null) {
			r = StaticBase.tratadorDeArquivo.getRequisitoByName(name);
			if (r != null){
				r.assign(actualReq);
			}
			ReqDB.getInstance().addReqToDB(actualReq);			
			
		}
		return "cadSucessfull.jsf";
	}

	public RequisitoBase[] getSelectedReqs() {
		return selectedReqs;
	}

	public void setSelectedReqs(RequisitoBase[] selectedReqs) {
		this.selectedReqs = selectedReqs;
	}

	public void excluirRequisito() {
		ReqDB.getInstance().removeFromDB(actualReq);
		StaticBase.tratadorDeArquivo.getRequisitos().remove(actualReq);
		actualReq = null;
		actualReq = new RequisitoBase();
	}

}

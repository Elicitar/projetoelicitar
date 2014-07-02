import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import db.ReqDB;
import exceptions.TipoDeReqisitoInvalidoException;
import exceptions.TratadaorDeExcecao;
import requirementExtract.StaticBase;
import requirementExtract.TratadorArquivo;
import requirements.ConstsRequisitos;
import requirements.RequisitoBase;
import requirements.TipoDeRequisito;


public class MainExcluir {

	public static void main(String[] args) {
		RequisitoBase actualReq = new RequisitoBase();
		TratadorArquivo t = StaticBase.tratadorDeArquivo;
		
		String tipoSelecionado = "Tipo de dado";
		actualReq.setTipoRequisitoStr(tipoSelecionado );
		
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
		if (actualReq.getFullDesc().isEmpty()) {
			actualReq.getDecricao()
					.add(ConstsRequisitos.getTemplate(tipo));
		}
		
		actualReq.setNome("Nome teste");
		actualReq.setObjetivo("Objetivo qualquer");
	
		
		String name = actualReq.getNome();
		if (name != null) {
			if (t.getRequisitoByName(name) == null)
				t.getRequisitos().add(actualReq);
			ReqDB.getInstance().addReqToDB(actualReq);
		}
		
		
	}

}

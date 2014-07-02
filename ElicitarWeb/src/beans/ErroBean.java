package beans;

import java.io.IOException;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import mail.SendMails;

@ManagedBean
public class ErroBean {
	String nomeAnalista;
	String emailAnalista;
	String feedback;
	String dadosMensagem;
	String solicitacao;
	String identificador;
	
	public String getSolicitacao() {
		Map<String, String> parameterMap = (Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		solicitacao = parameterMap.get("solicitacao");
		identificador = parameterMap.get("identificador");
		dadosMensagem = parameterMap.get("exception");
		return solicitacao;
	}
	
	public String inicio(){
		enviarEmail();
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		try {
			ec.redirect("index.jsf");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return "index.jsf";
	}
	public String enviarEmail(){		
		String textoEnviar = "IDENTIFICADOR: "+identificador+"\nSOLICITAÇÂOO:"+solicitacao+"\nFEEDBACK:\n"+feedback+"\n====Dados originais====\n"+dadosMensagem;
		SendMails.enviarEmail(nomeAnalista, "projetoelicitar@gmail.com", "[Elicitar - Erro] Feedback tela de erro", textoEnviar);
		return "index.jsf";
	}

	public String getEmailAnalista() {
		return emailAnalista;
	}

	public void setEmailAnalista(String emailAnalista) {
		this.emailAnalista = emailAnalista;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getNomeAnalista() {
		return nomeAnalista;
	}

	public void setNomeAnalista(String nomeAnalista) {
		this.nomeAnalista = nomeAnalista;
	}




	

}

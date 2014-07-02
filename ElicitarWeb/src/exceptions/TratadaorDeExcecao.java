package exceptions;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.UUID;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import model.Tblogger;
import db.PersitLogErrosDB;

public class TratadaorDeExcecao {
	
	public static String getStackTrace(Throwable aThrowable) {
	    Writer result = new StringWriter();
	    PrintWriter printWriter = new PrintWriter(result);
	    aThrowable.printStackTrace(printWriter);
	    return result.toString();
	  }
	
	@SuppressWarnings("deprecation")
	public static void vaiParaExcecao(String situacao,  Exception except){
		UUID idOne = UUID.randomUUID();
		String stack = getStackTrace(except);
		Tblogger looger = new Tblogger();
		looger.setIdentificador(idOne.toString());
		looger.setInformations(situacao);
		looger.setStacktrace(stack);
		PersitLogErrosDB.getInstance().addLogsToDB(looger);
		String url = "error.jsf?identificador="+ idOne.toString()+"&solicitacao="+URLEncoder.encode(situacao)+"&exception="+URLEncoder.encode(stack);
		
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		try {
			ec.redirect(url);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}

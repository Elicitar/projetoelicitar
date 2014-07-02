package beans;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import requirementExtract.StaticBase;
import requirements.ConstsRequisitos;
import requirements.RequisitoBase;
import requirements.TipoDeRequisito;
import exceptions.TipoDeReqisitoInvalidoException;
import exceptions.TratadaorDeExcecao;

@ManagedBean
@SessionScoped
public class FileGenBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String formToGen;
	String[] listOfFormsToGen = new String[] {};

	public String getFormToGen() {
		return formToGen;
	}

	public void setFormToGen(String formToGen) {
		this.formToGen = formToGen;
	}

	private String getTemporaryName(String nome, String extensao)
			throws IOException {
		File filelocal = File.createTempFile(nome + "-", "." + extensao);
		String prefix = FilenameUtils.getBaseName(filelocal.getName());
		String suffix = FilenameUtils.getExtension(filelocal.getName());
		String fileName = prefix + suffix;
		return fileName;
	}

	public StreamedContent getGerarJSON() {

		DefaultStreamedContent downfile = null;
		try {
			ServletContext context = FileController.getInstance()
					.getServerContext();

			String fileName = getTemporaryName("formulario", "json");
			String fileToGen = "temps" + File.separator + fileName;
			StaticBase.tratadorDeArquivo.organizar();
			if (formToGen.equals("todos")) {
				StaticBase.tratadorDeArquivo.requisitosToJSon(
						context.getRealPath("/") + fileToGen, null);
			} else {
				StaticBase.tratadorDeArquivo.requisitosToJSon(
						context.getRealPath("/") + fileToGen,
						StaticBase.tratadorDeArquivo.getFormByName(formToGen));
			}

			InputStream stream = context.getResourceAsStream(fileToGen);
			downfile = new DefaultStreamedContent(stream, "text/json",
					"formularios.json");

		} catch (Exception e) {
			TratadaorDeExcecao.vaiParaExcecao("Erro ao gerar JSON. Detalhe: "
					+ e.getMessage(), e);
			e.printStackTrace();
		}
		return downfile;
	}

	public StreamedContent getGerarXML() {
		DefaultStreamedContent downfile = null;
		try {
			ServletContext context = FileController.getInstance()
					.getServerContext();

			String fileName = getTemporaryName("formulario", "xml");
			String fileToGen = "temps" + File.separator + fileName;

			StaticBase.tratadorDeArquivo.organizar();
			if (formToGen.equals("todos")) {
				StaticBase.tratadorDeArquivo.RequisitosToComponents(
						context.getRealPath("/") + fileToGen, null);
			} else {
				StaticBase.tratadorDeArquivo.RequisitosToComponents(
						context.getRealPath("/") + fileToGen,
						StaticBase.tratadorDeArquivo.getFormByName(formToGen));
			}
			InputStream stream = context.getResourceAsStream(fileToGen);
			context.getResource("/");
			downfile = new DefaultStreamedContent(stream, "text/xml",
					"formularios.xml");

		} catch (Exception e) {
			TratadaorDeExcecao.vaiParaExcecao("Erro ao gerar XML. Detalhe: "
					+ e.getMessage(), e);
		}
		return downfile;

	}

	public FileGenBean() {
		formToGen = "Todos";
	}

	public String[] getAllReqForms() throws TipoDeReqisitoInvalidoException {
		ArrayList<RequisitoBase> requisitos = StaticBase.tratadorDeArquivo
				.getRequisitos();
		StringBuilder stringBuilder = new StringBuilder();
		String name;
		if (listOfFormsToGen.length > 0) {
			for (String nomes : listOfFormsToGen) {
				stringBuilder.append(nomes);
				stringBuilder.append("#//#");
			}
		} else {
			stringBuilder.append("todos#//#");
		}

		boolean contem = false;
		boolean adicionou = false;
		for (int i = 0; i < requisitos.size(); i++) {
			try {
				if (!requisitos.get(i).getNome().isEmpty()) {
					if (TipoDeRequisito.getRequirementType(requisitos.get(i)
							.getTipoRequisitoStr()) == TipoDeRequisito.tdrEntidadeAtiva) {
						name = requisitos.get(i).getNome();
						for (String str : listOfFormsToGen) {
							if (str.equals(name)) {
								contem = true;
							}
						}
						if (!contem) {
							stringBuilder.append(name);
							stringBuilder.append("#//#");
							adicionou = true;
						}
						contem = false;
					}
				}
			} catch (TipoDeReqisitoInvalidoException et) {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Erro do tipo de requisito: " + et.getMessage(), null);
				FacesContext.getCurrentInstance().addMessage(null, message);
			} catch (Exception e) {
				TratadaorDeExcecao.vaiParaExcecao(
						"Erro ao buscar informações dos formulários. Detalhe: "
								+ e.getMessage(), e);
			}
		}

		if (adicionou)
			listOfFormsToGen = stringBuilder.toString().split("#//#");
		return listOfFormsToGen;
	}

	public String[] getListOfFormsToGen() {
		return listOfFormsToGen;
	}

	public void setListOfFormsToGen(String[] listOfFormsToGen) {
		this.listOfFormsToGen = listOfFormsToGen;
	}
	
	
	public void onChangeForm(){
		
	}
}

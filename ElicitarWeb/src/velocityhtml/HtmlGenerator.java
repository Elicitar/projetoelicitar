package velocityhtml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import beans.FileController;
import exceptions.TratadaorDeExcecao;

public class HtmlGenerator {

	private BufferedWriter writer;
	SAXBuilder builder;
	Document root = null;
	ArrayList<FormStruct> formsToVelocity = new ArrayList<FormStruct>();

	public void generateFromJSOn(String jsonFile, String destino) {
		/*
		 * setup
		 */

		ServletContext sercontext = FileController.getInstance()
				.getServerContext();

		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH,
				sercontext.getRealPath("/"));
		ve.init();

		// Velocity.init();

		// Velocity.init(sercontext.getRealPath("/")+"configs/velocity.properties");

		/*
		 * Make a context object and populate with the data. This is where the
		 * Velocity engine gets the data to resolve the references (ex. $list)
		 * in the template
		 */

		VelocityContext context = new VelocityContext();
		Template template = null;
		template = ve.getTemplate("templates/htmlFromFormStruct.vm");

		JSONObject jsonObject; // Cria o parse de tratamento JSONParser
		JSONParser parser = new JSONParser(); // Variaveis que irao armazenar os
		// Salva no objeto JSONObject o que o parse tratou do arquivo
		try {
			jsonObject = (JSONObject) parser.parse(new FileReader(jsonFile));
			JSONArray forms = (JSONArray) jsonObject.get("formularios");
			FormStruct fs;
			ComponentStruct cs;
			for (int i = 0; i < forms.size(); i++) {
				JSONObject formTotal = (JSONObject) forms.get(i);
				JSONObject form = (JSONObject) formTotal.get("formulario" + i);
				String titulo = (String) form.get("Titulo");
				JSONObject listacomponentes = (JSONObject) form
						.get("componentes");
				fs = new FormStruct();
				formsToVelocity.add(fs);
				fs.setTitulo(titulo);
				for (int j = 0; j < listacomponentes.size(); j++) {
					JSONObject componente = (JSONObject) listacomponentes
							.get("componente" + j);
					String tipo = (String) componente.get("tipo");
					String nome = (String) componente.get("nome");
					String descricao = (String) componente.get("descricao");
					String obrigatorio = "não";
					if(componente.get("obrigatorio") == "true"){
						obrigatorio = "sim";
					}cs = new ComponentStruct();
					cs.setTipo(tipo);
					cs.setNome(nome);
					cs.setDescricao(descricao);
					cs.setObrigatorio(obrigatorio);
					fs.addComp(cs);
				}
				context.put("form", fs);
				ArrayList<ComponentStruct> componentes = fs.getComponents();
				context.put("componentes", componentes);
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(new File(destino));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				writer = new BufferedWriter(new OutputStreamWriter(fos));

				if (template != null)
					template.merge(context, writer);

				/*
				 * flush and cleanup
				 */

				try {
					writer.flush();

					writer.close();
					fos.close();
				} catch (IOException e) {
					TratadaorDeExcecao.vaiParaExcecao(
							"Erro escrevendo arquivo HTML. Detalhe: "
									+ e.getMessage(), e);
				}
				context.remove(fs);
				context.remove(componentes);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void generateFromXml(String xmlFile, String destino) {
		/*
		 * setup
		 */

		ServletContext sercontext = FileController.getInstance()
				.getServerContext();

		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH,
				sercontext.getRealPath("/"));
		ve.init();

		// Velocity.init();

		// Velocity.init(sercontext.getRealPath("/")+"configs/velocity.properties");

		/*
		 * Make a context object and populate with the data. This is where the
		 * Velocity engine gets the data to resolve the references (ex. $list)
		 * in the template
		 */

		VelocityContext context = new VelocityContext();
		/*
		 * get the Template object. This is the parsed version of your template
		 * input file. Note that getTemplate() can throw
		 * ResourceNotFoundException : if it doesn't find the template
		 * ParseErrorException : if there is something wrong with the VTL
		 * Exception : if something else goes wrong (this is generally
		 * indicative of as serious problem...)
		 */

		Template template = null;
		template = ve.getTemplate("templates/htmlFromXml.vm");
		try {

			try {
				File file = new File(xmlFile);
				builder = new SAXBuilder();
				root = builder.build(file);
			} catch (Exception e) {
				TratadaorDeExcecao.vaiParaExcecao(
						"Erro construindo o documento XML para processamento Velocity. Detalhe: "
								+ e.getMessage(), e);
			}

			/*
			 * now, make a Context object and populate it.
			 */

		} catch (Exception e) {
			TratadaorDeExcecao.vaiParaExcecao(
					"Erro geral ao gerar arquivo a partir de XML. Detalhe: "
							+ e.getMessage(), e);
		}

		/*
		 * Now have the template engine process your template using the data
		 * placed into the context. Think of it as a 'merge' of the template and
		 * the data to produce the output stream.
		 */

		@SuppressWarnings("unchecked")
		List<Element> forms = root.getRootElement().getChildren();
		for (int i = 0; i < forms.size(); i++) {
			Element n = (Element) forms.get(i);
			context.put("form", n);
			Element componentes = n.getChild("componentes");
			context.put("componentes", componentes);
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(new File(destino));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			writer = new BufferedWriter(new OutputStreamWriter(fos));

			if (template != null)
				template.merge(context, writer);

			/*
			 * flush and cleanup
			 */

			try {
				writer.flush();

				writer.close();
				fos.close();
			} catch (IOException e) {
				TratadaorDeExcecao.vaiParaExcecao(
						"Erro escrevendo arquivo HTML. Detalhe: "
								+ e.getMessage(), e);
			}
			context.remove(n);
			context.remove(componentes);
		}

	}

}

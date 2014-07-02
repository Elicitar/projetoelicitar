package requirementExtract;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.Tbrequisito;

import org.json.simple.JSONObject;
import org.w3c.dom.Element;

import patterns.EntidadeAtiva;
import patterns.EstruturaDeDado;
import patterns.TipoDeDado;
import requirements.RequisitoBase;
import xmlStruct.XmlControlComponent;
import xmlStruct.XmlControlRequirement;
import components.AbstractComponent;
import db.ReqDB;
import exceptions.TipoDeReqisitoInvalidoException;
import exceptions.TratadaorDeExcecao;

public class TratadorArquivo {

	ArrayList<String> linhas = new ArrayList<String>();
	ArrayList<RequisitoBase> requisitos = new ArrayList<RequisitoBase>();
	PatternExtract pattern = new PatternExtract();
	XmlControlRequirement xmlRequisito;

	public TratadorArquivo() {
		RequisitoBase r;
		Tbrequisito tbReq;
		List<Tbrequisito> reqs = ReqDB.getInstance().getAllReqs();
		for (int i = 0; i < reqs.size(); i++) {
			r = new RequisitoBase();
			tbReq = reqs.get(i);
			r.setNome(tbReq.getNome());
			r.addDecricao(tbReq.getDescricao());
			r.setObjetivo(tbReq.getObjetivo());
			r.setTipoRequisitoStr(tbReq.getTipo());
			requisitos.add(r);
		}

	}

	public ArrayList<RequisitoBase> getRequisitos() {
		return requisitos;
	}

	private OrganizadorDeRequisitos organizador;


		public BaseForm getFormByName(String nome) {
		BaseForm baseform = null;
		for (int i = 0; i < organizador.getFormularios().size(); i++) {
			baseform = organizador.getFormularios().get(i);
			if (baseform.getTitle().equals(nome))
				return baseform;
		}
		return baseform;
	}

	public RequisitoBase getRequisitoByName(String nome) {
		RequisitoBase reqbase = null;
		for (int i = 0; i < requisitos.size(); i++) {
			reqbase = requisitos.get(i);
			if (reqbase.getNome().equals(nome))
				return reqbase;
			reqbase = null;
		}
		return reqbase;
	}

	public void RequisitosToComponents(String fileName, BaseForm formToGen) {
		XmlControlComponent xmlComponente;
		Element raiz;
		Element componentes, nodeComponente;
		AbstractComponent comp;
		BaseForm form;
		ArrayList<BaseForm> forms;
		if (formToGen == null) {
			forms = organizador.getFormularios();
		} else {
			forms = new ArrayList<BaseForm>();
			forms.add(formToGen);
		}
		xmlComponente = new XmlControlComponent();
		Element el;
		String compType;
		raiz = xmlComponente.createNewDocumentFrom("Formularios");
		for (int i = 0; i < forms.size(); i++) {
			form = forms.get(i);
			el = xmlComponente.addNewSection(raiz, "formulario");
			xmlComponente.addSingleNodeText(el, "Titulo", form.getTitle());
			componentes = xmlComponente.addNewSection(el, "componentes");
			for (int j = 0; j < form.getComponents().size(); j++) {
				comp = form.getComponents().get(j);
				compType = comp.getTypeStr();
				nodeComponente = xmlComponente.addNewSection(componentes,
						"componente");
				xmlComponente.addSingleNodeText(nodeComponente, "tipo",
						compType);
				xmlComponente.addSingleNodeText(nodeComponente, "nome",
						comp.getNome());
				xmlComponente.addSingleNodeText(nodeComponente, "descricao",
						comp.getDescricao());
				if (comp.isObrigatorio()) {
					xmlComponente.addSingleNodeText(nodeComponente,
							"obrigatorio", "sim");
				} else {
					xmlComponente.addSingleNodeText(nodeComponente,
							"obrigatorio", "nao");
				}
			}
		}
		try {
			xmlComponente.geraXML(fileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked" })
	public void requisitosToJSon(String fileName, BaseForm formToGen) {
		AbstractComponent comp;
		BaseForm form;
		ArrayList<BaseForm> forms;
		if (formToGen == null) {
			forms = organizador.getFormularios();
		} else {
			forms = new ArrayList<BaseForm>();
			forms.add(formToGen);
		}
		String compType;

		JSONObject formJson = new JSONObject();
		JSONObject caracteristicas = new JSONObject();
		ArrayList<JSONObject> AllFormsJson = new ArrayList<JSONObject>();
		JSONObject componentesJson = new JSONObject();
		JSONObject compJSon = new JSONObject();
		JSONObject docJSon = new JSONObject();
		for (int i = 0; i < forms.size(); i++) {
			form = forms.get(i);
			formJson = new JSONObject();
			caracteristicas = new JSONObject();
			componentesJson = new JSONObject();
			caracteristicas.put("Titulo", form.getTitle());
			for (int j = 0; j < form.getComponents().size(); j++) {
				compJSon = new JSONObject();
				comp = form.getComponents().get(j);
				compType = comp.getTypeStr();
				compJSon.put("tipo", compType);
				compJSon.put("nome", comp.getNome());
				compJSon.put("descricao", comp.getDescricao());
				compJSon.put("obrigatorio", comp.isObrigatorio());
				componentesJson.put("componente" + j, compJSon);
			}

			caracteristicas.put("componentes", componentesJson);
			formJson.put("formulario" + i, caracteristicas);
			AllFormsJson.add(formJson);
		}
		docJSon.put("formularios", AllFormsJson);

		File file = new File(fileName);
		String content = docJSon.toJSONString();

		FileOutputStream fop;
		try {
			fop = new FileOutputStream(file);

			// if file doesn't exists, then create it
			if (!file.exists()) {

				file.createNewFile();

			}

			// get the content in bytes
			byte[] contentInBytes = content.getBytes();

			fop.write(contentInBytes);
			fop.flush();
			fop.close();
		} catch (IOException e) {
			TratadaorDeExcecao.vaiParaExcecao(
					"Erro ao gerar definição de arquivo JSON. Detalhe: "
							+ e.getMessage(), e);
		}

		System.out.println("Done");
	
	}

	public void RequisitosToXMl(String xmlName) throws Exception {
		xmlRequisito = new XmlControlRequirement();
		Element raiz = xmlRequisito.createNewDocumentFrom();
		Element requisito;
		Element descricoes;
		RequisitoBase edReq = null;

		Map<String, EstruturaDeDado> estruturas = organizador
				.getEstruturasDeDado();
		Map<String, TipoDeDado> tipos = organizador.getTiposDeDados();
		Map<String, EntidadeAtiva> entidades = organizador.getEntidadesAtivas();
	
		Set<String> estruturasNomes = estruturas.keySet();
		Set<String> tiposNomes = tipos.keySet();
		Set<String> entidadesNomes = entidades.keySet();
	
		EstruturaDeDado ed;
		TipoDeDado td;
		EntidadeAtiva ea;
	
		// grava as estruturas de dados
		Element el = xmlRequisito.addNewSection(raiz, "EstruturasDeDados");
		Element elTiposDeDados;
		for (Iterator<String> iteratorED = estruturasNomes.iterator(); iteratorED
				.hasNext();) {
			String chaveED = iteratorED.next();
			if (chaveED != null) {
				ed = estruturas.get(chaveED);
				edReq = ed.getRequisito();
				requisito = xmlRequisito.CreateNewRequirement(edReq.getNome(),
						el);
				xmlRequisito.addType(requisito, edReq.getTipoRequisitoStr());
				descricoes = xmlRequisito.CreateDescriptionList(requisito);
				for (int j = 0; j < edReq.getDescriptionSize(); j++) {
					xmlRequisito.addDescription(descricoes,
							edReq.getDecricaoByIndex(j));
				}
				elTiposDeDados = xmlRequisito.addNewSection(requisito,
						"TiposDeDadosReferenciados");
				ArrayList<TipoDeDado> tiposdedadoslist = ed.getTiposDeDados();

				for (int i = 0; i < tiposdedadoslist.size(); i++) {
					td = tiposdedadoslist.get(i);
					xmlRequisito.addSingleNodeText(elTiposDeDados,
							"tipodedado", td.getRequisito().getNome());
				}
			}
		}
		// grava os tipos de dados
		el = xmlRequisito.addNewSection(raiz, "TiposDeDados");

		for (Iterator<String> iteratorTipos = tiposNomes.iterator(); iteratorTipos
				.hasNext();) {
			String chaveTipos = iteratorTipos.next();
			if (chaveTipos != null) {
				td = tipos.get(chaveTipos);
				edReq = td.getRequisito();
				requisito = xmlRequisito.CreateNewRequirement(edReq.getNome(),
						el);
				xmlRequisito.addType(requisito, edReq.getTipoRequisitoStr());
				descricoes = xmlRequisito.CreateDescriptionList(requisito);
				for (int j = 0; j < edReq.getDescriptionSize(); j++) {
					xmlRequisito.addDescription(descricoes,
							edReq.getDecricaoByIndex(j));
				}

			}
		}
		// grava os requisitos de entidade ativa
		el = xmlRequisito.addNewSection(raiz, "EntidadeAtivas");

		for (Iterator<String> iteratorEntidades = entidadesNomes.iterator(); iteratorEntidades
				.hasNext();) {
			String chaveTipos = iteratorEntidades.next();
			if (chaveTipos != null) {
				ea = entidades.get(chaveTipos);
				edReq = ea.getRequisito();
				requisito = xmlRequisito.CreateNewRequirement(edReq.getNome(),
						el);
				xmlRequisito.addType(requisito, edReq.getTipoRequisitoStr());
				descricoes = xmlRequisito.CreateDescriptionList(requisito);
				for (int j = 0; j < edReq.getDescriptionSize(); j++) {
					xmlRequisito.addDescription(descricoes,
							edReq.getDecricaoByIndex(j));
				}

				elTiposDeDados = xmlRequisito.addNewSection(requisito,
						"TiposDeDadosReferenciados");
				ArrayList<TipoDeDado> tiposdedadoslist = ea.getTiposDeDados();

				for (int i = 0; i < tiposdedadoslist.size(); i++) {
					td = tiposdedadoslist.get(i);
					xmlRequisito.addSingleNodeText(elTiposDeDados,
							"tipodedado", td.getRequisito().getNome());
				}

				elTiposDeDados = xmlRequisito.addNewSection(requisito,
						"EstruturasDeDadosReferenciadas");
				ArrayList<EstruturaDeDado> estruturalist = ea
						.getEstruturaDeDados();

				for (int i = 0; i < estruturalist.size(); i++) {
					ed = estruturalist.get(i);
					xmlRequisito.addSingleNodeText(elTiposDeDados,
							"estruturadedado", ed.getRequisito().getNome());
				}

			}
		}
			xmlRequisito.geraXML(xmlName + File.separator + "formularios.xml");
	}

	public void toMySqlDb() {
		RequisitoBase req;
		for (int i = 0; i < requisitos.size(); i++) {
			req = requisitos.get(i);
			ReqDB.getInstance().addReqToDB(req);
		}
	}

	public void organizar() {
		try {
			organizador = new OrganizadorDeRequisitos(requisitos);
		} catch (TipoDeReqisitoInvalidoException e) {
			TratadaorDeExcecao.vaiParaExcecao(
					"Erro durante organização de informações. Detalhe: "
							+ e.getMessage(), e);
		}
	}



}

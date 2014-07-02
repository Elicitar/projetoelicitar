package requirementExtract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import patterns.AbstractPattern;
import patterns.EntidadeAtiva;
import patterns.EstruturaDeDado;
import patterns.TipoDeDado;
import requirements.RequisitoBase;
import requirements.TipoDeRequisito;
import components.AbstractComponent;
import exceptions.TipoDeReqisitoInvalidoException;

public class OrganizadorDeRequisitos {
	public ArrayList<BaseForm> getFormularios() {
		return formularios;
	}

	private Map<String, TipoDeDado> tiposDeDados = new HashMap<String, TipoDeDado>();
	private Map<String, EstruturaDeDado> estruturasDeDado = new HashMap<String, EstruturaDeDado>();
	private Map<String, EntidadeAtiva> entidadesAtivas = new HashMap<String, EntidadeAtiva>();
	private ArrayList<BaseForm> formularios = new ArrayList<BaseForm>();
	
	ArrayList<String> gerados = new ArrayList<String>();//Lista para contorno da duplica��o componentes que ja foram adicionados a estrutura da Tela.
	int progressTotal = 0;
	int progressAtual = 0;
	String progressName;

	public Map<String, TipoDeDado> getTiposDeDados() {
		return tiposDeDados;
	}

	public void setTiposDeDados(HashMap<String, TipoDeDado> tiposDeDados) {
		this.tiposDeDados = tiposDeDados;
	}

	public Map<String, EstruturaDeDado> getEstruturasDeDado() {
		return estruturasDeDado;
	}

	public void setEstruturasDeDado(
			HashMap<String, EstruturaDeDado> estruturasDeDado) {
		this.estruturasDeDado = estruturasDeDado;
	}

	public Map<String, EntidadeAtiva> getEntidadesAtivas() {
		return entidadesAtivas;
	}

	public void setEntidadesAtivas(
			HashMap<String, EntidadeAtiva> entidadesAtivas) {
		this.entidadesAtivas = entidadesAtivas;
	}

	public OrganizadorDeRequisitos(ArrayList<RequisitoBase> requisitos)
			throws TipoDeReqisitoInvalidoException {
		progressName = "Organizando requisitos";
		RequisitoBase r;
		for (int i = 0; i < requisitos.size(); i++) {
			r = requisitos.get(i);
			if (TipoDeRequisito.getRequirementType(r.getTipoRequisitoStr()) == TipoDeRequisito.tdrTipoDeDado) {
				TipoDeDado td = new TipoDeDado(r);
				tiposDeDados.put(r.getNome(), td);
			} else if (TipoDeRequisito.getRequirementType(r
					.getTipoRequisitoStr()) == TipoDeRequisito.tdrEstruturaDeDado) {
				EstruturaDeDado ed = new EstruturaDeDado(r);
				estruturasDeDado.put(r.getNome(), ed);
			} else if (TipoDeRequisito.getRequirementType(r
					.getTipoRequisitoStr()) == TipoDeRequisito.tdrEntidadeAtiva) {
				EntidadeAtiva ea = new EntidadeAtiva(r);
				entidadesAtivas.put(r.getNome(), ea);
			}
		}
		organizarEstruturaDeDado();
		organizarEntidadesAtivas();
		generateComponents();
	}

	private void organizarEntidadesAtivas() {
		progressName = "Organizando Entidades Ativas";
		TipoDeDado td;
		EstruturaDeDado ed;
		EntidadeAtiva ea;
		Set<String> entidadesNomes = entidadesAtivas.keySet();
		Set<String> tiposDeDadosNomes = tiposDeDados.keySet();
		Set<String> estruturasNomes = estruturasDeDado.keySet();

		// ajusta para que os tipos de dados usados pelas entidades ativas
		// apare�am referenciadas dentro das estruturas

		for (Iterator<String> iterator = tiposDeDadosNomes.iterator(); iterator
				.hasNext();) {
			String chave = iterator.next();
			if (chave != null) {
				td = (TipoDeDado) tiposDeDados.get(chave);
				for (Iterator<String> iteratorEA = entidadesNomes.iterator(); iteratorEA
						.hasNext();) {
					String chaveEA = iteratorEA.next();
					if (chaveEA != null) {
						ea = entidadesAtivas.get(chaveEA);
						if (ea.usaTipoDeDado(td)) {
							ea.addTipoDeDado(td);
						}
					}
				}
			}
		}

		for (Iterator<String> iterator = estruturasNomes.iterator(); iterator
				.hasNext();) {
			String chave = iterator.next();
			if (chave != null) {
				ed = estruturasDeDado.get(chave);
				for (Iterator<String> iteratorEA = entidadesNomes.iterator(); iteratorEA
						.hasNext();) {
					String chaveEA = iteratorEA.next();
					if (chaveEA != null) {
						ea = entidadesAtivas.get(chaveEA);
						if (ea.usaEstruturaDeDado(ed)) {
							ea.addEstruturaDeDado(ed);
						}
					}
				}
			}
		}

	}

	private void organizarEstruturaDeDado() {
		progressName = "Organizando Estrutura de Dados";
		TipoDeDado td;
		EstruturaDeDado ed;
		Set<String> tiposDeDadosNomes = tiposDeDados.keySet();
		Set<String> estruturasNomes = estruturasDeDado.keySet();

		for (Iterator<String> iterator = tiposDeDadosNomes.iterator(); iterator
				.hasNext();) {
			String chave = iterator.next();
			if (chave != null) {
				td = (TipoDeDado) tiposDeDados.get(chave);
				for (Iterator<String> iteratorED = estruturasNomes.iterator(); iteratorED
						.hasNext();) {
					String chaveED = iteratorED.next();
					if (chaveED != null) {
						ed = ((EstruturaDeDado) estruturasDeDado.get(chaveED));
						if (ed.usaTipoDeDado(td)) {
							ed.addTipoDeDado(td);
						}
					}
				}
			}
		}
	}

	private void addStructInForm(AbstractPattern ap, BaseForm form) {
		EstruturaDeDado ed;
		TipoDeDado td;
		AbstractComponent ab;
		if (ap instanceof EstruturaDeDado) {
			ed = (EstruturaDeDado) ap;
			for (int i = 0; i < ed.getTiposDeDados().size(); i++) {
				td = ed.getTiposDeDados().get(i);
				ab = DefineComponent.getinstance().getComponentFromPattern(td);
				String nome = ab.getNome();
				if (gerados.indexOf(nome) < 0) {
					form.addComponent(ab);
					gerados.add(ab.getNome());
				}
			}
		}

	}
	
	
	private void generateComponents() throws TipoDeReqisitoInvalidoException {
		progressName = "Gerando componentes";
		EntidadeAtiva ea;
		Set<String> entidadesNomes = entidadesAtivas.keySet();
		FormEntidadeAtiva fea;
		Set<Integer> sequence;
		AbstractPattern ap;
		AbstractComponent ac;
		DefineComponent dc = DefineComponent.getinstance();

		for (Iterator<String> iteratorEA = entidadesNomes.iterator(); iteratorEA
				.hasNext();) {
			String chave = (String) iteratorEA.next();
			if (chave != null) {
				gerados.clear();// a cada entidade ativa ele usa uma lista limpa
								// de componentes que ja foram gerados.
				ea = entidadesAtivas.get(chave);
				fea = new FormEntidadeAtiva(ea.getRequisito().getNome());
				sequence = ea.getSequence().keySet();
				for (Iterator<Integer> iteratorSeq = sequence.iterator(); iteratorSeq
						.hasNext();) {
					Integer integer = (Integer) iteratorSeq.next();
					ap = ea.getSequence().get(integer);
					// ********************** REMOVER O IF AP�S AJUSTAR
					// ***********************
					if (ap.getTipoRequisito() == TipoDeRequisito.tdrTipoDeDado) {
						ac = dc.getComponentFromPattern(ap);
						String nome = ac.getNome();
						if (gerados.indexOf(nome) < 0) {
							fea.addComponent(ac);
							gerados.add(ac.getNome());
						}
					} else if (ap.getTipoRequisito() == TipoDeRequisito.tdrEstruturaDeDado) {
						addStructInForm(ap, fea);
					}
				}
				formularios.add(fea);
			}
		}

	}

	
}

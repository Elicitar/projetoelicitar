package patterns;

import requirementExtract.PatternExtract;
import requirements.RequisitoBase;
import requirements.TipoDeRequisito;
import exceptions.TipoDeReqisitoInvalidoException;

public class AbstractPattern implements IPattern {

	private RequisitoBase requisito;
	private ExtractMorfologicalInfo morf = ExtractMorfologicalInfo
			.getInstance();
	String morfName = null;
	boolean multilinhas;
	boolean texto;
	boolean obrigatorio;
	boolean logico;

	public AbstractPattern(RequisitoBase req) {
		requisito = req;
		PatternExtract p = new PatternExtract();
		String s = requisito.getFullDesc();

		boolean isText = p.isText(s);
		boolean isMultiline = p.isMultilines(s);
		boolean isLogical = p.isLogical(s);
		boolean isRequired = p.isRequired(s);

		setObrigatorio(isRequired);
		setMultilinhas(isMultiline);
		setTexto(isText);
		setLogico(isLogical);
	}

	public RequisitoBase getRequisito() {
		return requisito;
	}

	public void setRequisito(RequisitoBase requisito) {
		this.requisito = requisito;
	}

	public boolean isMultilinhas() {
		return multilinhas;
	}

	public void setMultilinhas(boolean multilinhas) {
		this.multilinhas = multilinhas;
	}

	public boolean isTexto() {
		return texto;
	}

	public void setTexto(boolean texto) {
		this.texto = texto;
	}

	public boolean isObrigatorio() {
		return obrigatorio;
	}

	public void setObrigatorio(boolean obrigatorio) {
		this.obrigatorio = obrigatorio;
	}

	public boolean isLogico() {
		return logico;
	}

	public void setLogico(boolean logico) {
		this.logico = logico;
	}

	@Override
	public TipoDeRequisito getTipoRequisito()
			throws TipoDeReqisitoInvalidoException {
		return TipoDeRequisito.getRequirementType(requisito
				.getTipoRequisitoStr());
	}

	public String getMorfologicalSubj() {
		if (morfName == null) {
			String nome = getRequisito().getNome();

			nome = nome.substring(0, 1).toUpperCase().concat(nome.substring(1));
			morfName = morf.getMorfologicalSubjFromText("O " + nome + " é um "
					+ requisito.getTipoRequisitoStr());
		}
		return morfName;
	}

	public String getMorfologicalFullDescriptions() {
		String desc = getRequisito().getFullDesc();
		String morfdesc;
		morfdesc = morf.getMorfologicalText(desc);
		return morfdesc;
	}

}

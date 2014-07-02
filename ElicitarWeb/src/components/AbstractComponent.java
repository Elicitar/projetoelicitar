package components;

import requirementExtract.TipoDeComponente;

public abstract class AbstractComponent implements IComponents{

	public boolean isObrigatorio() {
		return obrigatorio;
	}

	public void setObrigatorio(boolean obrigatorio) {
		this.obrigatorio = obrigatorio;
	}

	boolean obrigatorio;
	boolean texto;
	boolean multilinhas;
	boolean logico;
	boolean multiselecao;	
	String nome;
	String descricao;
	
	
	@Override
	public TipoDeComponente getType() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getTypeStr() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}

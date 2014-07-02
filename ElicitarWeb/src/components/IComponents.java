package components;

import requirementExtract.TipoDeComponente;

public interface IComponents {
	
	TipoDeComponente getType();
	String getTypeStr();
	String getNome();
	String getDescricao();
}

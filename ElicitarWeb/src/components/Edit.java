package components;

import requirementExtract.TipoDeComponente;

public class Edit extends AbstractComponent implements IComponents{
	
	
	@Override
	public TipoDeComponente getType() {
		return TipoDeComponente.tcEdit;
	}

	@Override
	public String getTypeStr() {
		return TipoDeComponente.getStringName(TipoDeComponente.tcEdit);
	}


}

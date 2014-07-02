package components;

import requirementExtract.TipoDeComponente;

public class Checkbox extends AbstractComponent implements IComponents{

	@Override
	public TipoDeComponente getType() {
		return TipoDeComponente.tcCheckbox;
	}

	@Override
	public String getTypeStr() {
		return TipoDeComponente.getStringName(TipoDeComponente.tcCheckbox);
	}



}

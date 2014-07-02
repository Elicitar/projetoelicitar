package components;

import requirementExtract.TipoDeComponente;

public class Memo extends AbstractComponent implements IComponents {

	@Override
	public TipoDeComponente getType() {
		return TipoDeComponente.tcMemo;
	}

	@Override
	public String getTypeStr() {
		return TipoDeComponente.getStringName(TipoDeComponente.tcMemo);
	}

	

}

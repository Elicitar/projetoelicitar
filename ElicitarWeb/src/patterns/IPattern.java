package patterns;

import exceptions.TipoDeReqisitoInvalidoException;
import requirements.RequisitoBase;
import requirements.TipoDeRequisito;

public interface IPattern {
	RequisitoBase getRequisito();
	boolean isObrigatorio();
	boolean isTexto();
	boolean isMultilinhas();
	boolean isLogico();
	TipoDeRequisito getTipoRequisito() throws TipoDeReqisitoInvalidoException;
}

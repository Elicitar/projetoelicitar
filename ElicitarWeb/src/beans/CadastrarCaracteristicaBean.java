package beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

import model.Tbcaracteristica;
import patterns.ExtractMorfologicalInfo;
import requirementExtract.CaracteristicaDeRequisito;
import db.CaracteristicasDB;

@ManagedBean
public class CadastrarCaracteristicaBean implements Serializable,
ValueChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7268055592199330966L;

	private String tipocaracteristica;

	private String caracteristica;
	Tbcaracteristica tb = new Tbcaracteristica();

	public String getTipocaracteristica() {
		return tipocaracteristica;
	}

	public void setTipocaracteristica(String tipocaracteristica) {
		this.tipocaracteristica = tipocaracteristica;
	}

	public String getCaracteristica() {
		return caracteristica;
	}

	public void setCaracteristica(String caracteristica) {
		this.caracteristica = caracteristica;
		tb.setCaracteristica(caracteristica);
		tb.setCaracteristicamorf(ExtractMorfologicalInfo.getInstance().getMorfCaracteristicasFromText(caracteristica));
	}
	
	public String[] getTodasCaracteristicas(){
		return CaracteristicaDeRequisito.getNames();
	}

	@Override
	public void processValueChange(ValueChangeEvent e)
			throws AbortProcessingException {
		String newVal = e.getNewValue().toString();
		tipocaracteristica = newVal;
		tb.setTipocaracteristica(tipocaracteristica);		
	}
	
	public String salvar(){
		CaracteristicasDB.getInstance().addCaracteristicaToDB(tb.getCaracteristica(), tb.getTipocaracteristica(), tb.getCaracteristicamorf());
		return "index.jsf";
	}

}

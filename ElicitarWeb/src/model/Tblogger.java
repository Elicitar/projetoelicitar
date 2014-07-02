package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tblogger database table.
 * 
 */
@Entity
@NamedQuery(name="Tblogger.findAll", query="SELECT t FROM Tblogger t")
public class Tblogger implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String identificador;

	@Column
	@Lob
	private String informations;

	@Column
	@Lob
	private String stacktrace;

	public Tblogger() {
	}

	public String getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getInformations() {
		return this.informations;
	}

	public void setInformations(String informations) {
		this.informations = informations;
	}

	public String getStacktrace() {
		return this.stacktrace;
	}

	public void setStacktrace(String stacktrace) {
		this.stacktrace = stacktrace;
	}

}
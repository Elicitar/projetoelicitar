package model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the tbcaracteristicas database table.
 * 
 */
@Entity
@Table(name="tbcaracteristicas")
@NamedQuery(name="Tbcaracteristica.findAll", query="SELECT t FROM Tbcaracteristica t")
public class Tbcaracteristica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idtbcaracteristica;

	@Column
	@Lob
	private String caracteristica;
	
	@Column
	@Lob
	private String caracteristicamorf;

	@Column
	private String tipocaracteristica;

	public Tbcaracteristica() {
	}

	public int getIdtbcaracteristica() {
		return this.idtbcaracteristica;
	}

	public void setIdtbcaracteristica(int idtbcaracteristica) {
		this.idtbcaracteristica = idtbcaracteristica;
	}

	public String getCaracteristica() {
		return this.caracteristica;
	}

	public void setCaracteristica(String caracteristica) {
		this.caracteristica = caracteristica;
	}

	public String getCaracteristicamorf() {
		return this.caracteristicamorf;
	}

	public void setCaracteristicamorf(String caracteristicamorf) {
		this.caracteristicamorf = caracteristicamorf;
	}

	public String getTipocaracteristica() {
		return this.tipocaracteristica;
	}

	public void setTipocaracteristica(String tipocaracteristica) {
		this.tipocaracteristica = tipocaracteristica;
	}

}
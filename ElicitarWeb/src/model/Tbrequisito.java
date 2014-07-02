package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tbrequisitos database table.
 * 
 */
@Entity
@Table(name="tbrequisitos")
@NamedQuery(name="Tbrequisito.findAll", query="SELECT t FROM Tbrequisito t")
public class Tbrequisito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idtbRequisitos;

	@Column
	@Lob
	private String descricao;

	@Column
	@Lob
	private String morfdesc;
	
	@Column
	private String morfname;
	
	@Column
	private String nome;
	
	@Column
	@Lob
	private String objetivo;
	
	@Column
	private String tipo;

	public Tbrequisito() {
	}

	public int getIdtbRequisitos() {
		return this.idtbRequisitos;
	}

	public void setIdtbRequisitos(int idtbRequisitos) {
		this.idtbRequisitos = idtbRequisitos;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getMorfdesc() {
		return this.morfdesc;
	}

	public void setMorfdesc(String morfdesc) {
		this.morfdesc = morfdesc;
	}

	public String getMorfname() {
		return this.morfname;
	}

	public void setMorfname(String morfname) {
		this.morfname = morfname;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getObjetivo() {
		return this.objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
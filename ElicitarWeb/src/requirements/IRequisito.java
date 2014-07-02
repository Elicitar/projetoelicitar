package requirements;

public interface IRequisito {
	public String getTipoRequisitoStr();

	public void setTipoRequisitoStr(String texto);

	public String getNome();

	public void setNome(String nome);

	public String toString();
	
	public String getFullDesc();

	public void addDecricao(String texto);

	public String getDecricaoByIndex(int index);
	
	public int getDescriptionSize();
}

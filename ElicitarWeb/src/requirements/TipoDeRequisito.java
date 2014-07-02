package requirements;

import exceptions.TipoDeReqisitoInvalidoException;

public enum TipoDeRequisito {
	tdrTipoDeDado("Tipo de Dado"), tdrEstruturaDeDado("Estrutura de dado"), tdrInformacao("Informa��o"), tdrConsulta("Consulta"), tdrIdentificacao("Identifica��o"), tdrTransacao("Transa��o"), tdrConfiguracao("Configura��o"), tdrEntidadeAtiva("Entidade Ativa");
	
	public static final int ordTipoDedado = 0;
	public static final int ordEstruturaDedao = 1;
	public static final int ordInformacao = 2;
	public static final int ordConsulta = 3;
	public static final int ordIdentificacao = 4;
	public static final int ordTransacao = 5;
	public static final int ordConfiguracao = 6;
	public static final int ordEntidadeAtiva = 7;
	
	private final String name;       

    private TipoDeRequisito(String s) {
        name = s.trim().toLowerCase();
    }
    
    public static String[]  getNames(){
    	//String [] str = new String[]{"Tipo de Dado","Estrutura de dado","Informa��o","Consulta","Identifica��o","Transa��o","Configura��o","Entidade Ativa"};
    	/**acima s�o todos os tipos e abaixo apenas os suportados. Estou retornando apenas os suportados */
    	String [] str = new String[]{"Tipo de Dado","Estrutura de dado","Entidade Ativa"};
    	return str;
    } 

    public boolean equalsName(String otherName){
        return (otherName == null)? false:name.equals(otherName);
    }

    public String toString(){
       return name;
    }
    
    public static int getOrdinal(TipoDeRequisito tp){
    	return tp.ordinal();
    }
    
    public static TipoDeRequisito getRequirementType(String text)
			throws TipoDeReqisitoInvalidoException {
		if (text.trim().toLowerCase().equals("tipo de dado"))
			return TipoDeRequisito.tdrTipoDeDado;
		if (text.trim().toLowerCase().equals("estrutura de dado"))
			return TipoDeRequisito.tdrEstruturaDeDado;
		if (text.trim().toLowerCase().equals("entidade ativa"))
			return TipoDeRequisito.tdrEntidadeAtiva;
		if (text.trim().toLowerCase().equals("identifica��o"))
			return TipoDeRequisito.tdrIdentificacao;
		if (text.trim().toLowerCase().equals("configura��o"))
			return TipoDeRequisito.tdrConfiguracao;
		if (text.trim().toLowerCase().equals("transa��o"))
			return TipoDeRequisito.tdrTransacao;
		if (text.trim().toLowerCase().equals("consulta"))
			return TipoDeRequisito.tdrConsulta;
		if (text.trim().toLowerCase().equals("informa��o"))
			return TipoDeRequisito.tdrInformacao;


		throw new TipoDeReqisitoInvalidoException(
						" N�o foi reconhecido como um tipo v�lido para padr�o de requisito"+text);
	}

	public static boolean isRequirementType(String text)
			throws TipoDeReqisitoInvalidoException {
		if (text.trim().toLowerCase().equals("tipo de dado"))
			return true;
		if (text.trim().toLowerCase().equals("estrutura de dado"))
			return true;
		if (text.trim().toLowerCase().equals("entidade ativa"))
			return true;
		if (text.trim().toLowerCase().equals("identifica��o"))
			return true;
		if (text.trim().toLowerCase().equals("configura��o"))
			return true;
		if (text.trim().toLowerCase().equals("transa��o"))
			return true;
		if (text.trim().toLowerCase().equals("consulta"))
			return true;
		return false;

	}
}

package requirements;

import exceptions.TipoDeReqisitoInvalidoException;

public enum TipoDeRequisito {
	tdrTipoDeDado("Tipo de Dado"), tdrEstruturaDeDado("Estrutura de dado"), tdrInformacao("Informação"), tdrConsulta("Consulta"), tdrIdentificacao("Identificação"), tdrTransacao("Transação"), tdrConfiguracao("Configuração"), tdrEntidadeAtiva("Entidade Ativa");
	
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
    	//String [] str = new String[]{"Tipo de Dado","Estrutura de dado","Informação","Consulta","Identificação","Transação","Configuração","Entidade Ativa"};
    	/**acima são todos os tipos e abaixo apenas os suportados. Estou retornando apenas os suportados */
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
		if (text.trim().toLowerCase().equals("identificação"))
			return TipoDeRequisito.tdrIdentificacao;
		if (text.trim().toLowerCase().equals("configuração"))
			return TipoDeRequisito.tdrConfiguracao;
		if (text.trim().toLowerCase().equals("transação"))
			return TipoDeRequisito.tdrTransacao;
		if (text.trim().toLowerCase().equals("consulta"))
			return TipoDeRequisito.tdrConsulta;
		if (text.trim().toLowerCase().equals("informação"))
			return TipoDeRequisito.tdrInformacao;


		throw new TipoDeReqisitoInvalidoException(
						" Não foi reconhecido como um tipo válido para padrão de requisito"+text);
	}

	public static boolean isRequirementType(String text)
			throws TipoDeReqisitoInvalidoException {
		if (text.trim().toLowerCase().equals("tipo de dado"))
			return true;
		if (text.trim().toLowerCase().equals("estrutura de dado"))
			return true;
		if (text.trim().toLowerCase().equals("entidade ativa"))
			return true;
		if (text.trim().toLowerCase().equals("identificação"))
			return true;
		if (text.trim().toLowerCase().equals("configuração"))
			return true;
		if (text.trim().toLowerCase().equals("transação"))
			return true;
		if (text.trim().toLowerCase().equals("consulta"))
			return true;
		return false;

	}
}

package requirements;

public class ConstsRequisitos {
	private final String cNome = "#NOME#";

	private final static String templateTipo = "<<Nome do tipo de dado>>, que s�o usados para"
			+ " <<Finalidade do Tipo de Dado>>, deve ser do"
			+ " formul�rio <<Formul�rio Tipo de Dado>>.\n"
			+ " (<<Declara��o (descri��o) do formato da"
			+ " exibi��o>>). \n(<<Restri��es das declara��es>>.)"
			+ " \n(<<Manipula��o especial da declara��o>>.)\n"
			+ "EXEMPLO:\n"
			+ "O sistema permitir� endere�os de e-mail de at� 60 caracteres.";
	private final static String templateEstrutura = "<<Descri��o do tipo de dado>> dever� ter os seguintes"
			+ " itens de informa��o:\n"
			+ " � <<descri��o do dado do item 1>>\n"
			+ " � ...\n"
			+ "EXEMPLO:\n"
			+ "Os detalhes do nome pessoal dever�o mostrar os seguintes itens de informa��o:\n"
			+ "� Nome dado\n"
			+ "� Segundo nome\n"
			+ "� Sobrenome\n"
			+ "� Iniciais\n"
			+ "� T�tulo\n";
	private final static String templateEntidade = "O sistema deve armazenar as seguintes informa��es"
			+ " sobre o <<Nome da Entidade>>:\n"
			+ " � \"Dados item 1 descri��o\".\n"
			+ " ��\n"
			+ " Uma \"Entidade nome\" � \"Entidade explica��o\". \n Cada"
			+ " \"Entidade nome\" � exclusivamente identificada por"
			+ " \"identificador Entidade (s)\". \n(<<Detalhes das entidades geradas>>.)\n"
			+ "EXEMPLO:\n"
			+ "O sistema deve armazenar as seguintes informa��es sobre o cliente:\n"
			+ "� ID do cliente (como definido no requisito XR99.1).\n"
			+ "� Senha.\n"
			+ "� Informa��es de contatos pessoais  (como definido no requisito XR99.2).\n"
			+ "� Informa��es de cart�o de cr�dito (tal como definido no requisito XR99.3).\n"
			+ "� Data de nascimento.\n"
			+ "�Data de inscri��o.\n"
			+ "� Status (ativo, bloqueado ou encerrado). Isso nunca � mostrado ao cliente.\n"
			+ "Cada cliente � identificado unicamente pelo seu ID de cliente.\n";

	public java.lang.String getcNome() {
		return cNome;
	}

	public static String getTemplate(TipoDeRequisito tipo) {
		String result = "A descri��o pode ser preenchida conforme o modelo abaixo:\n";
		switch (tipo) {
		case tdrTipoDeDado: {
			result += templateTipo;
			break;
		}
		case tdrEstruturaDeDado: {
			result += templateEstrutura;
			break;
		}
		case tdrEntidadeAtiva: {
			result += templateEntidade;
			break;
		}
		default: {
			result = "Tipo n�o tratado";
		}
		}
		return result;
	}

}

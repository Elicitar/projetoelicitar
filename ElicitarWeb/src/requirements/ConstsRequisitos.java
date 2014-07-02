package requirements;

public class ConstsRequisitos {
	private final String cNome = "#NOME#";

	private final static String templateTipo = "<<Nome do tipo de dado>>, que são usados para"
			+ " <<Finalidade do Tipo de Dado>>, deve ser do"
			+ " formulário <<Formulário Tipo de Dado>>.\n"
			+ " (<<Declaração (descrição) do formato da"
			+ " exibição>>). \n(<<Restrições das declarações>>.)"
			+ " \n(<<Manipulação especial da declaração>>.)\n"
			+ "EXEMPLO:\n"
			+ "O sistema permitirá endereços de e-mail de até 60 caracteres.";
	private final static String templateEstrutura = "<<Descrição do tipo de dado>> deverá ter os seguintes"
			+ " itens de informação:\n"
			+ " • <<descrição do dado do item 1>>\n"
			+ " • ...\n"
			+ "EXEMPLO:\n"
			+ "Os detalhes do nome pessoal deverão mostrar os seguintes itens de informação:\n"
			+ "• Nome dado\n"
			+ "• Segundo nome\n"
			+ "• Sobrenome\n"
			+ "• Iniciais\n"
			+ "• Título\n";
	private final static String templateEntidade = "O sistema deve armazenar as seguintes informações"
			+ " sobre o <<Nome da Entidade>>:\n"
			+ " • \"Dados item 1 descrição\".\n"
			+ " •…\n"
			+ " Uma \"Entidade nome\" é \"Entidade explicação\". \n Cada"
			+ " \"Entidade nome\" é exclusivamente identificada por"
			+ " \"identificador Entidade (s)\". \n(<<Detalhes das entidades geradas>>.)\n"
			+ "EXEMPLO:\n"
			+ "O sistema deve armazenar as seguintes informações sobre o cliente:\n"
			+ "• ID do cliente (como definido no requisito XR99.1).\n"
			+ "• Senha.\n"
			+ "• Informações de contatos pessoais  (como definido no requisito XR99.2).\n"
			+ "• Informações de cartão de crédito (tal como definido no requisito XR99.3).\n"
			+ "• Data de nascimento.\n"
			+ "•Data de inscrição.\n"
			+ "• Status (ativo, bloqueado ou encerrado). Isso nunca é mostrado ao cliente.\n"
			+ "Cada cliente é identificado unicamente pelo seu ID de cliente.\n";

	public java.lang.String getcNome() {
		return cNome;
	}

	public static String getTemplate(TipoDeRequisito tipo) {
		String result = "A descrição pode ser preenchida conforme o modelo abaixo:\n";
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
			result = "Tipo não tratado";
		}
		}
		return result;
	}

}

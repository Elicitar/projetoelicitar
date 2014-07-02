package requirementExtract;

public enum CaracteristicaDeRequisito {
	cdrObrigatorio, cdropcional, cdrMultilinha, cdrTexto, cdrLogico;

	public static String[] getNames() {
		String[] str = new String[] { "obrigatorio", "opcional", "multilinha", "texto",
				"logico" };
		return str;
	}
}

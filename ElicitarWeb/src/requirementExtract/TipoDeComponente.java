package requirementExtract;

public enum TipoDeComponente {
	tcEdit, tcMemo, tcCheckbox, tcButton, tcCombobox, tcStatusbar;

	private final static String tcCheckboxtr = "tcCheckbox";
	private final static String tcMemoStr = "tcMemo";
	private final static String tcEditStr = "tcEdit";
	private final static String tcButtonStr = "tcButton";
	private final static String tcComboboxStr = "tcCombobox";
	private final static String tcStatusbarStr = "tcStatusbar";

	public static String getStringName(TipoDeComponente tp) {
		switch (tp) {
		case tcCheckbox:
			return tcCheckboxtr;
		case tcCombobox:
			return tcComboboxStr;
		case tcMemo:
			return tcMemoStr;
		case tcStatusbar:
			return tcStatusbarStr;
		case tcButton:
			return tcButtonStr;
		default:
			return tcEditStr;
		}
	}

	public static TipoDeComponente getComponentFromTypeStr(String tp) {
		if (tp.toLowerCase().equals(tcEditStr)) {
			return tcEdit;
		} else if (tp.toLowerCase().equals(tcCheckboxtr)) {
			return tcCheckbox;
		} else if (tp.toLowerCase().equals(tcComboboxStr)) {
			return tcCombobox;
		} else if (tp.toLowerCase().equals(tcMemoStr)) {
			return tcMemo;
		} else if (tp.toLowerCase().equals(tcStatusbarStr)) {
			return tcStatusbar;
		} else if (tp.toLowerCase().equals(tcButtonStr)) {
			return tcButton;
		} else return null; 
	}
}

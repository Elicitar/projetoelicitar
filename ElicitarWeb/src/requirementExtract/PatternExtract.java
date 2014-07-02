package requirementExtract;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mail.SendMails;
import model.Tbcaracteristica;
import db.CaracteristicasDB;

public class PatternExtract {

	private final static String constante = "NAOVALENADAESTETEXTOEï¿½APENASPARAGARANTIRQUENAOVAMISTIRARCOMCARCTERISTICA";

	private String internalGetCaracteristica(String tipo) {
		List<Tbcaracteristica> tb = CaracteristicasDB.getInstance()
				.getCaraceteristicaByTipo(tipo);
		String ret = constante;
		if (tb == null) {
			SendMails.enviarEmail("projetoelicitar@gmail.com", "projetoelicitar@gmail.com", "Erro na busca de características", tipo+" não é reconhecido como característica válida");
			ret = constante;
		} else {
			for (int i = 0; i < tb.size(); i++) {
				Tbcaracteristica tbcaracteristica = (Tbcaracteristica) tb
						.get(i);
				ret += "|" + tbcaracteristica.getCaracteristicamorf();
			}
		}
		if (ret == null)
			ret = constante;
		
		return ret;

	}

	private String getCaracetristicaObrigatorio() {
		String car = CaracteristicaDeRequisito.getNames()[CaracteristicaDeRequisito.cdrObrigatorio
				.ordinal()];
		return internalGetCaracteristica(car);
	}

	private String getCaracetristicaOpcional() {
		String car = CaracteristicaDeRequisito.getNames()[CaracteristicaDeRequisito.cdropcional
				.ordinal()];
		return internalGetCaracteristica(car);
	}

	private String getCaracetristicaTexto() {
		String car = CaracteristicaDeRequisito.getNames()[CaracteristicaDeRequisito.cdrTexto
				.ordinal()];
		return internalGetCaracteristica(car);
	}

	private String getCaracetristicaMultilinha() {
		String car = CaracteristicaDeRequisito.getNames()[CaracteristicaDeRequisito.cdrMultilinha
				.ordinal()];
		return internalGetCaracteristica(car);
	}

	private String getCaracetristicaLogico() {
		String car = CaracteristicaDeRequisito.getNames()[CaracteristicaDeRequisito.cdrLogico
				.ordinal()];
		return internalGetCaracteristica(car);
	}

	public boolean isRequired(String requirementDesc) {

		String obrigatorio = getCaracetristicaObrigatorio();
		String opcional = getCaracetristicaOpcional();

		Pattern padrao = Pattern.compile(obrigatorio, Pattern.CASE_INSENSITIVE);
		Matcher pesquisa = padrao.matcher(requirementDesc);
		if (pesquisa.find())
			return true;
		padrao = Pattern.compile(opcional, Pattern.CASE_INSENSITIVE);
		pesquisa = padrao.matcher(requirementDesc);
		if (pesquisa.find())
			return false;
		return false;
	}

	public boolean isText(String requirementDesc) {
		String texto = getCaracetristicaTexto();
		Pattern padrao = Pattern.compile(texto, Pattern.CASE_INSENSITIVE);
		Matcher pesquisa = padrao.matcher(requirementDesc);
		return pesquisa.find();
	}

	public boolean isMultilines(String requirementDesc) {
		String texto = getCaracetristicaMultilinha();
		Pattern padrao = Pattern.compile(texto, Pattern.CASE_INSENSITIVE);
		Matcher pesquisa = padrao.matcher(requirementDesc);
		return pesquisa.find();
	}

	public boolean isMultiselect(String requirementDesc) {
		Pattern padrao = Pattern.compile("escolher entre|selecionar entre",
				Pattern.CASE_INSENSITIVE);
		Matcher pesquisa = padrao.matcher(requirementDesc);
		return pesquisa.find();
	}

	public boolean isLogical(String requirementDesc) {
		String texto = getCaracetristicaLogico();
		Pattern padrao = Pattern.compile(texto, Pattern.CASE_INSENSITIVE);
		Matcher pesquisa = padrao.matcher(requirementDesc);
		return pesquisa.find();
	}

}

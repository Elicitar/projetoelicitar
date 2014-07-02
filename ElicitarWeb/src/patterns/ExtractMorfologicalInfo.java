package patterns;

import java.util.Locale;

import org.cogroo.analyzer.Analyzer;
import org.cogroo.analyzer.ComponentFactory;
import org.cogroo.text.Document;
import org.cogroo.text.Sentence;
import org.cogroo.text.SyntacticChunk;
import org.cogroo.text.Token;
import org.cogroo.text.impl.DocumentImpl;

public class ExtractMorfologicalInfo {
	private Analyzer cogroo;
	// Create a document and set the text.
	Document document = new DocumentImpl();
	static ExtractMorfologicalInfo instance;

	public static ExtractMorfologicalInfo getInstance() {
		if (instance == null) {
			instance = new ExtractMorfologicalInfo();
		}
		return instance;
	}

	public ExtractMorfologicalInfo() {
		/*
		 * The following command creates a component factory given a locale. The
		 * locale will be resolved as a configuration file in the classpath with
		 * the following pattern: /models_lang_COUNTRY. Another option is to use
		 * the method ComponentFactory.create(InputStream) directly.
		 */
		ComponentFactory factory = ComponentFactory.create(new Locale("pt",
				"BR"));

		/*
		 * Create the default pipe, which is complete, including from sentence
		 * detection to featurization.
		 */
		cogroo = factory.createPipe();
	}

	private void analyzeText(String documentText) {

		document.setText(documentText);

		// lets measure the time...
		// long start = System.nanoTime();

		// analyze it
		cogroo.analyze(document);

		// System.out.println("Document processed in "
		// + ((System.nanoTime() - start) / 1000000) + "ms");

	}

	public String getMorfCaracteristicasFromText(String text) {
		text = text.toLowerCase();
		analyzeText(text);
		StringBuilder fulltext = new StringBuilder();

		for (Sentence sentence : document.getSentences()) {
			for (Token token : sentence.getTokens()) {
				String lexeme = token.getLexeme();
				String pos = token.getPOSTag().toLowerCase();
				if (pos.equals("adj") || pos.equals("n")) {
					fulltext.append(lexeme+" ");
				}
			}
		}
		return fulltext.toString();
	}

	public String getMorfologicalText(String text) {
		analyzeText(text);
		String fulltext = "";
		StringBuffer sb = new StringBuffer();

		for (Sentence sentence : document.getSentences()) {
			for (Token token : sentence.getTokens()) {
				String lexeme = token.getLexeme();
				String pos = token.getPOSTag().toLowerCase();
				if (pos.equals("adj") || pos.equals("n")) {
					fulltext += lexeme + " ";
				}
			}
		}
		return fulltext;
	}

	public String getMorfologicalSubjFromText(String text) {
		analyzeText(text);
		String subject;
		for (Sentence sentence : document.getSentences()) {
			for (SyntacticChunk structure : sentence.getSyntacticChunks()) {
				subject = "";
				if (structure.getTag().toUpperCase().equals("SUBJ")) {
					for (Token innerToken : structure.getTokens()) {
						subject += innerToken.getLexeme() + " ";
					}
					subject = subject.substring(2, subject.length() - 1).trim();
					return subject;
				}
			}
		}
		return null;
	}

}

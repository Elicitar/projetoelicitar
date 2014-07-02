package velocityhtml;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.servlet.ServletContext;

import beans.FileController;
import exceptions.TratadaorDeExcecao;

public class PropertiesRead {

	Properties prop = new Properties();
	OutputStream output = null;
	InputStream input = null;

	public void write(Properties aProp) {
		try {

			output = new FileOutputStream("configs/templates.properties");

			// set the properties value
			prop.setProperty("html", "template/html.vm");
			
			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			TratadaorDeExcecao.vaiParaExcecao("Erro escrevendo properties. Detalhe: "+io.getMessage(), io);
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					TratadaorDeExcecao.vaiParaExcecao("Não foi possível escrever as porpriedades do templaete Velocity. Detalhe: "+e.getMessage(), e);
				}
			}

		}
	}

	public Properties read() {
		try {
			ServletContext context = FileController.getInstance().getServerContext();
			
			input = context.getResourceAsStream("configs/templates.properties");

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			System.out.println(prop.getProperty("html"));

		} catch (IOException ex) {
			TratadaorDeExcecao.vaiParaExcecao("Erro lendo properties. Detalhe: "+ex.getMessage(), ex);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					TratadaorDeExcecao.vaiParaExcecao("Não foi possível ler as porpriedades do componente Velocity. Detalhe: "+e.getMessage(), e);
				}
			}
		}
		return prop;
	}

}

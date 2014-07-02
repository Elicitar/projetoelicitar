package beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import velocityhtml.HtmlGenerator;

@ManagedBean
@SessionScoped
public class GenHtmlBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2057569575936440947L;
	private UploadedFile file;
	private StreamedContent downfile;
	File filelocal;

	public void upload() throws IOException {

	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public StreamedContent getGerarHtml() throws IOException {

		String prefix = FilenameUtils.getBaseName(file.getFileName());
		String filename = "temps/" + prefix + ".html";
		ServletContext context = FileController.getInstance()
				.getServerContext();

		HtmlGenerator h = new HtmlGenerator();
		/****/
		if (FilenameUtils.getExtension(file.getFileName()).toLowerCase() == "xml") {
			h.generateFromXml(filelocal.getAbsolutePath(),
					context.getRealPath("/") + filename);
		} else {
			h.generateFromJSOn(filelocal.getAbsolutePath(),
					context.getRealPath("/") + filename);
		}
		InputStream stream = context.getResourceAsStream(filename);
		downfile = new DefaultStreamedContent(stream, "text/html", "form.html");

		return downfile;

	}

	public void handleFileUpload(FileUploadEvent event) {
		file = event.getFile();
		if (file != null) {

			String prefix = FilenameUtils.getBaseName(file.getFileName());
			String suffix = FilenameUtils.getExtension(file.getFileName());
			try {
				filelocal = File.createTempFile(prefix + "-", "." + suffix);

				FacesMessage msg = new FacesMessage("Sucesso",
						file.getFileName() + " foi carregado.");
				FacesContext.getCurrentInstance().addMessage("teste", msg);

				InputStream input = file.getInputstream();
				OutputStream output = new FileOutputStream(filelocal);

				try {
					IOUtils.copy(input, output);
				} finally {
					IOUtils.closeQuietly(output);
					IOUtils.closeQuietly(input);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}

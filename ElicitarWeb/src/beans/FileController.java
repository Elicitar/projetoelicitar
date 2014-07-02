package beans;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;


public class FileController {
	
	private static FileController vInstance;

	public static FileController getInstance(){
		if(vInstance == null){
			vInstance = new FileController();
		}
		return vInstance;
	}
	
	public ServletContext getServerContext() {
		FacesContext aFacesContext = FacesContext.getCurrentInstance();
		ServletContext context = (ServletContext) aFacesContext
				.getExternalContext().getContext();

		return context;
	}
	
	public String getContextLoc(){
		return getServerContext().getRealPath("/");
	}

}

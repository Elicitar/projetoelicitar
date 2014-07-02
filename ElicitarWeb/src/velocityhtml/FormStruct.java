package velocityhtml;

import java.util.ArrayList;

public class FormStruct {
	String titulo;
	ArrayList<ComponentStruct> components = new ArrayList<ComponentStruct>();

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public ArrayList<ComponentStruct> getComponents() {
		return components;
	}

	public void addComp(ComponentStruct c) {
		components.add(c);
	}

}

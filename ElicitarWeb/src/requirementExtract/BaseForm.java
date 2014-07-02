package requirementExtract;

import java.util.ArrayList;

import components.AbstractComponent;

public class BaseForm {
	String title;
	ArrayList<AbstractComponent> components = new ArrayList<AbstractComponent>();

	public String getTitle() {
		return title;
	}

	public ArrayList<AbstractComponent> getComponents() {
		return components;
	}

	public void addComponent(AbstractComponent component){
		components.add(component);
	}
	
	public BaseForm(String title) {
		this.title = title;
	}
}

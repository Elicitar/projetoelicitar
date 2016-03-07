package requirementExtract;

import components.AbstractComponent;
import components.Checkbox;
import components.Edit;
import components.Memo;
import patterns.AbstractPattern;

public class DefineComponent {
	static DefineComponent define;

	public static DefineComponent getinstance() {
		if (define == null) {
			define = new DefineComponent();
		}
		return define;
	}

	public AbstractComponent getComponentFromPattern(AbstractPattern pattern) {
		
		AbstractComponent comp = null;
		String descricao = pattern.getRequisito().getNome();
		String nome =  descricao.replaceAll("\\s","");  		
		if (pattern.isTexto()) {
			if (pattern.isMultilinhas()) {
				comp = new Memo();
				comp.setNome("mm"+nome);				
			}  else {
				comp = new Edit();
				comp.setNome("ed"+nome);
			}
		}else if (pattern.isLogico()) {
			comp = new Checkbox();
			comp.setNome("cb"+nome);
		}else{
			comp = new Edit();
			comp.setNome("ed"+nome);
		}
		comp.setDescricao(descricao);
		comp.setObrigatorio(pattern.isObrigatorio());
		
		return comp;
	}

}

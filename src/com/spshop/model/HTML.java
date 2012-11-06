package com.spshop.model;

public class HTML extends Component{

	/**
	 *
	 */
	private static final long serialVersionUID = -5374332799910505972L;
	
	private String content;
	
	public HTML() {
	}
	
	public HTML(HTML html) {
		super(html);
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public HTML clone() {
		HTML obj = null;
		obj = new HTML(this);
		if (this.content != null) {
			obj.content = this.content;
		}
		return obj;
	}

}

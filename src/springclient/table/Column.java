package springclient.table;

public class Column {

	private String caption;
	private Integer width;
	private Class< ? > type;
	
	public String getCaption() {
	
		return caption;
	}
	
	public void setCaption( String caption ) {
	
		this.caption = caption;
	}
	
	public Integer getWidth() {
	
		return width;
	}
	
	public void setWidth( Integer width ) {
	
		this.width = width;
	}
	
	public Class< ? > getType() {
	
		return type;
	}
	
	public void setType( Class< ? > type ) {
	
		this.type = type;
	}
}

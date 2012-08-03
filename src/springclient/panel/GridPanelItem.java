package springclient.panel;

import org.springframework.beans.factory.annotation.Required;

import springclient.SwingComponent;

public class GridPanelItem {
	
	private Integer row;
	private Integer column;
	private Integer align = GridPanel.LEFT;
	private Boolean actualSize = false;
	private SwingComponent< ?, ? > component;
	
	public Integer getRow() {
	
		return row;
	}
	
	@Required
	public void setRow( Integer row ) {
	
		this.row = row;
	}
	
	public Integer getColumn() {
		
		return column;
	}
	
	public void setColumn( Integer column ) {
	
		this.column = column;
	}
	
	public Integer getAlign() {
	
		return align;
	}
	
	public void setAlign( Integer align ) {
	
		this.align = align;
	}
	
	public Boolean getActualSize() {
		
		return actualSize;
	}
	
	public void setActualSize( Boolean actualSize ) {
		
		this.actualSize = actualSize;
	}
	
	public SwingComponent< ?, ? > getComponent() {
	
		return component;
	}
	
	@Required
	public void setComponent( SwingComponent< ?, ? > component ) {
	
		this.component = component;
	}
}

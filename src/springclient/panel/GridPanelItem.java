package springclient.panel;

import springclient.SwingComponent;

public class GridPanelItem {
	
	private Integer row;
	private Integer column;
	private Integer align = GridPanel.LEFT;
	private SwingComponent< ?, ? > component;
	
	public Integer getRow() {
	
		return row;
	}
	
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
	
	public SwingComponent< ?, ? > getComponent() {
	
		return component;
	}
	
	public void setComponent( SwingComponent< ?, ? > component ) {
	
		this.component = component;
	}
}

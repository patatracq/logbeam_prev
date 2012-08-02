package springclient;

import java.awt.Dimension;


public class GoldenRatioDimension extends Dimension {

	public static final double GOLDEN_RATIO = 1.61803399;
	
	private static final long serialVersionUID = -9100282760298895296L;

	public void setWidth( double width ) {
		
		double height = width / GOLDEN_RATIO; 
		
		super.setSize( width, Math.round( height ) );
	}
	
	public void setHeight( double height ) {
		
		double width = height / GOLDEN_RATIO; 
		
		super.setSize( Math.round( width ), height );
	}
}

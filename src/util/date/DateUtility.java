package util.date;


import java.util.Calendar;

public abstract class DateUtility {

	public static Calendar rollforwardYear( int value, Calendar date ) {
		
		date.set( Calendar.YEAR, date.get( Calendar.YEAR ) + value );
		
		return date;
	}
	
	public static Calendar rollforwardMonth( int value, Calendar date ) {
		
		if ( date.get( Calendar.MONTH ) + value > 12 ) {
			date.set( Calendar.MONTH, date.get( Calendar.MONTH ) + value - 12 );
			date = rollforwardYear( 1, date );
		} else {
			date.set( Calendar.MONTH, date.get( Calendar.MONTH ) + value );
		}
		
		return date;
	}
	
	public static Calendar rollforwardDay( int value, Calendar date ) {
	
		if ( ( date.get( Calendar.DAY_OF_MONTH ) + value ) > date.getActualMaximum( Calendar.DAY_OF_MONTH ) ) {
			date.set( Calendar.DAY_OF_MONTH, date.get( Calendar.DAY_OF_MONTH ) + value - date.getActualMaximum( Calendar.DAY_OF_MONTH ) );
			date = rollforwardMonth( 1, date );
		} else {
			date.set( Calendar.DAY_OF_MONTH, date.get( Calendar.DAY_OF_MONTH ) + value );
		}
		
		return date;
	}
	
	public static Calendar rollforwardHour( int value, Calendar date ) {
	
		if ( ( date.get( Calendar.HOUR_OF_DAY ) + value ) >= 24 ) {
			date.set( Calendar.HOUR_OF_DAY, date.get( Calendar.HOUR_OF_DAY ) + value - 24 );
			date = rollforwardDay( 1, date );
		} else {
			date.set( Calendar.HOUR_OF_DAY, date.get( Calendar.HOUR_OF_DAY ) + value );
		}
		
		return date;
	}

	public static Calendar rollbackYear( int value, Calendar date ) {
		
		date.set( Calendar.YEAR, date.get( Calendar.YEAR ) - value );
		
		return date;
	}
	
	public static Calendar rollbackMonth( int value, Calendar date ) {
		
		if ( date.get( Calendar.MONTH ) <= value ) {
			date = rollbackYear( 1, date );
			date.set( Calendar.MONTH, 12 + ( date.get( Calendar.MONTH ) - value ) );
		} else {
			date.set( Calendar.MONTH, date.get( Calendar.MONTH ) - value );
		}
		
		return date;
	}
	
	public static Calendar rollbackDay( int value, Calendar date ) {
	
		if ( date.get( Calendar.DAY_OF_MONTH ) <= value ) {
			date = rollbackMonth( 1, date );
			date.set( Calendar.DAY_OF_MONTH, date.getActualMaximum( Calendar.DAY_OF_MONTH ) + ( date.get( Calendar.DAY_OF_MONTH ) - value ) );
		} else {
			date.set( Calendar.DAY_OF_MONTH, date.get( Calendar.DAY_OF_MONTH ) - value );
		}
		
		return date;
	}
	
	public static Calendar rollbackHour( int value, Calendar date ) {
	
		if ( date.get( Calendar.HOUR_OF_DAY ) < value ) {
			date.set(  Calendar.HOUR_OF_DAY, 24 + ( date.get( Calendar.HOUR_OF_DAY ) - value ) );
			date = rollbackDay( 1, date );
		} else {
			date.set( Calendar.HOUR_OF_DAY, date.get( Calendar.HOUR_OF_DAY ) - value );
		}
		
		return date;
	}
}

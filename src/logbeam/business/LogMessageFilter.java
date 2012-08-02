package logbeam.business;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;

import jonas.annotations.JonasElement;
import crudbeam.business.BusinessPojo;

@Entity
@Cacheable
public class LogMessageFilter extends BusinessPojo {

	private String regex;
	private String originalMessage;
	private String testResult;
	private Pattern compiledRegex = null;

	@Column( unique = true, nullable = false )
	@JonasElement
	public String getRegex() {
		
		return regex;
	}

	public void setRegex( String regularExpression ) {
		
		this.regex = regularExpression;
		
		if ( compiledRegex == null ) {
			compiledRegex = Pattern.compile( regularExpression );
		}
	}

	@Column( unique = true, nullable = false )
	@JonasElement
	public String getOriginalMessage() {
		
		return originalMessage;
	}
	
	public void setOriginalMessage( String originalMessage ) {
		
		this.originalMessage = originalMessage;
	}
	
	@JonasElement
	public String getTestResult() {
		
		return testResult;
	}
	
	public void setTestResult( String testResult ) {
		
		this.testResult = testResult;
	}
	
	public boolean matches( String s ) {
		
		if ( compiledRegex != null ) {
			Matcher matcher = compiledRegex.matcher( s );
			return matcher.matches();
		} else {
			return true;
		}
	}
}

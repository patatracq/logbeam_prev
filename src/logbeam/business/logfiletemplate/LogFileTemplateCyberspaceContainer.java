package logbeam.business.logfiletemplate;

import logbeam.business.LogFileTemplate;
import crudbeam.cyberspace.CyberspaceContainer;


public class LogFileTemplateCyberspaceContainer extends CyberspaceContainer< LogFileTemplate > implements
		LogFileTemplateContainer {

	@Override
	protected LogFileTemplate newObject() {

		return new LogFileTemplate();
	}
}

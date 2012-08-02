package logbeam.business.logfile;

import logbeam.business.LogFile;
import crudbeam.cyberspace.CyberspaceContainer;


public class LogFileCyberspaceContainer extends CyberspaceContainer< LogFile > implements
		LogFileContainer {

	@Override
	protected LogFile newObject() {

		return new LogFile();
	}
}

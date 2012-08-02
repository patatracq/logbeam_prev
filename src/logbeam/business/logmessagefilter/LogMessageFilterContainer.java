package logbeam.business.logmessagefilter;

import java.util.Collection;

import crudbeam.business.ContainerInterface;

import logbeam.business.LogMessageFilter;



public interface LogMessageFilterContainer extends ContainerInterface {

	public Collection< LogMessageFilter > getAll();
}

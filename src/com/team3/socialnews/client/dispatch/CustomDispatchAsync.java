package com.team3.socialnews.client.dispatch;

import net.customware.gwt.dispatch.client.standard.*;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.Result;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * DispatchAsync implementation that tries to
 * a request multiple times if failures occur.
 */
public class CustomDispatchAsync implements MonitoredDispatchAsync {

	private static final StandardDispatchServiceAsync realService = GWT.create( StandardDispatchService.class );
	public static final int DEFAULT_MAX_RETRY = 3;
	
	/* (non-Javadoc)
	 * @see com.team3.socialnews.client.dispatch.MonitoredDispatchAsync#execute(A, com.google.gwt.user.client.rpc.AsyncCallback)
	 */
	@Override
	/**
	 * Executes an action on the server. Will retry a certain
	 * number of times if an unexpected failure occurs.
	 */
	public <A extends Action<R>, R extends Result> void execute(
			final A action, final AsyncCallback<R> callback) {
		execute(action, DEFAULT_MAX_RETRY, callback, null);
	}
	

	@Override
	public <A extends Action<R>, R extends Result> void execute(
			final A action, int retryCount, final AsyncCallback<R> callback) {
		execute(action, retryCount, callback, null);
	}
	
	@Override
	public <A extends Action<R>, R extends Result> void execute(
			final A action, final AsyncCallback<R> callback, final AsyncProgressMonitor progressMonitor) {
		execute(action, DEFAULT_MAX_RETRY, callback, progressMonitor);
	}
	
	@Override
	public <A extends Action<R>, R extends Result> void execute(
			final A action,
			final int retryCount,
			final AsyncCallback<R> callback, 
			final AsyncProgressMonitor progressMonitor) {
		realService.execute( action, (AsyncCallback<Result>) new AsyncCallback<R>() {
            public void onFailure( Throwable caught ) {
            	if (!(caught instanceof ActionException) && retryCount > 0) {
            		// This failure is not normal and
            		// may be due to a bad connection.
            		
            		// If the progressMonitor is null. Try again
            		// If the progressMonitor is not null, ask if
            		// we should continue.
            		if (progressMonitor == null || progressMonitor.onFailure(caught, retryCount - 1)) {
            				execute(action, retryCount - 1, callback, progressMonitor);
            		} else {
            			callback.onFailure(caught);
            		}
            		
            	} else {
            		// We've failed too many times or this is an exception 
            		//thrown by the action.
            		callback.onFailure(caught);
            	} 
            }

            @SuppressWarnings("unchecked")
			public void onSuccess( Result result ) {
                callback.onSuccess( ( R ) result );
            }
        } );
	}
}

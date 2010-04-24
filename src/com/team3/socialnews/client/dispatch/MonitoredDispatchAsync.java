package com.team3.socialnews.client.dispatch;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MonitoredDispatchAsync extends DispatchAsync {

	public abstract <A extends Action<R>, R extends Result> void execute(
			final A action, int retryCount, final AsyncCallback<R> callback);

	/**
	 * Execute a request and allow the request
	 * to be retried on failure.
	 * @param action The action to send.
	 * @param retryCount The number of times the request can be retried.
	 * @param callback The callback to get the result.
	 * @param progressMonitor A progress monitor to monitor failures.
	 */
	public abstract <A extends Action<R>, R extends Result> void execute(
			final A action, final int retryCount,
			final AsyncCallback<R> callback,
			final AsyncProgressMonitor progressMonitor);

	<A extends Action<R>, R extends Result> void execute(A action, AsyncCallback<R> callback,
			AsyncProgressMonitor progressMonitor);

}
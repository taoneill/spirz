package com.team3.socialnews.client.dispatch;

/**
 * Allows clients to monitor the progress of an async request.
 */
public interface AsyncProgressMonitor {
	/**
	 * Called when a failure occurs during an
	 * Async request.
	 * @param caught The exception that was return from the server.
	 * @param remainingAttempts The number of attempts that are remaining.
	 * @return True if another retry should be attempted, false otherwise.
	 */
	boolean onFailure(Throwable caught, int remainingAttempts);
}

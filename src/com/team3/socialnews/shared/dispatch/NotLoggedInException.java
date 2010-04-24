package com.team3.socialnews.shared.dispatch;

import net.customware.gwt.dispatch.shared.ActionException;

/**
 * Thrown when a user that is not logged in
 * attempts to access functionality that requires
 * login credentials.
 */
public class NotLoggedInException extends ActionException {

  public NotLoggedInException() {}
}
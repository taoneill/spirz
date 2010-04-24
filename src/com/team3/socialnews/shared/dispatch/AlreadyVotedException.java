package com.team3.socialnews.shared.dispatch;

import net.customware.gwt.dispatch.shared.ActionException;

public class AlreadyVotedException extends ActionException {

  public AlreadyVotedException() {}

  public AlreadyVotedException(String message) {
    super(message);
  }

}
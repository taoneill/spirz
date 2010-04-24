package com.team3.socialnews.client.view;

import com.google.gwt.user.client.ui.Composite;
import com.team3.socialnews.client.presenter.AbstractPresenter;

public abstract class AbstractView extends Composite implements
		AbstractPresenter.Display {

	protected AbstractView() {
	}

	@Override
	public void startProcessing() {
	}

	@Override
	public void stopProcessing() {
	}
}

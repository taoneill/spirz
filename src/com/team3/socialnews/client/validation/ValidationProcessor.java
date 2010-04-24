package com.team3.socialnews.client.validation;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

public class ValidationProcessor {
	ArrayList<HasValueValidator> validators = new ArrayList<HasValueValidator>();
	ValidationHandler vhandler;
	KeyDownHandler keyDownHandler = new KeyDownHandler() {
		@Override
		public void onKeyDown(KeyDownEvent event) {
			run();
		}
	};
	
	ValueChangeHandler<String> valueChangeHandler = new ValueChangeHandler<String>() {
		@Override
		public void onValueChange(ValueChangeEvent<String> event) {
			run();
		}
	};
	
	
	public void run() {
		if (validates()) {
			vhandler.onPass();
		} else {
			vhandler.onFail();
		}
	}
	
	public ValidationProcessor(ValidationHandler vhandler) {
		this.vhandler = vhandler;
	}
	
	public void add(TextBox tb, StringValidator v) {
		validators.add(new HasValueValidator(tb, v));
		tb.addKeyDownHandler(keyDownHandler);
		tb.addValueChangeHandler(valueChangeHandler);
	}
	
	public void add(TextArea ta, StringValidator v) {
		validators.add(new HasValueValidator(ta, v));
		ta.addKeyDownHandler(keyDownHandler);
		ta.addValueChangeHandler(valueChangeHandler);
	}
	
	public boolean validates() {
		for(HasValueValidator v : validators)
			if (!v.validates())
				return false;
		return true;
	}
}

class HasValueValidator {
	private HasValue<String> hv;
	private StringValidator v;
	public HasValueValidator(HasValue<String> hv, StringValidator v) {
		this.hv = hv;
		this.v = v;
	}
	
	public boolean validates() {
		return v.validates(hv.getValue());
	}
}

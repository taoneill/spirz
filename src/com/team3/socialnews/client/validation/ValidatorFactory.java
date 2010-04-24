package com.team3.socialnews.client.validation;

public class ValidatorFactory {
	public static StringValidator getURLValidator() {
		return new StringValidator() {
			@Override
			public native boolean validates(String url) /*-{
				var pattern = /(http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/;
    			return pattern.test(url);
			}-*/;
		};
	}
	
	public static StringValidator getNonEmptyValidator() {
		return new StringValidator() {
			@Override
			public native boolean validates(String string) /*-{
				var pattern = /.+/;
    			return pattern.test(string);
			}-*/;
		};
	}
	
	public static StringValidator getCharacterLimitValidator(final Integer charLimit) {
		return new StringValidator() {
			@Override
			public boolean validates(String string) {
				return string.length() <= charLimit;
			};
		};
	}
}

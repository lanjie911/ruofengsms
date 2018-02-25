package com.sms.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;


public class ErrorMessageUtil {
	
	public static String getErrorMessage(BindingResult error) {
		String sumError = "";
		for (Object object : error.getAllErrors()) {
		    if(object instanceof FieldError) {
		        FieldError fieldError = (FieldError) object;
		        sumError += fieldError.getDefaultMessage();
		    }
		}
		return sumError;
	}
}

package com.dzcx.utils;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

public class ResourceUtil {

	
	public static String get(String fileName,String key){
		if(StringUtils.isBlank(fileName) || StringUtils.isBlank(key)){
			return null;
		}
		PropertyResourceBundle bundle = (PropertyResourceBundle) ResourceBundle.getBundle(fileName);
		return bundle.getString(key);
	}
}

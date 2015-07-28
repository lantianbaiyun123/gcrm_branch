package com.baidu.gcrm.common;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class DateEditor extends PropertyEditorSupport {

	public static final DateFormat DEFAULT_DATEFORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");
	public static final DateFormat DATEFORMAT = new SimpleDateFormat(
			"dd-MM-yyyy");

	private DateFormat dateFormat;

	public DateEditor() {
	}

	public DateEditor(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if(StringUtils.isBlank(text)){
			return;
		}
		try {
			if (this.dateFormat != null)
				setValue(this.dateFormat.parse(text));
			else {
				setValue(DEFAULT_DATEFORMAT.parse(text));
			}
		} catch (ParseException ex) {
			throw new IllegalArgumentException("Could not parse date: "
					+ ex.getMessage(), ex);
		}
	}

	@Override
	public String getAsText() {
		Date value = (Date) getValue();
		DateFormat dateFormat = this.dateFormat;
		if (dateFormat == null) {
			dateFormat = DEFAULT_DATEFORMAT;
		}
		return (value != null ? dateFormat.format(value) : "");
	}
}

package com.vteam.xml_project.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

public class DateUtil {

	private static final Logger logger = Logger.getLogger(DateUtil.class);

	public static String SERVER_RETURN_FORMATE_STRING = "yyyy-MM-dd HH:mm:ss";

	public static String BACK_END_FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";
	public static String BACK_END_DATE_ONLY_FORMAT_STRING = "yyyy-MM-dd";
	public static String BACK_END_TIME_ONLY_FORMAT_STRING = "HH:MM:SS";

	public static String FRONT_END_FORMAT_STRING = "MMM dd, yyyy hh:mma";
	public static String FRONT_END_DATE_ONLY_FORMAT_STRING = "MMM dd, yyyy";
	public static String FRONT_END_TIME_ONLY_FORMAT_STRING = "HH:mm";

	public static String TEST_DATE_ONLY_FORTMAT_STRING = "dd MMM yyyy";

	DateFormat dateFormat = new SimpleDateFormat(
			BACK_END_DATE_ONLY_FORMAT_STRING);

	public Date parseFromString(String dateString) throws ParseException {
		return dateFormat.parse(dateString);
	}

	public Date parseFromString(String dateString, String formatString)
			throws ParseException {
		return new SimpleDateFormat(formatString).parse(dateString);
	}

	public Date getCurrentDate() {
		java.util.Date date = new java.util.Date();
		String dateStr = dateFormat.format(date);
		try {
			return dateFormat.parse(dateStr);
		} catch (ParseException pe) {
			logger.error(ExceptionUtils.getStackTrace(pe));
			return null;
		}
	}

	public Date getCurrentDate(String timezone) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(timezone));
		return cal.getTime();
	}

	public static String getFormattedDate(Date day, String formatString) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(formatString);
			return format.format(day);
		} catch (Exception e) {
			return null;
		}
	}

	public static String getFormattedDate(String day, String formatString) {
		SimpleDateFormat format = new SimpleDateFormat(formatString);
		DateFormat inputFormat = new SimpleDateFormat(
				DateUtil.SERVER_RETURN_FORMATE_STRING);
		try {
			Date date = inputFormat.parse(day);
			return format.format(date);
		} catch (ParseException ex) {
			logger.error(ExceptionUtils.getStackTrace(ex));
			return "";
		}
	}
}

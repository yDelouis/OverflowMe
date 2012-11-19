package fr.ydelouis.overflowme.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil
{
	public static final long ONE_MINUTE = 60;
	public static final long ONE_HOUR = 60*ONE_MINUTE;
	public static final long ONE_DAY = 24*ONE_HOUR;
	
	public static String toShortString(long soDate) {
		long now = toSoTime(new Date());
		long splitSec = now - soDate;
		if(splitSec < ONE_MINUTE)
			return splitSec+" sec"+(splitSec == 1 ? "" : "s")+" ago";
		if(splitSec < ONE_HOUR)
			return (splitSec/ONE_MINUTE)+" min"+(splitSec/ONE_MINUTE == 1 ? "": "s")+" ago";
		if(splitSec < ONE_DAY)
			return (splitSec/ONE_HOUR)+" hour"+(splitSec/ONE_HOUR == 1 ? "" : "s")+" ago";
		if(splitSec < 2*ONE_DAY)
			return "yesterday";
		return toDateString(soDate);
	}
	
	public static String toDateString(long soDate) {
		Calendar cal = toCal(soDate);
		String monthShort = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US);
		return monthShort+" "+cal.get(Calendar.DAY_OF_MONTH);
	}
	
	public static String toDurationString(long soDate) {
		Calendar now = Calendar.getInstance();
		Calendar date = toCal(soDate);
		int years = now.get(Calendar.YEAR) - date.get(Calendar.YEAR);
		if(now.get(Calendar.DAY_OF_YEAR) < date.get(Calendar.DAY_OF_YEAR))
			years--;
		int months = now.get(Calendar.MONTH) - date.get(Calendar.MONTH);
		if(now.get(Calendar.DAY_OF_MONTH) < date.get(Calendar.DAY_OF_MONTH))
			months--;
		int days = now.get(Calendar.DAY_OF_YEAR) - date.get(Calendar.DAY_OF_YEAR);
		if(days < 0)
			days += 365;
		if(months < 0)
			months += 12;
		String duration = "";
		if(years != 0)
			duration += years+" year"+(years > 1 ? "s ":" ");
		if(months >= 3)
			duration += months+" month"+(months > 1 ? "s ":" ");
		if(years == 0 && months < 3)
			duration += days+" day"+(days > 1 ? "s":"");
		return duration.trim();
	}
	
	public static Date toDate(long soDate) {
		return new Date(soDate*1000);
	}
	
	public static Calendar toCal(long soDate) {
		Calendar date = Calendar.getInstance();
		date.setTime(toDate(soDate));
		return date;
	}
	
	public static long toSoTime(Date date) {
		return date.getTime()/1000;
	}
}

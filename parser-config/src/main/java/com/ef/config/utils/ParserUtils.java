package com.ef.config.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ef.config.enums.DurationEnum;

/**
 * 
 * @comment Generic utils from Parser 
 * @author <a href="mailto:bsllozad@gmail.com">Bernardo Lopez</a>
 * @project parser-config
 * @class ParserUtils
 * @date Jul 7, 2019
 *
 */
public class ParserUtils {
	
	public static final String PARSER_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String PARSER_DATE_FORMAT_2 = "yyyy-MM-dd.HH:mm:ss";
	public static final String PARSER_DATE_FORMAT_3 = "yyyy-MM-ddTHH:mm:ss.SSSZ";
	
	public static Timestamp convertStringToTimestamp(String strDate, int typeFormat) {
		try {
			DateFormat formatter = null;
			
			switch (typeFormat) {
			case 1:
				formatter = new SimpleDateFormat(PARSER_DATE_FORMAT);
				break;
			case 2:
				formatter = new SimpleDateFormat(PARSER_DATE_FORMAT_2);
				break;
			case 3:
				formatter = new SimpleDateFormat(PARSER_DATE_FORMAT_3);
				break;
			default:
				break;
			}
			Date date = formatter.parse(strDate);
			Timestamp timeStampDate = new Timestamp(date.getTime());

			return timeStampDate;
		} catch (ParseException e) {
			System.out.println("Exception :" + e);
			return null;
		}
	}
	
	public static boolean isBetweenDates(Timestamp recordDate, String startDate, String duration ){
		boolean result = false;
		
		Calendar startDateCalendar = stringToCalendar(startDate, 2);
		Calendar endDateCalendar = stringToCalendar(getStringFinishedDate(startDate, duration), 2);
		Calendar recordDateCalendar = Calendar.getInstance();
		recordDateCalendar.setTimeInMillis(recordDate.getTime());
		
		if (recordDateCalendar.getTime().after(startDateCalendar.getTime()) && recordDateCalendar.getTime().before(endDateCalendar.getTime())) {
			result = true;
		}
		
		return result;
	}
	
	public static DurationEnum getDurationType(String duration) {
		DurationEnum returnValue = null;
		if (duration.equals(DurationEnum.DAILY.name().toLowerCase())) {
			returnValue = DurationEnum.DAILY;
		} else if (duration.equals(DurationEnum.HOURLY.name().toLowerCase())) {
			returnValue = DurationEnum.HOURLY;
		}
		
		return returnValue;
	}
	
	public static String getStringFinishedDate(String startDate, String duration) {
		Calendar startDateCalendar = stringToCalendar(startDate, 2);
		Calendar endDateCalendar = startDateCalendar;
		DurationEnum type = getDurationType(duration);
		
		switch (type) {
		case DAILY:
			endDateCalendar.add(Calendar.HOUR_OF_DAY, 24);
			break;

		case HOURLY:
			endDateCalendar.add(Calendar.HOUR_OF_DAY, 1);
			break;
		default:
			break;
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(PARSER_DATE_FORMAT_2);
		
		return dateFormat.format(endDateCalendar.getTime());
	}
	
	public static Calendar stringToCalendar(String stringDate, int typeFormat) {
		Calendar resultDate = Calendar.getInstance();
		SimpleDateFormat dateFormat = null;
		
		try {
			switch (typeFormat) {
			case 1:
				dateFormat = new SimpleDateFormat(PARSER_DATE_FORMAT);
				resultDate.setTime(dateFormat.parse(stringDate));
				break;
			case 2:
				dateFormat = new SimpleDateFormat(PARSER_DATE_FORMAT_2);
				resultDate.setTime(dateFormat.parse(stringDate));
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultDate;
		
	}

}

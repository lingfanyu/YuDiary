package Main;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	private static Calendar calendar = Calendar.getInstance();
	private static SimpleDateFormat shortFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat longFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String getNowDate() {
		return shortFormat.format(new Date());
	}

	public static String getNowDateTime() {
		return longFormat.format(new Date());
	}

	public static int getYear() {
		return calendar.get(Calendar.YEAR);
	}

	public static int getMonth() {
		return calendar.get(Calendar.MONTH);
	}

	public static int getDay() {
		return calendar.get(Calendar.DATE);
	}

	public static int getHour() {
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMinute() {
		return calendar.get(Calendar.MINUTE);
	}

	public static int getSecond() {
		return calendar.get(Calendar.SECOND);
	}

	public static void main(String[] args) {
	}
}

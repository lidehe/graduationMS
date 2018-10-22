package utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class DateTimeUtil {
	public static String getCurrentDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}

	@SuppressWarnings("static-access")
	public static Date getCurrentDate(){
		Date date=new Date();
		date.from(Instant.now());
		return date;
	}
}

package dispatcher.almundo.service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Clase de utilidad para fechas
 * 
 * @author Braian
 *
 */
public class DateUtils {

	
	public static long getDateDiff(Date date, Date date1 ) {
	    long diffInMillies = date1.getTime() - date.getTime();
	    return TimeUnit.SECONDS.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
	
}

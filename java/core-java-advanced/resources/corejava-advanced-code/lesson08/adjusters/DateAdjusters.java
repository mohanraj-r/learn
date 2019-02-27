package adjusters;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class DateAdjusters {
	public static boolean isWeekend(DayOfWeek dow) {
		return dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY;
	}
	
    public static void main(String[] args) {
        LocalDate nextTuesday = LocalDate.now().with(
                TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY));
        System.out.println("nextTuesday: " + nextTuesday);
        LocalDate lastTuesdayOfMonth = LocalDate.now().with(
                TemporalAdjusters.lastInMonth(DayOfWeek.TUESDAY));
        System.out.println("lastTuesdayOfMonth: " + lastTuesdayOfMonth);
        LocalDate lastDayOfMonth = LocalDate.now().with(
                TemporalAdjusters.lastDayOfMonth());
        System.out.println("lastDayOfMonth: " + lastDayOfMonth);
        
        LocalDate today = LocalDate.of(2013, 11, 9); // Saturday

        TemporalAdjuster NEXT_WORKDAY = w -> {
            LocalDate result = (LocalDate) w;
            do {
                result = result.plusDays(1);
            } while (isWeekend(result.getDayOfWeek()));
            return result;
        };

        LocalDate backToWork = today.with(NEXT_WORKDAY);
        System.out.println("backToWork: " + backToWork);
        
        NEXT_WORKDAY = TemporalAdjusters.ofDateAdjuster(w -> {
			LocalDate result = w; // No cast
            	do {
            		result = result.plusDays(1);
            	} while (isWeekend(result.getDayOfWeek()));
            return result;
        });
        
        backToWork = today.with(NEXT_WORKDAY); 
        System.out.println("backToWork: " + backToWork);
    }
}

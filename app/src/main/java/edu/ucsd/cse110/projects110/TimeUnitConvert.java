package edu.ucsd.cse110.projects110;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TimeUnitConvert {
    public static String formatMillisToHM(long millis) {
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60;

        if (hours > 0) {
            return String.format(Locale.getDefault(), "%dh", hours);
        } else {
            return String.format(Locale.getDefault(), "%dm", minutes);
        }
    }
}

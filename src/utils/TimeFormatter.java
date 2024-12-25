package utils;

public class TimeFormatter {
    public static String formattedTimeOf(long timeInMicro){
        long h = timeInMicro/3_600_000_000L;
        long m = (timeInMicro%3_600_000_000L)/60_000_000;
        long s = ((timeInMicro%3_600_000_000L)%60_000_000)/1_000_000;
        String hours = (h < 10)? ("0" + h):("" + h);
        String minutes = (m < 10)? ("0" + m):("" + m);
        String seconds = (s < 10)? ("0" + s):("" + s);
        String formattedForm =  hours + ":" + minutes + ":" + seconds;

        return formattedForm;
    }
}

package com.ziorye.proofread;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;

@Component
public class CustomTimeUtil {
    public static String duration(long seconds) {
        LocalTime timeOfDay = LocalTime.ofSecondOfDay(seconds);
        return timeOfDay.toString();
    }

    public static String duration2(long seconds) {
        Duration duration = Duration.ofSeconds(seconds);
        LocalTime time = LocalTime.MIDNIGHT.plus(duration);
        return time.toString();
    }

    public static void main(String[] args) {
        int seconds = 3661;
        System.out.println(duration(seconds));
        System.out.println(duration2(seconds));
    }
}

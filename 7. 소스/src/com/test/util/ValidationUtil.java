package com.test.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class ValidationUtil {

    // 두 날짜 사이의 일수 계산
    public static int calculateDaysBetween(String startDate, String endDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate start = LocalDate.parse(startDate, formatter);
            LocalDate end = LocalDate.parse(endDate, formatter);

            return (int) ChronoUnit.DAYS.between(start, end); // 두 날짜 사이의 일수 계산
        } catch (DateTimeParseException e) {
            return -1; // 잘못된 형식일 경우 -1 반환
        }
    }

    // 총 금액 계산 (숙박 기간 * 1박 요금)
    public static int calculateTotalPrice(int stayDuration, int pricePerNight) {
        return stayDuration * pricePerNight;
    }

    // 최소 숙박 기간 검증 (1박 이상)
    public static boolean isMinimumStayValid(long stayDuration) {
        return stayDuration >= 1; // 최소 1박 이상
    }
    
    //
}



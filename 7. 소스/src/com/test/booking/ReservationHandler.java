package com.test.booking;

import com.test.util.ValidationUtil;

public class ReservationHandler {
    private static int accommodationId;
    private static String checkInDate;
    private static String checkOutDate;
    private static int numGuests;
    private static int pricePerNight; // 1박 요금
    private static int totalPrice;

    public static void setReservationDetails(int id, String checkIn, String checkOut, int guests, int pricePerNight) {
        accommodationId = id;
        checkInDate = checkIn;
        checkOutDate = checkOut;
        numGuests = guests;
        ReservationHandler.pricePerNight = pricePerNight;

        // 총 금액 계산
        long stayDuration = ValidationUtil.calculateDaysBetween(checkIn, checkOut);
        totalPrice = (int) (stayDuration * pricePerNight);

        // 디버깅 로그
        System.out.println("숙박 기간: " + stayDuration + "박");
        System.out.println("1박 요금: " + pricePerNight + "원");
        System.out.println("총 금액: " + totalPrice + "원");
    }

    public static int getAccommodationId() {
        return accommodationId;
    }

    public static String getCheckInDate() {
        return checkInDate;
    }

    public static String getCheckOutDate() {
        return checkOutDate;
    }

    public static int getNumGuests() {
        return numGuests;
    }

    public static int getTotalPrice() {
        return totalPrice;
    }
}

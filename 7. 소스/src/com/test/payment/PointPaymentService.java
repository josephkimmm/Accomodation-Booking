package com.test.payment;

import java.io.*;
import java.util.*;
import com.test.booking.Booking;
import com.test.user.User;

public class PointPaymentService {

    private static final String MEMBERS_FILE = "./data/members.txt";
    private static final String BOOKING_LIST_FILE = "./data/booking_list.txt";

    public boolean processPointPayment(int userIndex, int totalPrice, Booking booking) throws IOException {
        List<User> users = loadMembers();
        for (User user : users) {
            if (user.getUserIndex() == userIndex) {
                if (user.getUserPoints() >= totalPrice) {
                    user.setUserPoints(user.getUserPoints() - totalPrice);
                    saveMembers(users);
                    addBooking(booking);
                    return true;
                }
            }
        }
        return false;
    }

    // 포인트 충전 메서드
    public void chargeUserPoints(int userIndex, int chargeAmount) throws IOException {
        List<User> users = loadMembers();

        for (User user : users) {
            if (user.getUserIndex() == userIndex) {
                // 포인트 충전
                user.setUserPoints(user.getUserPoints() + chargeAmount);
                saveMembers(users);
                System.out.println("\n포인트가 성공적으로 충전되었습니다. 현재 포인트: " + user.getUserPoints() + "P");
                return;
            }
        }

        System.out.println("사용자를 찾을 수 없습니다.");
    }

    public List<User> loadMembers() throws IOException {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(MEMBERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("■");
                users.add(new User(
                        Integer.parseInt(parts[0]), parts[1], parts[2], parts[3],
                        parts[4], parts[5], Integer.parseInt(parts[6])
                ));
            }
        }
        return users;
    }

    public void saveMembers(List<User> members) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MEMBERS_FILE))) {
            for (User user : members) {
                writer.write(user.toFileFormat());
                writer.newLine();
            }
        }
    }

    public void addBooking(Booking booking) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKING_LIST_FILE, true))) {
            writer.write(booking.toFileFormat());
            writer.newLine();
        }
    }

    public int generateBookingIndex() {
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKING_LIST_FILE))) {
            String line;
            int maxIndex = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("■");
                maxIndex = Math.max(maxIndex, Integer.parseInt(parts[0]));
            }
            return maxIndex + 1;
        } catch (IOException e) {
            return 1;
        }
    }
}

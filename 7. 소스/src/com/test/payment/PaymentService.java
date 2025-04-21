package com.test.payment;


import com.test.booking.Booking;
import com.test.user.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentService {

    private static final String LOGIN_USER_FILE = "./data/loginUser.txt";
    private static final String MEMBERS_FILE = "./data/members.txt";
    private static final String BOOKING_LIST_FILE = "./data/booking_list.txt";

    // 로그인된 사용자 정보 가져오기
    public User getLoggedInUser() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(LOGIN_USER_FILE))) {
            String line = reader.readLine();
            if (line != null) {
                String[] parts = line.split("■");
                return new User(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], parts[4].equals("true"));
            }
        }
        return null;
    }

    // 모든 사용자 정보 가져오기
    public List<User> loadMembers() throws IOException {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(MEMBERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("■");
                users.add(new User(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], parts[4], parts[5], Integer.parseInt(parts[6])));
            }
        }
        return users;
    }

    // 예약 리스트에 추가
    public void addBooking(Booking booking) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKING_LIST_FILE, true))) {
            writer.write(booking.toFileFormat());
            writer.newLine();
        }
    }

    // 포인트 결제 처리
//    public boolean processPointPayment(User user, int totalPrice, Booking booking) throws IOException {
//        List<User> users = loadMembers();
//        for (User user : members) {
//            if (member.getUsername().equals(user.getUserIndex())) {
//                if (member.getBalance() >= totalPrice) {
//                    // 포인트 차감
//                    member.setBalance(member.getBalance() - totalPrice);
//
//                    // 예약 추가
//                    addBooking(booking);
//
//                    // 파일 업데이트
//                    saveMembers(members);
//
//                    System.out.println("포인트 결제가 완료되었습니다.");
//                    System.out.println("남은 포인트: " + member.getBalance() + "P");
//                    return true;
//                } else {
//                    System.out.println("포인트가 부족합니다. 충전 후 다시 시도해주세요.");
//                    return false;
//                }
//            }
//        }
//        return false;
//    }

//    // 사용자 정보 저장
//    private void saveMembers(List<Member> members) throws IOException {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MEMBERS_FILE))) {
//            for (Member member : members) {
//                writer.write(member.toFileFormat());
//                writer.newLine();
//            }
//        }
//    }
//
//    // Booking 생성
//    public static Booking createBooking(int userIndex, int accommodationIndex, String checkInDate, String checkOutDate, int numGuests, int totalPrice) {
//        int bookingIndex = generateBookingIndex(); // 예약 ID 생성
//        return new Booking(bookingIndex, userIndex, accommodationIndex, checkInDate, checkOutDate, numGuests, totalPrice);
//    }

    // 예약 ID 생성
    private static int generateBookingIndex() {
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKING_LIST_FILE))) {
            String line;
            int maxIndex = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("■");
                maxIndex = Math.max(maxIndex, Integer.parseInt(parts[0]));
            }
            return maxIndex + 1;
        } catch (IOException e) {
            return 1; // 파일이 비어있으면 예약 ID를 1로 설정
        }
    }

//
//    public Payment processCardPayment(String cardComp, String cardNumber, String cardExp, String cardPw) throws Exception {
//
//        if (cardNumber.startsWith("1234")) {
//            throw new Exception("카드 결제 실패: 잘못된 카드 정보");
//        }
//
//
//        Payment payment = new Payment("Card", cardComp, 1000); // 임시 결제 금액
//        return payment;
//    }

}

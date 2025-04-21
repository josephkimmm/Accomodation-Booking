package com.test.payment;

import java.io.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

import com.test.booking.Booking;
import com.test.user.User;
import com.test.util.LoginSystem;

public class PaymentProcessor {
    private final DecimalFormat decimalFormat = new DecimalFormat("#,###");
    private final String MEMBERS_FILE_PATH = "./data/members.txt"; // 파일 경로

    public void processPointsPayment(Scanner scan, List<User> users, List<Booking> bookings) throws NumberFormatException, IOException {
        // 로그인한 사용자의 ID 가져오기
        int userId = Integer.parseInt(LoginSystem.getUserIndex());

        // 회원 정보 확인
        User user = users.stream()
                .filter(m -> m.getUserIndex() == userId)
                .findFirst()
                .orElse(null);

        if (user == null) {
            System.out.println("회원 정보를 찾을 수 없습니다.");
            return;
        }

        // 해당 회원의 예약 정보 찾기
        Booking userBooking = bookings.stream()
                .filter(b -> b.getUserId() == userId)
                .findFirst()
                .orElse(null);

        if (userBooking == null) {
            System.out.println("예약 정보를 찾을 수 없습니다.");
            return;
        }

        int amount = userBooking.getTotalPrice();
        System.out.println("결제하실 금액은 " + decimalFormat.format(amount) + "원 입니다.");

        // 잔액 확인 및 충전/취소 옵션 제공
        while (user.getUserPoints()< amount) {
            System.out.println("계좌 잔액이 부족합니다.");
            System.out.println("현재 잔액: " + decimalFormat.format(user.getUserPoints()) + "원");
            System.out.println("필요 금액: " + decimalFormat.format(amount) + "원");
            System.out.println("\n[1] 쌍용머니 충전");
            System.out.println("[2] 결제 취소");
            System.out.print("선택: ");
            String choice = scan.nextLine();

            switch (choice) {
                case "1":
                    chargeAccount(scan, users, user); // members를 전달
                    break;
                case "2":
                    System.out.println("결제를 취소합니다.");
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
            }
        }

        // 결제 진행 확인
        System.out.print("결제를 진행하시겠습니까? (Y/N): ");
        String response = scan.nextLine();

        if (!response.equalsIgnoreCase("Y")) {
            System.out.println("결제가 취소되었습니다.");
            return;
        }

        // 결제 처리
        user.setUserPoints(user.getUserPoints() - amount);
        System.out.println("\n결제가 완료되었습니다!");
        System.out.println("결제 금액: " + decimalFormat.format(amount) + "원");
        System.out.println("남은 잔액: " + decimalFormat.format(user.getUserPoints()) + "원");

        // 회원 정보 파일에 잔액 업데이트
        updateMemberBalanceInFile(users);

        // 프로그램 종료
        System.out.println("\n즐거운 숙박되세요. 감사합니다.");
        System.exit(0); // 프로그램 종료
    }

    private void chargeAccount(Scanner scan, List<User> users, User user) {
        System.out.print("충전하실 금액을 입력하세요: ");
        String input = scan.nextLine();

        int chargeAmount;
        try {
            chargeAmount = Integer.parseInt(input);
            if (chargeAmount <= 0) {
                System.out.println("충전 금액은 0원보다 커야 합니다.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("유효하지 않은 금액입니다. 숫자를 입력해주세요.");
            return;
        }

        // 충전 처리
        user.setUserPoints(user.getUserPoints() + chargeAmount);
        System.out.println("\n충전이 완료되었습니다!");
        System.out.println("충전 금액: " + decimalFormat.format(chargeAmount) + "원");
        System.out.println("현재 잔액: " + decimalFormat.format(user.getUserPoints()) + "원");

        // 충전 후 회원 정보 파일에 잔액 업데이트
        try {
            updateMemberBalanceInFile(users);
        } catch (IOException e) {
            System.out.println("파일 업데이트 중 오류가 발생했습니다.");
        }
    }

    // 파일 내의 회원 잔액을 업데이트하는 메소드
    private void updateMemberBalanceInFile(List<User> users) throws IOException {
        // 파일에 모든 회원 정보를 덮어쓰도록 BufferedWriter 사용
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MEMBERS_FILE_PATH))) {
            for (User user : users) {
                // 파일에 회원 정보를 "ID■username■password■name■email■phoneNumber■balance" 형식으로 기록
                writer.write(user.getUserIndex() + "■" +
                        user.getUserId() + "■" +
                        user.getUserPassword() + "■" +
                        user.getUserName() + "■" +
                        user.getUserEmail() + "■" +
                        user.getUserPhone() + "■" +
                        user.getUserPoints() + "\n");
            }
        }
    }
}

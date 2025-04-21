package com.test.payment;

import com.test.booking.BookingService;
import com.test.booking.ReservationHandler;
import com.test.booking.Booking;
import com.test.user.User;
import com.test.util.LoginSystem;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PaymentView {

    private PointPaymentService pointPaymentService = new PointPaymentService();
    private BookingService bookingService = new BookingService();
    
    public void showPaymentOptions() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("\033[47m\033[30m");
        System.out.println("                     ┏━━━━━━━━━━┓                   ");
        System.out.println("┏━━━━━━━━━━━━━━━━━━━━┃ 결제 옵션┃━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃                    ┗━━━━━━━━━━┛                  ┃");
        System.out.println("┃ ┏━━━━━━━━━━━━━━━━━━━━━━┓┏━━━━━━━━━━━━━━━━━━━━━┓  ┃");
        System.out.println("┃ ┃ [1] 카드결제         ┃┃ [2] 포인트결제      ┃  ┃");
        System.out.println("┃ ┗━━━━━━━━━━━━━━━━━━━━━━┛┗━━━━━━━━━━━━━━━━━━━━━┛  ┃");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
        System.out.print("\033[0m");
        System.out.println("\n선택: ");

        int choice = scanner.nextInt();
        try {
            if (choice == 1) {
                processCardPayment();
            } else if (choice == 2) {
                processPointPayment();
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processCardPayment() throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.print("카드사 입력: ");
        String cardComp = scan.nextLine();

        String cardNumber = getValidInput(scan, "카드 정보 입력 (양식: 0000-0000-0000-0000): ",
                "\\d{4}-\\d{4}-\\d{4}-\\d{4}", "잘못된 입력입니다. 다시 입력해주세요. (양식: 0000-0000-0000-0000)");

        String cardExp = getValidCardExpiry(scan);

        String cardPw = getValidInput(scan, "카드 비밀번호 앞 2자리: ",
                "\\d{2}", "잘못된 입력입니다. 입력은 2자리 숫자여야 합니다.");

        try {
            System.out.println("카드 결제가 진행 중입니다...");
            Thread.sleep(1000); // 간단한 대기 시뮬레이션
            System.out.println("카드 결제가 완료되었습니다.");

            // 예약 정보 생성 및 저장
            int userId = Integer.parseInt(LoginSystem.getUserIndex());
            Booking newBooking = createBooking();
            bookingService.addBooking(newBooking.getUserId(),
                    newBooking.getAccommodationId(),
                    newBooking.getCheckInDate(),
                    newBooking.getCheckOutDate(),
                    newBooking.getNumGuests(),
                    newBooking.getTotalPrice());

            System.out.println("예약이 완료되었습니다.");
            System.out.println("예약 ID: " + newBooking.getBookingId());
            System.out.println("즐거운 숙박되세요. 감사합니다.");

        } catch (Exception e) {
            System.out.println("결제 실패: " + e.getMessage());
        }
    }
    
    private String getValidCardExpiry(Scanner scan) {
        while (true) {
            String input = getValidInput(scan, "카드 유효기한 입력 (양식: MM/YYYY): ",
                    "(0[1-9]|1[0-2])/(\\d{4})", "잘못된 입력입니다. 다시 입력해주세요. (양식: MM/YYYY)");
            
            try {
                // 입력된 날짜를 파싱
                String[] parts = input.split("/");
                int month = Integer.parseInt(parts[0]);
                int year = Integer.parseInt(parts[1]);
                
                // 현재 날짜 가져오기
                YearMonth currentYearMonth = YearMonth.now();
                YearMonth inputYearMonth = YearMonth.of(year, month);
                
                // 현재 날짜와 비교
                if (inputYearMonth.isBefore(currentYearMonth)) {
                    System.out.println("카드가 만료되었습니다. 다른 카드를 사용해주세요.");
                    continue;
                }
                
                return input;
            } catch (DateTimeException e) {
                System.out.println("유효하지 않은 날짜입니다. 다시 입력해주세요.");
            }
        }
    }
    
    private String getValidInput(Scanner scan, String prompt, String regex, String errorMessage) {
        System.out.print(prompt);
        String input;
        while (true) {
            input = scan.nextLine();
            if (input.matches(regex)) {
                break;
            } else {
                System.out.println(errorMessage);
            }
        }
        return input;
    }

    
    
    private void processPointPayment() throws IOException {
        int userId = Integer.parseInt(LoginSystem.getUserIndex());
        int totalPrice = ReservationHandler.getTotalPrice();

        // 포인트 결제 처리
        boolean success = pointPaymentService.processPointPayment(userId, totalPrice, createBooking());

        if (success) {
             System.out.println("포인트 결제가 완료되었습니다.");
             System.out.println("프로그램이 종료됩니다. ");
             System.out.println();
             System.out.print("\033[47m\033[30m");
              System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
             System.out.println("┃         프로그램 종료         ┃");
              System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            System.out.print("\033[0m");
            System.out.println();
            System.exit(0);
        } else {
            System.out.println("포인트가 부족합니다.");
            handleInsufficientPoints(userId, totalPrice);
        }
    }

    // 포인트 부족 처리 메서드
    private void handleInsufficientPoints(int userId, int totalPrice) throws IOException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
           System.out.print("\033[47m\033[30m");
            System.out.println("                     ┏━━━━━━━━━━━━┓                     ");
            System.out.println("┏━━━━━━━━━━━━━━━━━━━━┃ 포인트 부족┃━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                    ┗━━━━━━━━━━━━┛                    ┃");
            System.out.println("┃   포인트가 부족합니다. 다음 옵션 중 선택하세요:      ┃");
            System.out.println("┃ ┏━━━━━━━━━━━━━━━━━━━━━━┓  ┏━━━━━━━━━━━━━━━━━━━━━━┓   ┃");
            System.out.println("┃ ┃ [1] 포인트 충전      ┃  ┃ [2] 결제 취소   ㅤ   ┃   ┃");
            System.out.println("┃ ┗━━━━━━━━━━━━━━━━━━━━━━┛  ┗━━━━━━━━━━━━━━━━━━━━━━┛   ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            System.out.print("\033[0m");
            System.out.println();
            int choice = scanner.nextInt();

            if (choice == 1) {
                chargePoints(userId);
                System.out.println("\n포인트 충전이 완료되었습니다. 결제를 다시 시도합니다.");

                // 포인트 결제 재시도
                boolean success = pointPaymentService.processPointPayment(userId, totalPrice, createBooking());
                if (success) {
                 System.out.println("포인트 결제가 완료되었습니다. ");
                 System.out.println("프로그램이 종료됩니다. ");
                 System.out.println();
                 System.out.print("\033[47m\033[30m");
                   System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                  System.out.println("┃         프로그램 종료         ┃");
                   System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                System.out.print("\033[0m");
               System.out.println();
                    return;
                } else {
                    System.out.println("\n충전된 포인트로도 결제가 불가능합니다. 다시 시도해주세요.");
                }
            } else if (choice == 2) {
                System.out.println("결제가 취소되었습니다.");
                return;
            } else {
                System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }

    // 포인트 충전 메서드
    private void chargePoints(int userId) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\n충전할 포인트 금액을 입력하세요: ");
        int chargeAmount = scanner.nextInt();

        // 포인트 충전
        pointPaymentService.chargeUserPoints(userId, chargeAmount);
    }

    private Booking createBooking() throws NumberFormatException, IOException {
        int userId = Integer.parseInt(LoginSystem.getUserIndex());
        return new Booking(
            pointPaymentService.generateBookingIndex(),
            userId,
            ReservationHandler.getAccommodationId(),
            ReservationHandler.getCheckInDate(),
            ReservationHandler.getCheckOutDate(),
            ReservationHandler.getNumGuests(),
            ReservationHandler.getTotalPrice()
        );
    }

    private void saveBooking() throws IOException {
        Booking booking = createBooking();
        pointPaymentService.addBooking(booking);
        System.out.println("\n예약이 완료되었습니다. 예약 ID: " + booking.getBookingId());
    }



}

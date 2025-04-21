package com.test.admin;

import java.util.Scanner;
import com.test.accommodation.Accommodation;
import com.test.accommodation.AccommodationService;

public class AdminService {
    private final String ADMIN_ID = "admin"; // 기본 관리자 ID
    private final String ADMIN_PASSWORD = "1234"; // 기본 관리자 비밀번호
    private AccommodationService accommodationService;

    public AdminService(AccommodationService accommodationService) {
        this.accommodationService = accommodationService;
    }

    // 관리자 로그인
    public boolean login(String id, String password) {
        return ADMIN_ID.equals(id) && ADMIN_PASSWORD.equals(password);
    }

    // 숙소 추가
    public void addAccommodation() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\n숙소 이름: ");
        String name = scanner.nextLine();
        System.out.print("호스트 이름: ");
        String host = scanner.nextLine();
        System.out.print("숙소 지역: ");
        String area = scanner.nextLine();
        System.out.print("숙소 주소: ");
        String address = scanner.nextLine();
        System.out.print("최대 인원: ");
        int maxGuests = scanner.nextInt();
        System.out.print("1박 요금: ");
        int price = scanner.nextInt();
        scanner.nextLine(); // 버퍼 비우기
        System.out.print("공지사항: ");
        String notice = scanner.nextLine();

        // 숙소 ID는 자동 생성
        int newAccommodationId = accommodationService.getAccommodations().size() + 1;

        Accommodation accommodation = new Accommodation(newAccommodationId, host, area, address, name, maxGuests, price, notice);
        accommodationService.addAccommodation(accommodation);

        System.out.println("숙소가 성공적으로 추가되었습니다.");
    }

    // 숙소 수정
    public void modifyAccommodation() {
        Scanner scanner = new Scanner(System.in);
        accommodationService.showAllAccommodations(); // 모든 숙소 출력
        System.out.print("수정할 숙소 ID를 입력하세요: ");
        int accommodationId = scanner.nextInt();
        scanner.nextLine(); // 버퍼 비우기

        Accommodation accommodation = accommodationService.getAccommodationById(accommodationId);
        if (accommodation == null) {
            System.out.println("숙소를 찾을 수 없습니다.");
            return;
        }

        System.out.print("새로운 숙소 이름 (Enter 입력 시 기존 유지): ");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) accommodation.setAccommodationName(newName);

        System.out.print("새로운 숙소 지역 (Enter 입력 시 기존 유지): ");
        String newArea = scanner.nextLine();
        if (!newArea.isEmpty()) accommodation.setArea(newArea);

        System.out.print("새로운 숙소 주소 (Enter 입력 시 기존 유지): ");
        String newAddress = scanner.nextLine();
        if (!newAddress.isEmpty()) accommodation.setAddress(newAddress);

        System.out.print("새로운 최대 인원 (Enter 입력 시 기존 유지): ");
        String maxGuestsInput = scanner.nextLine();
        if (!maxGuestsInput.isEmpty()) accommodation.setMaxGuest(Integer.parseInt(maxGuestsInput));

        System.out.print("새로운 1박 요금 (Enter 입력 시 기존 유지): ");
        String priceInput = scanner.nextLine();
        if (!priceInput.isEmpty()) accommodation.setPrice(Integer.parseInt(priceInput));

        System.out.print("새로운 공지사항 (Enter 입력 시 기존 유지): ");
        String newNotice = scanner.nextLine();
        if (!newNotice.isEmpty()) accommodation.setNotice(newNotice);

        accommodationService.saveAccommodations(); // 변경된 정보 저장
        System.out.println("\n숙소 정보가 성공적으로 수정되었습니다.");
    }

    // 숙소 삭제
    public void deleteAccommodation() {
        Scanner scanner = new Scanner(System.in);
        accommodationService.showAllAccommodations(); // 모든 숙소 출력
        System.out.print("삭제할 숙소 ID를 입력하세요: ");
        int accommodationId = scanner.nextInt();

        boolean isDeleted = accommodationService.deleteAccommodation(accommodationId);
        if (isDeleted) {
            System.out.println("\n숙소가 성공적으로 삭제되었습니다.");
        } else {
            System.out.println("\n숙소를 찾을 수 없습니다.");
        }
    }
}

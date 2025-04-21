package com.test.accommodation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.test.booking.Booking;
import com.test.booking.BookingService;
import com.test.booking.ReservationHandler;
import com.test.payment.PaymentView;

public class AccommodationService {
	private List<Accommodation> accommodations;
	BookingService bookingService = new BookingService();
	static PaymentView paymentView = new PaymentView();
	private static final String FILE_PATH = "./data/accommodation_list.txt";

	public AccommodationService() {
		accommodations = new ArrayList<>();
		loadAccommodations();
	}

	
	// 생성자: 파일에서 숙소 데이터를 로드

	// 파일에서 숙소 데이터 로드
	private void loadAccommodations() {
		try {
			List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
			for (String line : lines) {
				accommodations.add(Accommodation.fromFile(line));
			}
		} catch (Exception e) {
			System.err.println("⚠️숙소 데이터를 로드하는 데 실패했습니다: " + e.getMessage());
		}
	}

	// 파일에 숙소 데이터 저장
	public void saveAccommodations() {
		List<String> lines = new ArrayList<>();
		for (Accommodation accommodation : accommodations) {
			lines.add(accommodation.toFileFormat());
		}
		try {
			Files.write(Paths.get(FILE_PATH), lines);
		} catch (Exception e) {
			System.err.println("⚠️숙소 데이터를 저장하는 데 실패했습니다: " + e.getMessage());
		}
	}

	// 숙소 목록 반환
	public List<Accommodation> getAccommodations() {
		return accommodations;
	}

	// 숙소 추가
	public void addAccommodation(Accommodation accommodation) {
		accommodations.add(accommodation);
		saveAccommodations(); // 파일에 저장
		System.out.println("➕새로운 숙소가 추가되었습니다.");
	}

	// 숙소 삭제
	public boolean deleteAccommodation(int accommodationId) {
		for (Accommodation accommodation : accommodations) {
			if (accommodation.getId() == accommodationId) {
				accommodations.remove(accommodation);
				saveAccommodations(); // 파일에 저장
				return true; // 삭제 성공
			}
		}
		return false; // 삭제 실패 (숙소 ID를 찾을 수 없음)
	}

	// 숙소 ID로 숙소 가져오기
	public Accommodation getAccommodationById(int accommodationId) {
		for (Accommodation accommodation : accommodations) {
			if (accommodation.getId() == accommodationId) {
				return accommodation;
			}
		}
		return null; // 숙소를 찾을 수 없는 경우
	}
	
	   private static String centerText(String text, int width) {
	       int padding = (width - text.length()) / 2;
	       String leftPadding = " ".repeat(Math.max(0, padding));
	       String rightPadding = " ".repeat(Math.max(0, width - text.length() - padding));
	       return leftPadding + text + rightPadding;
	   }

	// 모든 숙소 출력
	public void showAllAccommodations() {
		System.out.println("+" + "-".repeat(50) + "+");
		System.out.println("|" + " ".repeat(18) + "숙소 리스트" + " ".repeat(19) + "|");
		System.out.println("+" + "-".repeat(50) + "+");

		if (accommodations.isEmpty()) {
			System.out.println("🈚등록된 숙소가 없습니다.");
			return;
		}

		for (Accommodation accommodation : accommodations) {
			System.out.printf("ID: %d, 이름: %s, 지역: %s, 1박 요금: %,d원\n", accommodation.getId(),
					accommodation.getAccommodationName(), accommodation.getArea(), accommodation.getPrice());
		}
	}
//

	
	public static void groupRandomlist2() throws IOException {
	    String accomfilePath = "./data/accommodation_list.txt"; // 맥 환경
	    String bookingList = "./data/booking_list.txt"; // 맥 환경

	    ArrayList<Accommodation> accommodations = new ArrayList<>();
	    Map<Integer, List<Booking>> bookingData = new HashMap<>();

	    // booking_list 읽어서 Map에 저장
	    try (BufferedReader br = new BufferedReader(new FileReader(bookingList))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] parts = line.split("■");
	            int accommodationId = Integer.parseInt(parts[2].trim()); // 숙소 ID
	            String checkInDate = parts[3].trim();
	            String checkOutDate = parts[4].trim();

	            // Booking 객체 생성
	            Booking booking = new Booking(accommodationId, accommodationId, accommodationId, checkInDate,
	                    checkOutDate, accommodationId, accommodationId);

	            // Map에 데이터 추가
	            bookingData.putIfAbsent(accommodationId, new ArrayList<>());
	            bookingData.get(accommodationId).add(booking);
	        }
	    } catch (Exception e) {
	        System.out.println("Error reading the booking list: " + e.getMessage());
	    }

	    // accommodation_list 읽기
	    try (BufferedReader br = new BufferedReader(new FileReader(accomfilePath))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] parts = line.split("■");

	            if (parts.length > 6) { // 데이터 유효성 검사
	                int accommodationId = Integer.parseInt(parts[0].trim());
	                String username = parts[1].trim();
	                String area = parts[2].trim();
	                String address = parts[3].trim();
	                String accommodationName = parts[4].trim();
	                int maxGuest = Integer.parseInt(parts[5].trim());
	                int price = Integer.parseInt(parts[6].trim());
	                String notice = parts[7].trim();

	                accommodations.add(new Accommodation(accommodationId, username, area, address, accommodationName,
	                        maxGuest, price, notice));
	            } else {
	                System.out.println("Invalid line format: " + line);
	            }
	        }
	    } catch (IOException e) {
	        System.out.println("Error reading the file: " + e.getMessage());
	    }

	    Scanner scanner = new Scanner(System.in);
	    Set<String> validRegions = Set.of("서울", "대구", "대전", "부산", "제주", "강릉", "경주", "속초");

	    // 지역 입력받기
	    String searchRegion;
	    while (true) {
	        System.out.print("\n🔍검색할 지역을 입력하세요\n(서울, 대구, 대전, 부산, 제주, 강릉, 경주, 속초 중 선택): ");
	        searchRegion = scanner.nextLine().trim();

	        if (validRegions.contains(searchRegion)) {
	            break; // 유효한 지역 입력 시 루프 종료
	        } else {
	            System.out.println("\n⚠️검색 가능한 지역이 아닙니다. 다시 입력해주세요.\n");
	        }
	    }

	    // 게스트 수 입력받기
	    int guestCount;
	    while (true) {
	        System.out.print("\n👨‍👩‍👧‍👦숙박할 인원수를 입력하세요: ");
	        if (scanner.hasNextInt()) {
	            guestCount = scanner.nextInt();
	            if (guestCount > 0) {
	                break; // 유효한 인원수 입력 시 루프 종료
	            } else {
	                System.out.println("\n⚠️숙박 인원수는 1명 이상이어야 합니다. 다시 입력해주세요.\n");
	            }
	        } else {
	            System.out.println("\n🔢숫자를 입력해주세요.\n");
	            scanner.next(); // 잘못된 입력 제거
	        }
	    }

	    String checkInDate, checkOutDate;

	    while (true) {
	        System.out.println("\n🛌체크인 날짜를 선택해주세요:");
	        checkInDate = selectDateFromCalendar();

	        System.out.println("\n🛏️체크아웃 날짜를 선택해주세요:");
	        checkOutDate = selectDateFromCalendar();

	        if (areValidDates(checkInDate, checkOutDate)) {
	            break;
	        } else {
	            System.out.println("⚠️체크아웃 날짜는 체크인 날짜 이후여야 합니다. 다시 선택해주세요.");
	        }
	    }

	    // 조건에 따라 숙소 필터링
	    List<Accommodation> filteredAccommodations = new ArrayList<>();

	    for (Accommodation accom : accommodations) {
	        if (accom.getArea().equals(searchRegion) && accom.getMaxGuest() >= guestCount) {
	            // 예약 데이터를 확인하여 예약 가능한 숙소인지 필터링
	            List<Booking> bookings = bookingData.getOrDefault(accom.getId(), new ArrayList<>());
	            boolean isAvailable = true;

	            for (Booking booking : bookings) {
	                if (booking.overlapsWith(checkInDate, checkOutDate)) {
	                    isAvailable = false;
	                    break;
	                }
	            }

	            if (isAvailable) {
	                filteredAccommodations.add(accom);
	            }
	        }
	    }

	    if (!filteredAccommodations.isEmpty()) {
	        // 숙소 리스트 출력
	        displayAccommodationList(filteredAccommodations);

	        int selectedNumber = -1;
	        boolean isBooking = false;
	        do {
	            System.out.print(
	                    "\r\n🔍더 자세히 보고 싶은 숙소의 번호를 입력하세요 (1 ~ " + Math.min(20, filteredAccommodations.size()) + "): ");
	            while (!scanner.hasNextInt()) {
	                System.out.println("⚠️유효한 숫자를 입력해주세요.");
	                scanner.next(); // 잘못된 입력 제거
	                System.out.print("\r\n🔍더 자세히 보고 싶은 숙소의 번호를 입력하세요 (1 ~ " + Math.min(20, filteredAccommodations.size())
	                        + "): ");
	            }
	            selectedNumber = scanner.nextInt();

	            if (selectedNumber >= 1 && selectedNumber <= filteredAccommodations.size()) {
	                Accommodation selectedAccommodation = filteredAccommodations.get(selectedNumber - 1);
	                System.out.print("\033[47m\033[30m");
	                System.out.println("+" + "-".repeat(50) + "+");
	                System.out.println(centerText("[선택한 숙소 정보]", 45));
	                System.out.println("+" + "-".repeat(50) + "+");
	                System.out.print("\033[0m");
	                System.out.println();
	                System.out.printf("%-10s: %-35s \n", "📛이름", selectedAccommodation.getAccommodationName());
	                System.out.printf("%-10s: %-35s \n", "🚩주소", selectedAccommodation.getAddress());
	                System.out.printf("%-10s: %-35s \n", "👨‍👩최대 인원 ", selectedAccommodation.getMaxGuest() + "명");
	                System.out.printf("%-10s: %-35s \n", "💲가격", String.format("%,d원", selectedAccommodation.getPrice()));
	                System.out.println("\nℹ️공지사항                                      ");
	                System.out.println("+" + "-".repeat(50) + "+");
	                printFormattedNotice(selectedAccommodation.getNotice(), 48); // 공지사항 출력
	                System.out.println("+" + "-".repeat(50) + "+"); 
	                
	                
	                
	                int userChoice = -1;
	                do {
	                    System.out.print("\n1.숙소 리스트로 돌아가기\n2.예약하기\n\n옵션을 선택하세요: ");
	                    while (!scanner.hasNextInt()) {
	                        System.out.println("\n⚠️유효한 숫자를 입력해주세요.");
	                        scanner.next();
	                        System.out.print("\n1.숙소 리스트로 돌아가기\n2.예약하기\n옵션을 선택하세요: ");
	                    }
	                    userChoice = scanner.nextInt();

	                    if (userChoice == 1) {
	                        System.out.println("\n숙소 리스트로 돌아갑니다.");
	                        displayAccommodationList(filteredAccommodations);
	                        break;
	                    } else if (userChoice == 2) {
	                        System.out.println("\n예약을 진행합니다.");
	                        ReservationHandler.setReservationDetails(selectedAccommodation.getId(), checkInDate,
	                                checkOutDate, guestCount, selectedAccommodation.getPrice());
	                        paymentView.showPaymentOptions();
	                        isBooking = true;
	                        break;
	                    } else {
	                        System.out.println("\n⚠️잘못된 선택입니다. 다시 입력해주세요.");
	                    }
	                } while (userChoice != 1 && userChoice != 2);

	                if (isBooking) {
	                    break;
	                }
	            } else {
	                System.out.println("\n⚠️잘못된 번호를 입력하셨습니다. 다시 입력해주세요.");
	            }
	        } while (!isBooking);
	    } else {
	        System.out.println("\n✖️예약 가능한 숙소가 없습니다.");
	    }
	}
	

	private static void displayAccommodationList(List<Accommodation> filteredAccommodations) {

		System.out.println("예약 가능한 추천 숙소 리스트:");
		System.out.println("[번호]\t[이름]\t\t [최대 인원]\t  [가격]\t[주소]\t");

		for (int i = 0; i < Math.min(20, filteredAccommodations.size()); i++) {
			Accommodation accom = filteredAccommodations.get(i);
			System.out.printf("%d\t%-5s\t\t%2d명\t%,9d원\t%5s\t%n", (i + 1), accom.getAccommodationName(),
					accom.getMaxGuest(), accom.getPrice(), accom.getAddress());
		}
	}

	 private static String selectDateFromCalendar() {
	      LocalDate today = LocalDate.now(); // 현재 날짜
	      LocalDate calendarMonth = today.withDayOfMonth(1); // 달력의 첫날 설정

	      Scanner scanner = new Scanner(System.in);
	      String selectedDate = "";

	      while (true) {
	         
	         System.out.print("\033[47m\033[30m"); // 흰색 배경(47) + 검정 텍스트(30)

	           // 기존 코드
	           System.out.println("\n╔══════════════════════════════════════════════════════╗");
	           System.out.printf("║                     %d년 %02d월                      ║\n", calendarMonth.getYear(), calendarMonth.getMonthValue());
	           System.out.println("╠══════════════════════════════════════════════════════╣");
	           displayCalendar(calendarMonth); // 사용자 정의 달력 출력 함수
	           System.out.println("╚══════════════════════════════════════════════════════╝\n");

	           // 스타일 초기화 (배경/텍스트 색상 원래대로)
	           System.out.print("\033[0m");
	         

	         System.out.print("[다음 달 : + / 지난 달 : - / 날짜 선택 : 숫자] \n\n입력 : ");
	         String input = scanner.nextLine().trim();

	         if (input.equals("+")) {
	            calendarMonth = calendarMonth.plusMonths(1); // 다음 달로 이동
	         } else if (input.equals("-")) {
	            calendarMonth = calendarMonth.minusMonths(1); // 저번 달로 이동
	         } else if (input.matches("\\d{1,2}")) { // 숫자 입력
	            int day = Integer.parseInt(input);
	            try {
	               selectedDate = calendarMonth.withDayOfMonth(day).toString();
	               break; // 유효한 날짜를 선택하면 종료
	            } catch (DateTimeException e) {
	               System.out.println("\n유효하지 않은 날짜입니다. 다시 선택해주세요.");
	            }
	         } else {
	            System.out.println("\n잘못된 입력입니다. 다시 선택해주세요.");
	         }
	      }
	      return selectedDate;
	   }

	   //달력 출력 함수
	   private static void displayCalendar(LocalDate calendarMonth) {
	      LocalDate firstDay = calendarMonth.withDayOfMonth(1); // 해당 월의 첫날
	      int firstDayOfWeek = firstDay.getDayOfWeek().getValue(); // 첫날의 요일 (1=월, 7=일)
	      int daysInMonth = calendarMonth.lengthOfMonth(); // 해당 월의 총 일수
	      
	      System.out.println("[일]\t[월]\t[화]\t[수]\t[목]\t[금]\t[토]");

	      // 첫 주 공백 처리
	      for (int i = 0; i < (firstDayOfWeek % 7); i++) {
	         System.out.print("\t");
	      }

	      // 날짜 출력
	      for (int day = 1; day <= daysInMonth; day++) {
	         System.out.printf("%2d\t", day);
	         if ((day + (firstDayOfWeek % 7) - 1) % 7 == 6) { // 토요일 후 줄 바꿈
	            System.out.println();
	         }
	      }
	      System.out.println();
	   }

	
	public static void printFormattedNotice(String notice, int maxLength) {
		int start = 0;
		while (start < notice.length()) {
			// 현재 출력할 부분 계산
			int end = Math.min(start + maxLength, notice.length());
			String line = notice.substring(start, end);
			System.out.printf(" %-35s \n", line); // 좌측 정렬 및 너비 조정
			start = end; // 다음 부분으로 이동
		}
	}

	private static boolean areValidDates(String checkInDate, String checkOutDate) {

		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			// 문자열을 LocalDate로 변환
			LocalDate checkIn = LocalDate.parse(checkInDate, formatter);
			LocalDate checkOut = LocalDate.parse(checkOutDate, formatter);
			LocalDate today = LocalDate.now(); // 현재 날짜

			// 체크인 날짜가 현재 날짜보다 이전이면
			if (checkIn.isBefore(today)) {
				System.out.println("\n⚠️체크인 날짜는 현재 날짜 이후여야 합니다.");
				return false;
			}

			// 체크아웃 날짜가 체크인 날짜보다 이전이면
			if (checkOut.isBefore(checkIn)) {
				System.out.println("\n⚠️체크아웃 날짜는 체크인 날짜 이후여야 합니다.");
				return false;
			}

			// 체크아웃 날짜가 현재 날짜보다 이전이면
			if (checkOut.isBefore(today)) {
				System.out.println("\n⚠️체크아웃 날짜는 현재 날짜 이후여야 합니다.");
				return false;
			}

			return true;
		} catch (DateTimeParseException e) {
			System.out.println("\n⚠️유효하지 않은 날짜 형식입니다.");
			return false;
		}
	}
	
	public static void randomList() throws IOException {
	    String filePath = "./data/accommodation_list.txt"; // 맥 환경

	    ArrayList<Accommodation> accommodations = new ArrayList<>();
	    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] parts = line.split("■");

	            if (parts.length > 6) {
	                int accommodationId = Integer.parseInt(parts[0].trim());
	                String username = parts[1].trim();
	                String area = parts[2].trim();
	                String address = parts[3].trim();
	                String accommodationName = parts[4].trim();
	                int maxGuest = Integer.parseInt(parts[5].trim());
	                int price = Integer.parseInt(parts[6].trim());
	                String notice = parts[7].trim();

	                accommodations.add(new Accommodation(accommodationId, username, area, address, accommodationName,
	                        maxGuest, price, notice));
	            }
	        }
	    } catch (IOException e) {
	        System.out.println("Error reading the file: " + e.getMessage());
	    }

	    if (!accommodations.isEmpty()) {
	        Collections.shuffle(accommodations);
	        System.out.print("\033[47m\033[30m");
	        System.out.println();
        	System.out.println("====================================================");
            System.out.println("              인기 급 상승 숙소 리스트              ");      			 			System.out.println("====================================================");
            System.out.print("\033[0m");
            System.out.println();
	        System.out.println("[번호]\t[지역]\t[숙소이름]\t[최대 인원]   [가격]");
	        for (int i = 0; i < Math.min(20, accommodations.size()); i++) {
	            Accommodation accom = accommodations.get(i);
	            System.out.printf("%d\t%s\t%2s\t  %5d\t%,10d원%n", (i + 1), accom.getArea(), accom.getAccommodationName(),
	                    accom.getMaxGuest(), accom.getPrice());
	        }

	        Scanner scanner = new Scanner(System.in);
			System.out.println("====================================================");
	        System.out.print("\n🔍더 자세히 보고 싶은 숙소의 번호를 입력하세요 (1 ~ 20): ");
	        int selectedNumber = scanner.nextInt();
	        if (selectedNumber >= 1 && selectedNumber <= accommodations.size()) {
	            Accommodation selectedAccommodation = accommodations.get(selectedNumber - 1);
	            
	            System.out.println();
	            System.out.print("\033[47m\033[30m");
	            System.out.println();
	        	System.out.println("====================================================");
	            System.out.println("              선택한 숙소 정보                      ");      			 				System.out.println("====================================================");
	            System.out.print("\033[0m");
	            System.out.println();
	           
	            System.out.println("📛이름: " + selectedAccommodation.getAccommodationName());
	            System.out.println("🚩‍주소: " + selectedAccommodation.getAddress());
	            System.out.println("👩‍👧‍👦최대 인원: " + selectedAccommodation.getMaxGuest() + "명");
	            System.out.printf("💲가격: %,d원\n" , selectedAccommodation.getPrice() );
	            System.out.println("+--------------------------------------------------+");
				System.out.println("ℹ️공지사항: ");
				printFormattedNotice(selectedAccommodation.getNotice(), 40);
	            System.out.println("+--------------------------------------------------+");

	            System.out.print("\n1.예약하기\n2.돌아가기\n\n✔️선택: ");
	            int choice = scanner.nextInt();
	            if (choice == 1) {
	                // 예약하기로 이동
	                AccommodationBooking.runBooking(selectedAccommodation);
	            }
	        } else {
	            System.out.println("⚠️잘못된 입력입니다.");
	        }
	    } else {
	        System.out.println("🈚추천할 숙소가 없습니다.");
	    }
	}
}



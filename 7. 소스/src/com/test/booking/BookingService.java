package com.test.booking;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.test.accommodation.Accommodation;
import com.test.accommodation.AccommodationService;
import com.test.util.FileUtil;
import com.test.util.LoginSystem;
import com.test.util.ValidationUtil;

/**
 * BookingService 클래스는 숙소 예약 관리를 위한 다양한 기능을 제공합니다.
 * 이 클래스는 예약 추가, 수정, 취소 및 조회와 같은 주요 기능을 포함하며,
 * 파일을 통해 예약 데이터를 영구적으로 저장 및 로드할 수 있습니다.
 */
public class BookingService {

	private List<Booking> bookings = new ArrayList<>();
	private static final String FILE_PATH = "./data/booking_list.txt"; // 예약 데이터를 저장하는 파일 경로

	/**
	 * BookingService 생성자.
	 * 예약 데이터를 지정된 파일 경로에서 로드하여 초기화합니다.
	 */
	public BookingService() {
		loadBookings();
	}

	/**
	 * 새로운 예약을 추가합니다.
	 *
	 * @param userId          예약을 생성하는 사용자의 고유 Index.
	 * @param accommodationId 예약하려는 숙소의 고유 Index.
	 * @param checkInDate     체크인 날짜 (형식: "yyyy-MM-dd").
	 * @param checkOutDate    체크아웃 날짜 (형식: "yyyy-MM-dd").
	 * @param numGuests       예약 인원 수.
	 * @param pricePerNight   숙소의 1박당 요금.
	 * @return 생성된 Booking 객체를 반환하며, 입력이 유효하지 않은 경우 null을 반환합니다.
	 *
	 * @throws IllegalArgumentException 체크인 및 체크아웃 날짜가 유효하지 않을 경우 예외를 던집니다.
	 * @implNote 생성된 예약은 파일에 자동으로 저장됩니다.
	 */
	public Booking addBooking(int userId, int accommodationId, String checkInDate, String checkOutDate, int numGuests, int pricePerNight) {
		long stayDuration = ValidationUtil.calculateDaysBetween(checkInDate, checkOutDate);
		if (stayDuration <= 0) {
			System.out.println("체크인 날짜와 체크아웃 날짜가 유효하지 않습니다.");
			return null;
		}
		int totalPrice = (int) (stayDuration * pricePerNight);
		Booking booking = new Booking(generateBookingId(), userId, accommodationId, checkInDate, checkOutDate, numGuests, totalPrice);
		bookings.add(booking);
		saveBookings();
		return booking;
	}

	/**
	 * 기존 예약을 수정합니다.
	 *
	 * @param bookingId       수정하려는 예약의 고유 Index.
	 * @param newCheckInDate  새로운 체크인 날짜 (형식: "yyyy-MM-dd").
	 * @param newCheckOutDate 새로운 체크아웃 날짜 (형식: "yyyy-MM-dd").
	 * @param newNumGuests    새로운 예약 인원 수.
	 * @return 수정이 성공하면 true, 실패하면 false를 반환합니다.
	 *
	 * @throws IllegalArgumentException 숙박 기간이 유효하지 않을 경우 예외를 던집니다.
	 * @implNote 수정된 예약은 파일에 자동으로 저장됩니다.
	 */
	public boolean modifyBooking(int bookingId, String newCheckInDate, String newCheckOutDate, int newNumGuests) {
		for (Booking booking : bookings) {
			if (booking.getBookingId() == bookingId) {
				long stayDuration = ValidationUtil.calculateDaysBetween(newCheckInDate, newCheckOutDate);
				if (stayDuration <= 0) {
					System.out.println("숙박 기간이 유효하지 않습니다.");
					return false;
				}
				AccommodationService accommodationService = new AccommodationService();
				Accommodation accommodation = accommodationService.getAccommodationById(booking.getAccommodationId());
				if (accommodation == null) {
					System.out.println("숙소 정보를 찾을 수 없습니다.");
					return false;
				}
				int totalPrice = (int) (stayDuration * accommodation.getPrice());
				booking.setCheckInDate(newCheckInDate);
				booking.setCheckOutDate(newCheckOutDate);
				booking.setNumGuests(newNumGuests);
				booking.setTotalPrice(totalPrice);
				saveBookings();
				return true;
			}
		}
		return false;
	}

	/**
	 * 기존 예약을 취소합니다.
	 *
	 * @param bookingId 취소하려는 예약의 고유 Index.
	 * @param userIndex 예약을 생성한 사용자의 고유 Index.
	 * @param password  사용자의 비밀번호.
	 * @return 예약 취소가 성공하면 true, 실패하면 false를 반환합니다.
	 * @throws IOException 파일 저장 중 문제가 발생할 경우 예외를 던집니다.
	 */
	public boolean cancelBooking(int bookingId, int userIndex, String password) throws IOException {
		for (Booking booking : bookings) {
			if (booking.getBookingId() == bookingId && Integer.parseInt(LoginSystem.getUserIndex()) == userIndex && LoginSystem.getUserPassword().equals(password)) {
				bookings.remove(booking);
				saveBookings();
				return true;
			}
		}
		return false;
	}

	/**
	 * 특정 사용자의 모든 예약을 조회합니다.
	 *
	 * @param userIndex 조회하려는 사용자의 고유 Index.
	 * @return 사용자의 예약 목록을 반환하며, 예약이 없을 경우 빈 리스트를 반환합니다.
	 */
	public List<Booking> getUserBookings(int userIndex) {
		List<Booking> userBookings = new ArrayList<>();
		for (Booking booking : bookings) {
			if (booking.getUserId() == userIndex) {
				userBookings.add(booking);
			}
		}
		return userBookings;
	}

	/**
	 * 예약 데이터를 파일에서 로드합니다.
	 *
	 * @implNote 파일이 없거나 비어 있을 경우 빈 리스트로 초기화됩니다.
	 */
	private void loadBookings() {
		List<String> lines = FileUtil.readFromFile(FILE_PATH);
		for (String line : lines) {
			bookings.add(Booking.fromFile(line));
		}
	}

	/**
	 * 현재 예약 데이터를 파일에 저장합니다.
	 *
	 * @implNote 예약 데이터는 지정된 파일 경로에 저장됩니다.
	 */
	private void saveBookings() {
		List<String> lines = new ArrayList<>();
		for (Booking booking : bookings) {
			lines.add(booking.toFileFormat());
		}
		FileUtil.writeToFile(FILE_PATH, lines);
	}

	/**
	 * 새로운 예약 ID를 생성합니다.
	 *
	 * @return 예약 목록의 크기를 기반으로 고유한 예약 ID를 반환합니다.
	 */
	private int generateBookingId() {
		return bookings.size() == 0 ? 1 : bookings.get(bookings.size() - 1).getBookingId() + 1;
	}

	/**
	 * 특정 숙소의 예약된 날짜 목록을 반환합니다.
	 *
	 * @param accommodationId 예약된 날짜를 조회하려는 숙소의 고유 Index.
	 * @return LocalDate 객체 리스트로 예약된 날짜들을 반환합니다.
	 */
	public List<LocalDate> getBookedDatesByAccommodationId(int accommodationId) {
		List<LocalDate> bookedDates = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		for (Booking booking : bookings) {
			if (booking.getAccommodationId() == accommodationId) {
				LocalDate checkIn = LocalDate.parse(booking.getCheckInDate(), formatter);
				LocalDate checkOut = LocalDate.parse(booking.getCheckOutDate(), formatter);
				for (LocalDate date = checkIn; date.isBefore(checkOut); date = date.plusDays(1)) {
					bookedDates.add(date);
				}
			}
		}
		return bookedDates;
	}

	/**
	 * 특정 예약의 총 금액을 업데이트합니다.
	 *
	 * @param bookingId  업데이트하려는 예약의 고유 Index.
	 * @param totalPrice 새로 설정할 총 금액.
	 * @return 업데이트가 성공하면 true, 실패하면 false를 반환합니다.
	 */
	public boolean updateBookingTotalPrice(int bookingId, int totalPrice) {
		for (Booking booking : bookings) {
			if (booking.getBookingId() == bookingId) {
				booking.setTotalPrice(totalPrice);
				saveBookings();
				return true;
			}
		}
		return false;
	}
}

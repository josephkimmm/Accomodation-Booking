package com.test.booking;

public class Booking {

	private int bookingId;
	private int userId;
	private int accommodationId;
	private String checkInDate;
	private String checkOutDate;
	private int numGuests;
	private int totalPrice;

	// 생성자
	public Booking(int bookingId, int userId, int accommodationId, String checkInDate, String checkOutDate, int numGuests, int totalPrice) {
		this.bookingId = bookingId;
		this.userId = userId;
		this.accommodationId = accommodationId;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.numGuests = numGuests;
		this.totalPrice = totalPrice;
	}


	// Getters and Setters
	public int getBookingId() {
		return bookingId;
	}

	public int getUserId() {
		return userId;
	}

	public int getAccommodationId() {
		return accommodationId;
	}

	public String getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}

	public String getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public int getNumGuests() {
		return numGuests;
	}

	public void setNumGuests(int numGuests) {
		this.numGuests = numGuests;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	// Convert to file format
	public String toFileFormat() {
		return bookingId + "■" + userId + "■" + accommodationId + "■" +
				(checkInDate != null ? checkInDate : "N/A") + "■" +
				(checkOutDate != null ? checkOutDate : "N/A") + "■" +
				numGuests + "■" + totalPrice;
	}


	public static Booking fromFile(String line) {
	    // 데이터를 "■"로 분리
	    String[] parts = line.split("■");

	    // 배열 크기 확인 (필드 개수 검증)
	    if (parts.length < 7) {
	        throw new IllegalArgumentException("Invalid data format: " + line);
	    }

	    try {
	        return new Booking(
	            Integer.parseInt(parts[0]),  // 예약 ID
	            Integer.parseInt(parts[1]),  // 사용자 ID
	            Integer.parseInt(parts[2]),  // 숙소 ID
	            parts[3],                    // 체크인 날짜
	            parts[4],                    // 체크아웃 날짜
	            Integer.parseInt(parts[5]),  // 게스트 수
	            Integer.parseInt(parts[6])   // 총 가격
	        );
	        
	    } catch (NumberFormatException e) {
	        throw new IllegalArgumentException("Invalid number format in data: " + line, e);
	    }
	}

	public boolean overlapsWith(Booking date) {
		 return (checkInDate.equals(this.checkInDate) || checkOutDate.equals(this.checkOutDate) ||
		            (checkInDate.compareTo(this.checkInDate) >= 0 && checkInDate.compareTo(this.checkOutDate) < 0) ||
		            (checkOutDate.compareTo(this.checkInDate) > 0 && checkOutDate.compareTo(this.checkOutDate) <= 0));
		}

	public boolean overlapsWith(String checkInDate2, String checkOutDate2) {
		 return (checkInDate.equals(this.checkInDate) || checkOutDate.equals(this.checkOutDate) ||
		            (checkInDate.compareTo(this.checkInDate) >= 0 && checkInDate.compareTo(this.checkOutDate) < 0) ||
		            (checkOutDate.compareTo(this.checkInDate) > 0 && checkOutDate.compareTo(this.checkOutDate) <= 0));
		}
		
}

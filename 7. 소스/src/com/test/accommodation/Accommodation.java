package com.test.accommodation;

public class Accommodation {
    private int accommodationId;
    private String username;
    private String area;
    private String address;
    private String accommodationName;
    private int maxGuest;
    private int price;
    private String notice;

    // Constructor
    public Accommodation(int accommodationId, String username, String area, String address, String accommodationName, int maxGuest, int price, String notice) {
        this.accommodationId = accommodationId;
        this.username = username;
        this.area = area;
        this.address = address;
        this.accommodationName = accommodationName;
        this.maxGuest = maxGuest;
        this.price = price;
        this.notice = notice;
    }

    // Getters
    public int getId() {
        return accommodationId;
    }
    
	public String getArea() {
		// TODO Auto-generated method stub
		return area;
	}

    public String getAccommodationName() {
        return accommodationName;
    }

    public String getAddress() {
        return address;
    }

    public int getPrice() {
        return price;
    }
    
    public int getMaxGuest() {
    	return maxGuest;
    }

    public String getNotice() {
        return notice;
    }
    
    

    public void setAccommodationId(int accommodationId) {
		this.accommodationId = accommodationId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setAccommodationName(String accommodationName) {
		this.accommodationName = accommodationName;
	}

	public void setMaxGuest(int maxGuest) {
		this.maxGuest = maxGuest;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

    // 파일 저장 형식
    public String toFileFormat() {
        return accommodationId + "■" + username + "■" + area + "■" + address + "■" + accommodationName + "■" + maxGuest + "■" + price + "■" + notice;
    }

	// File parsing
    public static Accommodation fromFile(String line) {
        String[] parts = line.split("■");
        return new Accommodation(
            Integer.parseInt(parts[0]),
            parts[1],
            parts[2],
            parts[3],
            parts[4],
            Integer.parseInt(parts[5]),
            Integer.parseInt(parts[6]),
            parts[7]
        );
    }

}

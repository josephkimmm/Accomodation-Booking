package com.test.review;

import java.io.IOException;

public class Review {
    private int reviewId;
    private int userId;
    private String userName; // 작성자 이름 추가
    private int accommodationId;
    private String content;
    private int rating;

    // Constructor
    public Review(int reviewId, int userId, String userName, int accommodationId, String content, int rating) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.userName = userName;
        this.accommodationId = accommodationId;
        this.content = content;
        this.rating = rating;
    }

    // Getters and Setters


    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(int accommodationId) {
        this.accommodationId = accommodationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    // 파일 저장 형식
    public String toFileFormat() throws IOException {
        return reviewId + "■" + userId + "■" + userName + "■" + accommodationId + "■" + content + "■" + rating;
    }

    // 파일로부터 객체 생성
    public static Review fromFile(String line) {
        String[] parts = line.split("■");
        return new Review(
                Integer.parseInt(parts[0]), // reviewId
                Integer.parseInt(parts[1]), // userId
                parts[2],                   // userName
                Integer.parseInt(parts[3]), // accommodationId
                parts[4],                   // content
                Integer.parseInt(parts[5])  // rating
        );
    }
}

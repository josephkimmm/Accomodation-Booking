package com.test.review;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ReviewService {
    private List<Review> reviews = new ArrayList<>();
    private static final String FILE_PATH = "./data/reviews.txt";

    public ReviewService() {
        loadReviews();
    }

    // 리뷰 데이터 로드
    private void loadReviews() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            for (String line : lines) {
                reviews.add(Review.fromFile(line));
            }
        } catch (Exception e) {
            System.err.println("Failed to load reviews: " + e.getMessage());
        }
    }

    // 리뷰 데이터 저장
    private void saveReviews() throws IOException {
        List<String> lines = new ArrayList<>();
        for (Review review : reviews) {
            lines.add(review.toFileFormat());
        }
        try {
            Files.write(Paths.get(FILE_PATH), lines);
        } catch (Exception e) {
            System.err.println("Failed to save reviews: " + e.getMessage());
        }
    }

    // 유효성 검사: 평점 범위 확인
    private boolean isValidRating(int rating) {
        return rating >= 1 && rating <= 5;
    }

    // 중복 리뷰 확인
    public boolean isDuplicateReview(int userId, int accommodationId) {
        for (Review review : reviews) {
            if (review.getUserId() == userId && review.getAccommodationId() == accommodationId) {
                return true;
            }
        }
        return false;
    }
    
 // 리뷰 추가
    public boolean addReview(int userId, String userName, int accommodationId, String content, int rating, String checkOutDate) throws IOException {
        // 중복 리뷰 방지
        if (isDuplicateReview(userId, accommodationId)) {
            System.out.println("이미 이 숙소에 리뷰를 작성하셨습니다.");
            return false;
        }

        // 평점 유효성 검사
        if (!isValidRating(rating)) {
            System.out.println("평점은 1~5 사이의 값만 입력 가능합니다.");
            return false;
        }

        // 체크아웃 날짜 유효성 검사
        if (!isCheckOutDateValid(checkOutDate)) {
            System.out.println("체크아웃 날짜가 아직 지나지 않았습니다. 체크아웃 후에 리뷰를 작성할 수 있습니다.");
            return false;
        }

        // 새로운 리뷰 추가
        int newReviewId = reviews.size() + 1; // 간단히 리뷰 ID 생성
        Review newReview = new Review(newReviewId, userId, userName, accommodationId, content, rating);
        reviews.add(newReview);
        saveReviews();
        System.out.println("리뷰가 성공적으로 등록되었습니다.");
        return true;
    }

    // 숙소 ID로 리뷰 조회
    public List<Review> getReviewsByAccommodationId(int accommodationId) {
        List<Review> result = new ArrayList<>();
        for (Review review : reviews) {
            if (review.getAccommodationId() == accommodationId) {
                result.add(review);
            }
        }
        return result;
    }
    
 // 특정 숙소의 평균 평점 계산
    public double calculateAverageRating(int accommodationId) {
        int totalRating = 0;
        int reviewCount = 0;

        for (Review review : reviews) {
            if (review.getAccommodationId() == accommodationId) {
                totalRating += review.getRating();
                reviewCount++;
            }
        }

        if (reviewCount == 0) {
            return 0.0; // 리뷰가 없으면 평균 평점 0.0 반환
        }

        return (double) totalRating / reviewCount; // 평균 평점 계산
    }
    
 // 체크아웃 날짜 유효성 검사
    private boolean isCheckOutDateValid(String checkOutDate) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            LocalDate checkOut = LocalDate.parse(checkOutDate, formatter);
            return !checkOut.isAfter(today); // 체크아웃 날짜가 오늘 이전이어야 유효
        } catch (DateTimeParseException e) {
            System.out.println("유효하지 않은 날짜 형식입니다. (올바른 형식: yyyy-MM-dd)");
            return false;
        }
    }
    
}

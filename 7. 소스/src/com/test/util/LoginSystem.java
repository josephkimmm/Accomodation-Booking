package com.test.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


import com.test.user.User;
import com.test.user.UserView;


public class LoginSystem {
//    private static final String LOGIN_FILE = ".\\data\\loginUser.txt"; //윈도우 환경
//    private static final String USER_FILE = ".\\data\\members.txt"; // 윈도우 환경
    private static final String LOGIN_FILE = "./data/loginUser.txt"; //맥 환경
    private static final String USER_FILE = "./data/members.txt";  // 맥 환경



    // 로그인
    public static void login(String userId, String password) throws IOException {
    	UserView userview = new UserView();
        File file = new File(USER_FILE);

        // 유저 파일이 없으면 오류
        if (!file.exists()) {
            System.out.println("⚠️회원 정보가 없습니다. 먼저 회원가입을 해주세요.");
            return;
        }

     // 아이디와 비밀번호 검증
        boolean isValid = false;
        int userIndex = -1; // 사용자의 인덱스를 저장할 변수
        String loginUsername = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            List<List<String>> users = FileUtil.readAndSplitFile(USER_FILE, "■");
            for (int i = 0; i < users.size(); i++) {
                List<String> user = users.get(i);
                if (user.get(1).equals(userId) && user.get(2).equals(password)) {
                    isValid = true;
                    loginUsername = user.get(3);
                    userIndex = i+1; // 인덱스를 저장
                    break;
                }
            }
        }
        
        
        if (isValid) {
            // 로그인 상태 저장
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOGIN_FILE))) {
            	writer.write(userIndex + "■" + userId + "■" + password + "■" + loginUsername + "■" + isValid);
            }
            System.out.println("✔️로그인 성공: " + loginUsername + "님 반갑습니다.");
            
            User user = new User(
                    userIndex,
                    userId,
                    password,
                    LoginSystem.getUserName(),
                    null,
                    null,
                    0);
            
            userview.memberMenu(user);
            
        } else {
            System.out.println("⚠️아이디 또는 비밀번호가 잘못되었습니다.");
        }
    }

    // 로그아웃
    public static void logout() {
        File file = new File(LOGIN_FILE);

        if (file.exists()) {
            if (file.delete()) {
                System.out.println("✔️로그아웃 성공.");
            } else {
                System.out.println("✖️로그아웃 실패");
            }
        } else {
            System.out.println("⚠️현재 로그인 상태가 아닙니다.");
        }
    }
    
    //login한 userIndex값
    public static String getUserIndex() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(LOGIN_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim(); // 공백 제거
                String[] parts = line.split("■"); // ■로 데이터 분리
                if (parts.length > 0) {
                    return parts[0]; // 1 번째 값(userIndex) 반환
                }
            }
        }
        return null; // 파일이 비어있거나 데이터가 없을 경우 null 반환
    }

    //login한 userPassword값
    public static String getUserPassword() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(LOGIN_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim(); // 공백 제거
                String[] parts = line.split("■"); // ■로 데이터 분리
                if (parts.length > 0) {
                    return parts[2]; // 2 번째 값(userIndex) 반환
                }
            }
        }
        return null; // 파일이 비어있거나 데이터가 없을 경우 null 반환
    }

    //login한 userPassword값
    public static String getUserName() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(LOGIN_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim(); // 공백 제거
                String[] parts = line.split("■"); // ■로 데이터 분리
                if (parts.length > 0) {
                    return parts[3]; // 3 번째 값(userIndex) 반환
                }
            }
        }
        return null; // 파일이 비어있거나 데이터가 없을 경우 null 반환
    }
    
  //login한 유저의 경로구분
    public static String getValid() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(LOGIN_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim(); // 공백 제거
                String[] parts = line.split("■"); // ■로 데이터 분리
                if (parts.length > 0) {
                    return parts[4]; // 3 번째 값(userIndex) 반환
                }
            }
        }
        return null; // 파일이 비어있거나 데이터가 없을 경우 null 반환
    }
    
    


    
    public static void main(String[] args) throws IOException {
        // 테스트
        login("t5mht0p3", "jahu07xapnpz");  // 로그인
        //logout();          // 로그아웃

    }
}



package com.test.user;

import java.io.*;
import java.util.*;

import com.test.util.LoginSystem;

public class UserService {

    private List<User> users = new ArrayList<>();

    //private static final String PATH = ".\\data\\members.txt"; //윈도우
    private static final String PATH = "./data/members.txt"; //맥

    // 사용자 목록 파일에서 읽어오기
    public List<User> readMemberFile() {
        List<User> userList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split("■");
                if (userData.length == 7) {
                    int userIndex = Integer.parseInt(userData[0]);
                    String userId = userData[1];
                    String userPassword = userData[2];
                    String userName = userData[3];
                    String userEmail = userData[4];
                    String userPhone = userData[5];
                    int points = Integer.parseInt(userData[6]);

                    User user = new User(userIndex, userId, userPassword, userName, userEmail, userPhone, points);
                    userList.add(user);
                }
            }
        } catch (IOException e) {
            System.out.println("파일을 읽는 중 오류가 발생했습니다: " + e.getMessage());
        }

        return userList;
    }
    
    // 마지막 userIndex 값을 찾아서 1을 더한 값 반환
    public int getNextUserIndex(List<User> userList) {
        int maxIndex = 0;  // 기본값 설정

        // userList에서 가장 큰 userIndex를 찾음
        for (User user : userList) {
            if (user.getUserIndex() > maxIndex) {
                maxIndex = user.getUserIndex();  // 가장 큰 인덱스를 갱신
            }
        }

        return maxIndex + 1;  // 가장 큰 인덱스에 1을 더하여 새 userIndex 생성
    }



    // 사용자 목록을 파일에 쓰기
    protected void writeMemberFile(List<User> userList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH))) {
            for (User user : userList) {
                String line = String.join("■",
                    String.valueOf(user.getUserIndex()),
                    user.getUserId(),
                    user.getUserPassword(),
                    user.getUserName(),
                    user.getUserEmail(),
                    user.getUserPhone(),
                    String.valueOf(user.getUserPoints())
                );
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("파일 쓰기에 실패했습니다: " + e.getMessage());
        }
    }
}

package com.test.user;

import java.util.List;

public class UserAuthService {

    private static final String PATH = "./data/members.txt"; // 맥
    //private static final String PATH = ".\\data\\members.txt"; //윈도우

    private UserService userService = new UserService();

    // 회원 탈퇴 메서드
    public boolean deleteUser(String userPassword) {
        List<User> userList = userService.readMemberFile(); // 사용자 목록 읽기
        boolean isDeleted = false;

        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            if (user.getUserPassword().equals(userPassword)) {  // 비밀번호 확인
                userList.remove(i); // 해당 사용자 삭제
                isDeleted = true;
                break;
            }
        }

        if (isDeleted) {
            // 업데이트된 사용자 목록을 파일에 저장
            userService.writeMemberFile(userList);
        }
        return isDeleted;
    }
}

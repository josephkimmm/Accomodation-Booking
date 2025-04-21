package com.test.user;

import java.util.List;

public class UserInfoService {

    private UserService userService = new UserService();

    // 사용자 정보 수정
    public boolean updateUserInfo(String userId, String newUserPassword, String newUserName, String newEmail, String newPhoneNum) {
        // 사용자 정보를 읽어옵니다.
        List<User> users = userService.readMemberFile();
        boolean isUpdated = false;

        for (User user : users) {
            if (user.getUserId().equals(userId)) { // userId로만 비교
                // 사용자 정보를 업데이트합니다.
                if (!newUserPassword.isEmpty()) {
                    user.setUserPassword(newUserPassword); // 비밀번호 변경
                }
                user.setUserName(newUserName.isEmpty() ? user.getUserName() : newUserName);
                user.setUserEmail(newEmail.isEmpty() ? user.getUserEmail() : newEmail);
                user.setUserPhone(newPhoneNum.isEmpty() ? user.getUserPhone() : newPhoneNum);
                isUpdated = true;
                break;
            }
        }

        if (isUpdated) {
            // 변경된 사용자 정보를 파일에 저장합니다.
            userService.writeMemberFile(users);
            return true;
        }

        return false;
    }
}

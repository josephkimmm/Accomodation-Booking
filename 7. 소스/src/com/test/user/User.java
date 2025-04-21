package com.test.user;

public class User {


	private int userIndex;
	private String userId;
    private String userPassword;
    private String userName;
    private String userEmail;
    private String userPhone;
    private int userPoints;
	private boolean valid;
    
	public User(int userIndex, String userId, String userPassword, String userName, String userEmail, String userPhone,
			int userPoints) {
		this.userIndex = userIndex;
		this.userId = userId;
		this.userPassword = userPassword;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPhone = userPhone;
		this.userPoints = userPoints;
	}

	public User(int userIndex, String userId, String userPassword, String userName, boolean valid) {
		this.userIndex = userIndex;
		this.userId = userId;
		this.userPassword = userPassword;
		this.userName = userName;
		this.valid = valid;
	}

	public int getUserIndex() {
		return userIndex;
	}

	public void setUserIndex(int userIndex) {
		this.userIndex = userIndex;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public int getUserPoints() {
		return userPoints;
	}

	public void setUserPoints(int userPoints) {
		this.userPoints = userPoints;
	}

	@Override
	public String toString() {
		return "User [userIndex=" + userIndex + ", userId=" + userId + ", userPassword=" + userPassword + ", userName="
				+ userName + ", userEmail=" + userEmail + ", userPhone=" + userPhone + ", userPoints=" + userPoints
				+ "]";
	}

    public String toFileFormat() {
        return userIndex + "■" + userId + "■" + userPassword + "■" + userName + "■" + userEmail + "■" + userPhone + "■" + userPoints;
    }
}
    

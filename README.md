# Accomodation-Booking

📌 저기어때 (JeogiOttae) - Java Console Project
🇰🇷 프로젝트 개요

저기어때는 콘솔 기반의 숙박 예약 및 사용자 관리 시스템으로, 자바를 활용한 토이 프로젝트입니다. 실제 웹 또는 모바일 기반 숙박 플랫폼에서 제공하는 회원가입, 로그인, 예약, 포인트 관리 등 핵심 기능을 구현하여 학습 목적의 클론 프로젝트로 개발되었습니다.

# 📅 프로젝트 진행 기간

> 2025.01.06 ~ 2025.01.10(6일)

# 🛠️ 사용 기술 (Tech Stack)

- **Language:** Java (JDK 11)
- **Data Persistence:** File I/O (텍스트 파일 기반 데이터 저장/수정)
- **Tool:** Eclipse IDE

---

# 👥 프로젝트 인원

- 총 4명

🧑‍💻 담당 기능: 마이페이지 (MyPageService)
제가 맡은 부분은 사용자 마이페이지 기능이며, 핵심 구현 내용은 다음과 같습니다:

회원가입: 사용자 ID 중복 체크, 이메일/전화번호 형식 유효성 검사 포함

포인트 관리: 사용자 포인트 조회 및 충전 기능 구현

회원정보 수정: 사용자 이름, 이메일, 전화번호 변경 기능

회원 탈퇴: 비밀번호 인증을 통한 회원 정보 삭제

⚙️ 문제 해결 과정
ID 중복 검사 및 유효성 체크: 회원가입 시 기존 회원 목록을 파일에서 읽어와 중복 체크를 수행하고, 정규표현식으로 이메일과 전화번호의 형식을 검증했습니다.

데이터 영속성 처리: 사용자 정보는 members.txt 파일에 저장되며, readMemberFile과 writeMemberFile 메소드를 통해 읽기/쓰기 작업을 수행합니다.

유저 인덱스 자동 할당: getNextUserIndex() 메서드를 통해 새로운 사용자의 인덱스를 자동으로 계산하여 고유성 유지

서비스 계층 분리: 사용자 관련 로직을 UserService, MyPageService, UserInfoService 등으로 역할 분리하여 유지보수성과 테스트 용이성 향상

📌 JeogiOttae - Java Console Project (English)
🧾 Overview

JeogiOttae is a Java console-based clone project inspired by accommodation booking services. This project was developed for educational purposes and includes core features such as user registration, login, booking, point management, and account editing.

# 📅 Project period

> 2025.01.06 ~ 2025.01.10(6 days)

# 🛠️ Tech Stack

- **Language:** Java (JDK 11)
- **Data Persistence:** File I/O
- **Tool:** Eclipse IDE

---

# 👥 The number of people

- Total 4 teammates

👨‍💻 My Role: My Page Service
I was responsible for implementing the My Page module, which includes:

User Registration: Validates ID uniqueness, email, and phone formats using regex

Point System: Allows users to view and top up points

Update Profile: Enables name, email, and phone number changes

Account Deletion: Deletes a user account with password authentication

🔍 Problem-Solving Focus
Validation and Duplication Check: Read users from file, validate ID uniqueness and input format

Persistence: Data is read from and written to members.txt, simulating a lightweight database

Auto-assign User Index: The getNextUserIndex() method automatically assigns a unique index

Layered Architecture: Clear separation of concerns through different service classes for modular and scalable code

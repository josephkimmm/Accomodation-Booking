package com.test;

import java.io.IOException;
import java.util.Scanner;

import com.test.admin.AdminView;
import com.test.booking.BookingView;
import com.test.payment.PaymentView;
import com.test.user.UserView;
import com.test.util.LoginSystem;

public class Main {
	
    public static void main(String[] args) throws IOException {
    	
    	 Scanner scanner = new Scanner(System.in);
         UserView userView = new UserView();

         while (true) {
        	 
             // ANSI escape codes
             String whiteBackground = "\033[47m";  // 흰색 배경
             String redText = "\033[31m";          // 빨간 글씨
             String reset = "\033[0m";             // 초기화

             // 메시지
             String message = 
            		"                 🤍🤍                                             \r\n"+
                    "                🤍🤍                                              \r\n"+
                    "               🤍🤍                                                \r\n"+
                    "              =====      /  \\                                    \r\n"+
                    "             _|___|_____/ __ \\_____________                      \r\n"+
                    "            |::::::::::/ |  | \\::::::::::::|                     \r\n"+
                    "            |:::::::::/  ====  \\:::::::::::|                     \r\n"+
                    "            |::::::::/__________\\::::::::::|                     \r\n"+
                    "            |_________|  ______ |__________|                     \r\n"+
                    "             |_________ // || \\  _________|                       \r\n"+
                    "             ||   |   || ====== ||   |   ||                      \r\n"+
                    "             ||---+---|| |    | ||---+---||                      \r\n"+
                    "             ||___|___|| |   o| ||___|___||                      \r\n"+
                    "             |=========| |____| |========||                      \r\n"+
                    "             (^^-^^^^^-|________|-^^^--^^^)                      \r\n"+
                    "             (,,,,,,,,//_________\\,,,,,,,,,)                     \r\n"+
                    "             ;;,,,,,,//___________\\,,,,,,,;;                     \n"+
                    "                                                                 \n"+
                    "                                                                 \n"+
                    "   -#######- +#+ .######-  ##   #####+  -##  ######.##-##        \n"+
                    "      -##    +#+      +#+  ##  ##-  ##- -## .## ##  ##-##        \n"+
                    "      ### +####+      +#+  ##  ##-  ####### .## ##  #####        \n"+
                    "      ####.  +#+      ##.  ##  ##-  ##+ +## .## ##  ##-##        \n"+
                    "    -##. ##. +#+    -##-   ##  ##-  ##- -## .## ##  ##-##        \n"+
                    "   -#+   +#- +#+ -###-     ##   #####-  -##  ######-##-##        \n"+
                    "                                                                 \n"+
                    "                                                                 ";

             // 흰색 배경에 빨간 글씨 출력
             System.out.println(whiteBackground + redText + message + reset);

             System.out.print("\033[47m\033[30m"); 
            System.out.println("=================================================================");
            System.out.println("              🏨저기어때 - 전국 숙소 예약 프로그램🏨      	         ");      			System.out.println("=================================================================");
            
            //System.out.print("\033[47m\033[30m");
			System.out.println("	                     ┏━━━━━━━━━━┓                        ");
			System.out.println("	┏━━━━━━━━━━━━━━━━━━━━┃ 회원메뉴  ┃━━━━━━━━━━━━━━━━━━━┓    ");
			System.out.println("	┃                    ┗━━━━━━━━━━┛                   ┃    ");
			System.out.println("	┃ ┏━━━━━━━━━━━━━━━━━━━━━━┓┏━━━━━━━━━━━━━━━━━━━━━━┓  ┃    ");
			System.out.println("	┃ ┃[1] 🧑사용자 메뉴	 ┃┃[2] 🧑‍💼관리자 메뉴	 ┃  ┃  	 ");
			System.out.println("	┃ ┗━━━━━━━━━━━━━━━━━━━━━━┛┗━━━━━━━━━━━━━━━━━━━━━━┛  ┃    ");
			System.out.println("	┃ ┏━━━━━━━━━━━━━━━━━━━━━━┓                          ┃    ");
			System.out.println("	┃ ┃[3] 🔚종료    	 ┃                   	    ┃    ");
			System.out.println("	┃ ┗━━━━━━━━━━━━━━━━━━━━━━┛                          ┃    ");
			System.out.println("	┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛    ");
			System.out.print("\033[0m");
			System.out.println();
			System.out.print("✔️선택: ");
			
			
            int choice = scanner.nextInt();
            scanner.nextLine(); // 버퍼 비우기
             
             
             switch (choice) {
                 case 1:
                     userView.start(); // UserView 진입
                     break;
                 case 2:
                     AdminView.main(args); // AdminView 진입
                     break;
                 case 3:
                     System.out.println("🔚프로그램을 종료합니다.");
                     System.exit(0);
                     break;
                 default:
                     System.out.println("⚠️잘못된 입력입니다. 다시 시도하세요.");
             }
         }
     }

    }



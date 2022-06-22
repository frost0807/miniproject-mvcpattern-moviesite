package viewer;

import java.util.Scanner;
import util.ScUtil;
import model.UserDTO;
import controller.UserController;

public class UserViewer {
	private Scanner sc;
	private UserDTO logInInfo;
	private UserController userController;
	private MovieViewer movieViewer;
	private TheaterViewer theaterViewer;
	private ReservationViewer reservationViewer;

	public UserViewer(Scanner sc) {
		userController = new UserController();
		this.sc = sc;
	}

	public void setMovieViewer(MovieViewer movieViewer) {
		this.movieViewer = movieViewer;
	}

	public void setTheaterViewer(TheaterViewer theaterViewer) {
		this.theaterViewer = theaterViewer;
	}

	public void setReservationViewer(ReservationViewer reservationViewer) {
		this.reservationViewer = reservationViewer;
	}

	// 초기화면 메소드
	public void showIndex() {
		userInit();

		while (true) {
			System.out.println("영화관리&예매 사이트에 오신걸 환영합니다.");
			int userChoice = ScUtil.nextInt(sc, 1, 3, "1.로그인 2.회원가입 3.종료");

			if (userChoice == 1) {
				login();
				if (logInInfo != null) {
					System.out.println("로그인 되었습니다.");
					showMenu();
				}
			} else if (userChoice == 2) {
				signup();
			} else {
				System.out.println("종료합니다. 이용해주셔서 감사합니다.");
			}
		}
	}

	// guest계정과 어드민 계정3개를 생성하는 메소드
	private void userInit() {
		UserDTO temp = new UserDTO();
		temp.setUsername("guest");
		temp.setPassword("guest");
		temp.setNickname("guest");
		temp.setGrade(1);
		userController.insert(new UserDTO(temp));
		temp.setUsername("guest1");
		temp.setPassword("guest1");
		temp.setNickname("guest1");
		temp.setGrade(2);
		userController.insert(new UserDTO(temp));
		for (int i = 1; i < 4; i++) {
			temp.setUsername("admin" + i);
			temp.setPassword(i + "qwer");
			temp.setNickname("admin" + i);
			temp.setGrade(3);
			userController.insert(new UserDTO(temp));
		}
	}

	// 로그인 후 나오는 메뉴
	private void showMenu() {
		while (logInInfo != null) {
			int userChoice;
			if (logInInfo.getGrade() == 3) {
				userChoice = ScUtil.nextInt(sc, 1, 7, "1.영화목록보기 2.극장목록보기 3.영화예매 4.로그아웃 5.영화정보 등록하기(관) 6.극장정보 등록하기(관) 7.회원등급수정(관)");
			} else {
				userChoice = ScUtil.nextInt(sc, 1, 4, "1.영화목록보기 2.극장목록보기 3.영화예매 4.로그아웃");
			}

			if (userChoice == 1) {
				movieViewer.setLogIn(logInInfo);
				movieViewer.printList();
			} else if (userChoice == 2) {
				theaterViewer.setLogIn(logInInfo);
				theaterViewer.printList();
			} else if (userChoice == 3) {
				reservationViewer.setLogIn(logInInfo);
//				reservationViewer.reservationMenu();
			} else if (userChoice == 5) {
				movieViewer.setLogIn(logInInfo);
				movieViewer.addMovie();
			} else if(userChoice==6) {
				theaterViewer.setLogIn(logInInfo);
				theaterViewer.addTheater();
			} else if(userChoice==7) {
//				userGradeUpdate();
			}
			
			
			
			else {
				logInInfo = null;
			}
		}
	}

	// 로그인하는 메소드
	private void login() {
		System.out.println("(방문자용아이디는 guest 비밀번호는 guest입니다.)");
		String tempUserName = ScUtil.nextLine(sc, "아이디를 입력해주세요");
		String tempPassword = ScUtil.nextLine(sc, "비밀번호를 입력해주세요");
		int tempCount = 1;

		while (userController.auth(tempUserName, tempPassword) == null) {
			if (tempCount < 5) {
				System.out.printf("잘못된 입력입니다. 다시 입력하시겠습니까? Y/N (현재 로그인 시도횟수%d/5)", tempCount++);
				String yOrN = ScUtil.nextLine(sc);

				if (yOrN.equalsIgnoreCase("y")) {
					tempUserName = ScUtil.nextLine(sc, "아이디를 입력해주세요");
					tempPassword = ScUtil.nextLine(sc, "비밀번호를 입력해주세요");
				} else {
					tempPassword = null;
					break;
				}
			} else {
				System.out.println("로그인 시도횟수를 초과하여 종료합니다.");
				tempPassword = null;
				break;
			}
		}
		logInInfo = userController.auth(tempUserName, tempPassword);
	}

	private void signup() {
		System.out.println("회원가입 페이지입니다.");
		String tempUserName = ScUtil.nextLine(sc, "아이디를 입력해주세요");

		while (userController.checkValidId(tempUserName)) {
			String yOrN = ScUtil.nextLine(sc, "존재하는 아이디입니다. 다시 입력하시겠습니까? Y/N");

			if (yOrN.equalsIgnoreCase("y")) {
				tempUserName = ScUtil.nextLine(sc, "중복되지 않는 아이디를 입력해주세요");
			} else {
				break;
			}
		}
		if (!userController.checkValidId(tempUserName)) {
			String tempPassword = ScUtil.nextLine(sc, "사용할 비밀번호를 입력해주세요");
			String tempNickName = ScUtil.nextLine(sc, "사용할 닉네임을 입력해주세요");

			UserDTO temp = new UserDTO();
			temp.setUsername(tempUserName);
			temp.setPassword(tempPassword);
			temp.setNickname(tempNickName);
			temp.setGrade(1);

			userController.insert(temp);
			System.out.println("회원가입이 완료되었습니다.");
		}
	}
	public void userGradeUpdate() {
		int userChoice=ScUtil.nextInt(sc, "1.일반관람객등급 보기 2.전문평론가등급 보기 3.뒤로가기");
		if(userChoice==1) {
			System.out.println("-------------------------------------------------");
			for(int id:userController.getPublicIdList()) {
				printOne(id);
			}
			System.out.println("-------------------------------------------------");
		} else if(userChoice==2) {
			System.out.println("-------------------------------------------------");
			for(int id:userController.getCriticIdList()) {
				printOne(id);
			}
			System.out.println("-------------------------------------------------");
		}
	}
	public void printOne(int id) {
		UserDTO temp=userController.selectOne(id);
		System.out.printf("회원코드:%d 등급:%s 아이디:%s 닉네임:%s\n",
				temp.getId(),temp.getGrade(),temp.getUsername(),temp.getNickname());
	}
	public void printNickNameFromId(int userId) {
		System.out.print(userController.selectOne(userId).getNickname());
	}
}

package viewer;

import java.util.Scanner;

import controller.TheaterController;
import model.MovieDTO;
import model.TheaterDTO;
import model.UserDTO;
import util.ScUtil;

public class TheaterViewer {
	private Scanner sc;
	private UserDTO logInInfo;
	private TheaterController theaterController;
	private ScreenViewer screenViewer;
	private PlayInfoViewer playInfoViewer;
	private ReservationViewer reservationViewer;
	
	public TheaterViewer(Scanner sc) {
		theaterController=new TheaterController();
		this.sc=sc;
	}
	public void setPlayInfoViewer(PlayInfoViewer playInfoViewer) {
		this.playInfoViewer=playInfoViewer;
	}
	public void setScreenViewer(ScreenViewer screenViewer) {
		this.screenViewer=screenViewer;
	}
	public void setReservationViewer(ReservationViewer reservationViewer) {
		this.reservationViewer=reservationViewer;
	}
	public void setLogIn(UserDTO logInInfo) {
		this.logInInfo=logInInfo;
	}
	public void printList() {
		while(true) {
			System.out.println("=================================================");
			System.out.println("극장코드\t지점명\t극장명");
			System.out.println("=================================================");
			
			for(TheaterDTO t:theaterController.selectAll()) {
				System.out.printf("%d\t%s\t%s\n",t.getId(),t.getBranch(),t.getName());
			}
			int theaterChoice=ScUtil.nextInt(sc, "자세히 볼 극장의 코드를 입력하시거나 0을눌러 뒤로가기");
			
			while(theaterChoice!=0&&theaterController.selectOne(theaterChoice)==null) {
				System.out.println("잘못된 입력입니다. 다시 입력해주세요");
				theaterChoice=ScUtil.nextInt(sc, "자세히 볼 극장의 코드를 입력하시거나 0을눌러 뒤로가기");
			}
			if(theaterChoice!=0) {
				printTheater(theaterChoice);
			} else {
				break;
			}
		}
	}
	private void printTheater(int theaterId) {
		TheaterDTO temp=theaterController.selectOne(theaterId);
		System.out.println("=================================================");
		System.out.printf("극장코드:%d\n지점명:%s\n극장연락처:%s\n",temp.getId(),temp.getBranch(),temp.getPhoneNumber());
		System.out.printf("극장명:%s\n",temp.getName());
		System.out.println("-------------------------------------------------");
		screenViewer.printList();
		System.out.println("=================================================");
		
		int adminChoice;
		if(logInInfo.getGrade()==3) {
			adminChoice=ScUtil.nextInt(sc, 1, 9,
					"1.이 극장정보 삭제하기(관) 2.이 극장정보 수정하기(관)\n"
					+ "3.새로운 상영관 등록하기(관) 4.기존 상영관정보 삭제하기(관) 5.기존 상영관정보 수정하기(관)\n"
					+ "6.새로운 상영정보 등록하기(관) 7.기존 상영정보 삭제하기(관) 8.기존 상영정보 수정하기(관) 9.뒤로가기\n");
			if(adminChoice==1) {
				deleteTheater(theaterId);
			} else if(adminChoice==2) {
				updateTheater(theaterId);
			} else if(adminChoice==3) {
				screenViewer.setLogIn(logInInfo);
				screenViewer.addScreen(theaterId);
			} else if(adminChoice==4) {
				screenViewer.setLogIn(logInInfo);
				screenViewer.deleteScreen(theaterId);
			} else if(adminChoice==5) {
				screenViewer.setLogIn(logInInfo);
				screenViewer.updateScreen(theaterId);
			} else if(adminChoice==6) {
				playInfoViewer.setLogIn(logInInfo);
				playInfoViewer.addPlayInfo(theaterId);
			} else if(adminChoice==7) {
				playInfoViewer.setLogIn(logInInfo);
				playInfoViewer.deletePlayInfo(theaterId);
			} else if(adminChoice==8) {
				playInfoViewer.setLogIn(logInInfo);
				playInfoViewer.updatePlayInfo(theaterId);
			}
		} else {
			int userChoice=ScUtil.nextInt(sc, 0, 1, "이 극장에서 예매하려면 1을 입력하거나 0을눌러 뒤로가기");
			if(userChoice==1) {
//				ReservationViewer.pickTheaterFirstWithId(theaterId);
			}
		}
	}
	public void addTheater() {
		TheaterDTO temp=new TheaterDTO();
		temp.setName(ScUtil.nextLine(sc, "등록할 극장명을 입력해주세요"));
		temp.setBranch(ScUtil.nextLine(sc, "지점명을 입력해주세요"));
		temp.setPhoneNumber(ScUtil.nextLine(sc, "극장연락처를 입력해주세요"));
		
		theaterController.insert(temp);
		System.out.println("등록이 완료되었습니다.");
	}
	private void deleteTheater(int theaterId) {
		String yOrN=ScUtil.nextLine(sc, "정말로 삭제하시겠습니까? Y/N");
		if(yOrN.equalsIgnoreCase("y")) {
			theaterController.delete(theaterId);
			System.out.println("극장정보가 삭제되었습니다.");
		}
	}
	private void updateTheater(int theaterId) {
		TheaterDTO temp=new TheaterDTO(theaterId);
		
		temp.setName(ScUtil.nextLine(sc, "새로운 극장명을 입력해주세요"));
		temp.setBranch(ScUtil.nextLine(sc, "새로운 지점명을 입력해주세요"));
		temp.setPhoneNumber(ScUtil.nextLine(sc, "새로운 극장연락처를 입력해주세요"));
		
		theaterController.update(temp);
		System.out.println("극장정보 수정이 완료되었습니다.");
	}
	public void printTheaterListWithMovieId(int movieId) {
		System.out.println("-------------------------------------------------");
		for(TheaterDTO t:theaterController.selectAll()) {
			System.out.printf("극장코드:%d 지점명:%s 극장명:%s\n",t.getId(),t.getBranch(),t.getName());
		}
		System.out.println("-------------------------------------------------");
	}
	public void checkValidTheaterWithTwoId(int movieId,int theaterChoice) {
		TheaterDTO t=theaterController.selectOne(theaterChoice);
		
		while(theaterChoice!=0&&t==null) {
			System.out.println("잘못된 입력입니다.");
			theaterChoice=ScUtil.nextInt(sc, "예매할 극장코드를 입력하거나 0을입력해 뒤로가기");
		}
		if(theaterChoice!=0) {
			screenViewer.reservationPickPlayInfo(movieId, theaterChoice);
		}
	}
	public void printTheaterIdToName(int theaterId) {
		System.out.print(theaterController.selectOne(theaterId).getName());
	}
}

package viewer;

import model.ScreenDTO;
import model.TheaterDTO;
import model.UserDTO;
import util.ScUtil;

import java.util.Scanner;
import controller.ScreenController;

public class ScreenViewer {
	private Scanner sc;
	private UserDTO logInInfo;
	private ScreenController screenController;
	private MovieViewer movieViewer;
	private PlayInfoViewer playInfoViewer;
	
	public ScreenViewer(Scanner sc) {
		screenController=new ScreenController();
		this.sc=sc;
	}
	public void setMovieViewer(MovieViewer movieViewer) {
		this.movieViewer=movieViewer;
	}
	public void setPlayInfoViewer(PlayInfoViewer playInfoViewer) {
		this.playInfoViewer=playInfoViewer;
	}
	public void setLogIn(UserDTO logInInfo) {
		this.logInInfo=logInInfo;
	}
	public void printList() {
		System.out.println("[상영관정보]");
		for(ScreenDTO s:screenController.selectAll()) {
			//상영관코드 상영관타입 상영영화 시작시간 남은좌석수
			System.out.printf("[상영관코드:%d 상영관타입:%s]\n",
					s.getId(),screenController.screenTypeString(s.getScreenType()));
			playInfoViewer.printList(s.getId());
		}
	}
	public void addScreen(int theaterId) {
		ScreenDTO temp=new ScreenDTO();
		temp.setTheaterId(theaterId);
		temp.setScreenType(ScUtil.nextInt(sc, "상영관유형을 입력해주세요 1.2D 2.3D 3.4D 4.IMAX"));
		temp.setSeatTotal(ScUtil.nextInt(sc, "총 좌석수를 입력해주세요"));
		
		screenController.insert(temp);
		System.out.println("등록이 완료되었습니다.");
	}
	public void deleteScreen(int theaterId) {
		int userChoice=ScUtil.nextInt(sc, "삭제하려는 상영관의 코드를 입력하시거나 0을눌러 뒤로가기");

		while(userChoice!=0&&screenController.selectOne(userChoice)==null) {
			userChoice=ScUtil.nextInt(sc, "잘못된 입력입니다. 다시 입력해주세요");
		}
		if(userChoice!=0) {
			String yOrN=ScUtil.nextLine(sc, "정말로 삭제하시겠습니까? Y/N");
			if(yOrN.equalsIgnoreCase("y")) {
				screenController.delete(userChoice);
				System.out.println("삭제가 완료되었습니다.");
			}
		}
	}
	public void updateScreen(int theaterId) {
		int screenChoice=ScUtil.nextInt(sc, "수정하려는 상영관의 코드를 입력하시거나 0을눌러 뒤로가기");
		ScreenDTO temp=screenController.selectOne(screenChoice);
		
		while(screenChoice!=0&&(temp==null||temp.getTheaterId()!=theaterId)) {
			screenChoice=ScUtil.nextInt(sc, "잘못된 입력입니다. 다시 입력해주세요");
			temp=screenController.selectOne(screenChoice);
		}
		if(screenChoice!=0) {
			ScreenDTO s=new ScreenDTO(screenChoice);
			s.setScreenType(ScUtil.nextInt(sc, "상영관의 새로운 유형을 입력해주세요 1.2D 2.3D 3.4D 4.IMAX"));
			s.setSeatTotal(ScUtil.nextInt(sc, "상영관의 새로운 총 좌석수를 입력해주세요"));
			
			screenController.update(s);
			System.out.println("수정이 완료되었습니다.");
		}
	}
}

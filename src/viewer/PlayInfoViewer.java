package viewer;

import java.util.Scanner;
import java.util.ArrayList;

import model.UserDTO;
import util.ScUtil;
import model.PlayInfoDTO;
import model.ReservationDTO;
import controller.PlayInfoController;

public class PlayInfoViewer {
	private Scanner sc;
	private UserDTO logInInfo;
	private MovieViewer movieViewer;
	private ReservationViewer reservationViewer;
	private PlayInfoController playInfoController;
	private TheaterViewer theaterViewer;

	public PlayInfoViewer(Scanner sc) {
		playInfoController=new PlayInfoController();
		this.sc=sc;
	}
	public void setTheaterViewer(TheaterViewer theaterViewer) {
		this.theaterViewer=theaterViewer;
	}
	public void setMovieViewer(MovieViewer movieViewer) {
		this.movieViewer=movieViewer;
	}
	public void setReservationViewer(ReservationViewer reservationViewer) {
		this.reservationViewer=reservationViewer;
	}
	public void setLogIn(UserDTO logInInfo) {
		this.logInInfo=logInInfo;
	}
	public void printList(int screenId) {
		//상영영화 시작시간 잔여좌석
		for(PlayInfoDTO p:playInfoController.selectAllByScreenId(screenId)) {
			System.out.printf("상영정보코드:%d 상영영화:",p.getId());
			movieViewer.printMovieIdToTitle(p.getMovieId());
			System.out.printf(" 시작시간:%s 잔여좌석:%d\n",p.getstartTime(),p.getSeatLeft());
		}
	}
	public void addPlayInfo(int theaterId) {
		PlayInfoDTO temp=new PlayInfoDTO();
		temp.setMovieId(ScUtil.nextInt(sc, "새로 등록할 상영정보의 영화코드를 입력해주세요"));
		temp.setScreenId(ScUtil.nextInt(sc, "새로 등록할 상영정보의 상영관코드를 입력해주세요"));
		temp.setSeatLeft(ScUtil.nextInt(sc, "새로 등록할 상영정보의 총 좌석수를 입력해주세요"));
		temp.setstartTime(ScUtil.nextLine(sc, "새로 등록한 상영정보의 시작시간을 입력해주세요 ex)6월22일7시26분시작이면 0622/1926"));
		temp.setTheaterId(theaterId);
		
		playInfoController.insert(temp);
		System.out.println("등록이 완료되었습니다.");
	}
	public void deletePlayInfo(int theaterId) {
		//screenId상영관의 상영정보들을 출력하고 screenId에 담기
		int screenId=printListByScreenId(theaterId);
		int deleteChoice=ScUtil.nextInt(sc, "삭제할 상영정보의 코드를 입력해주시거나 0을눌러 뒤로가기");
		PlayInfoDTO temp=playInfoController.selectOne(deleteChoice);

		while(deleteChoice!=0&&(temp==null||temp.getScreenId()!=screenId||temp.getTheaterId()!=theaterId)) {
			System.out.println(temp.getScreenId());
			System.out.println(screenId);
			System.out.println(temp.getTheaterId());
			System.out.println(theaterId);
			System.out.println("잘못된 입력입니다.");
			deleteChoice=ScUtil.nextInt(sc, "삭제할 상영정보의 코드를 입력해주시거나 0을눌러 뒤로가기");
		}
		if(deleteChoice!=0) {
			String yOrN=ScUtil.nextLine(sc, "정말로 삭제하시겠습니까? Y/N");
			if(yOrN.equalsIgnoreCase("y")) {
				playInfoController.delete(temp.getId());
				System.out.println("삭제가 완료되었습니다.");
			}
		}
	}
	public void updatePlayInfo(int theaterId) {
		//screenId상영관의 상영정보들을 출력하고 screenId에 담기
		int screenId=printListByScreenId(theaterId);
		int updateChoice=ScUtil.nextInt(sc, "수정할 상영정보의 코드를 입력해주시거나 0을눌러 뒤로가기");
		PlayInfoDTO temp=playInfoController.selectOne(updateChoice);

		while(updateChoice!=0&&(temp==null||temp.getScreenId()!=screenId||temp.getTheaterId()!=theaterId)) {
			System.out.println("잘못된 입력입니다.");
			updateChoice=ScUtil.nextInt(sc, "수정할 상영정보의 코드를 입력해주시거나 0을눌러 뒤로가기");
		}
		if(updateChoice!=0) {
			movieViewer.printMovieListBrief();
			temp.setMovieId(ScUtil.nextInt(sc, "상영정보의 새로운 영화코드를 입력해주세요"));
			temp.setstartTime(ScUtil.nextLine(sc, "상영정보의 새로운 시작시간을 입력해주세요"));
			temp.setSeatLeft(ScUtil.nextInt(sc, "상영정보의 새로운 최대좌석수를 입력해주세요"));
			
			playInfoController.update(temp);
			System.out.println("상영정보 수정이 완료되었습니다.");
		}
		
	}
	public int printListByScreenId(int theaterId) {
		int userChoice=ScUtil.nextInt(sc, "조회할 상영정보의 상영관의 코드를 입력해주시거나 0을눌러 뒤로가기");
		ArrayList<PlayInfoDTO> tempList=new ArrayList<>();
		//극장코드와 상영관코드를 만족하는 상영정보 tempList에 담기
		for(PlayInfoDTO p:playInfoController.selectAllByScreenId(userChoice)) {
			if(p.getTheaterId()==theaterId) {
				tempList.add(p);
			}
		}
		//
		while(userChoice!=0&&tempList.size()==0) {
			System.out.println("잘못된 입력입니다.");
			userChoice=ScUtil.nextInt(sc, "조회할 상영정보의 상영관의 코드를 입력해주시거나 0을눌러 뒤로가기");
			for(PlayInfoDTO p:playInfoController.selectAllByScreenId(userChoice)) {
				if(p.getTheaterId()==theaterId) {
					tempList.add(p);
				}
			}
		}
		//극장코드+상영관 즉, 상영관의 상영정보들을 출력
		if(userChoice!=0) {
			System.out.printf("[상영관코드:%d]\n",userChoice);
			for(PlayInfoDTO p:tempList) {
				System.out.printf("상영정보코드:%d 극장코드:%d 영화제목:",p.getId(),p.getTheaterId());
				movieViewer.printMovieIdToTitle(p.getMovieId());
				System.out.printf(" 시작시간:%s 남은좌석수:%d\n",p.getstartTime(),p.getSeatLeft());
			}
		} else {}
		//userChoice는 해당극장의 상영관 중 하나이다. userChoice가 0이면 뒤로가기한것
		return userChoice;
	}
	public void playInfoViewerWithTwoId(int movieId,int screenId) {
		for(PlayInfoDTO p:playInfoController.selectAllByScreenId(screenId)) {
			if(p.getMovieId()==movieId) {
				printList(screenId);
			}
		}
	}
	public void pickPlayInfo(int movieId,int theaterId) {
		int playInfoChoice=ScUtil.nextInt(sc, "예매할 상영정보코드를 입력하거나 0을 입력해 뒤로가기");
		PlayInfoDTO p=playInfoController.selectOne(playInfoChoice);
		
		while(playInfoChoice!=0&&(p==null||p.getMovieId()!=movieId||p.getTheaterId()!=theaterId)) {
			System.out.println("잘못된 입력입니다.");
			playInfoChoice=ScUtil.nextInt(sc, "예매할 상영정보코드를 입력하거나 0을 입력해 뒤로가기");
		}
		if(playInfoChoice!=0) {
			if(p.getSeatLeft()==0) {
				System.out.println("현재 잔여좌석이 없습니다.");
			} else {
				reservationViewer.setLogIn(logInInfo);
				reservationViewer.checkValidAndGoOn(p.getId());
			}
		}
	}
	public void minusSeatLeft(int playInfoId) {
		PlayInfoDTO p=playInfoController.selectOne(playInfoId);
		int seatLeft=p.getSeatLeft();
		
		p.setSeatLeft(seatLeft-1);
		
		playInfoController.update(p);
	}
	public void plusSeatLeft(int playInfoId) {
		PlayInfoDTO p=playInfoController.selectOne(playInfoId);
		int seatLeft=p.getSeatLeft();
		
		p.setSeatLeft(seatLeft+1);
		
		playInfoController.update(p);
	}
	public void printReservedOne(int reservationId,int playInfoId) {
		for(PlayInfoDTO p:playInfoController.selectAll()) {
			if(p.getId()==playInfoId) {
				System.out.printf("예매코드:%d 영화:",reservationId);
				movieViewer.printMovieIdToTitle(p.getMovieId());
				System.out.printf(" 극장:");
				theaterViewer.printTheaterIdToName(p.getTheaterId());
				System.out.printf(" 상영관코드:%d\n",p.getScreenId());
			}
		}
	}
}

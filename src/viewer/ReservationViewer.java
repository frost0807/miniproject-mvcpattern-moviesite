package viewer;

import java.util.Scanner;
import java.util.ArrayList;
import model.UserDTO;
import util.ScUtil;
import model.ReservationDTO;
import controller.ReservationController;

public class ReservationViewer {
	private Scanner sc;
	private UserDTO logInInfo;
	private MovieViewer movieViewer;
	private TheaterViewer theaterViewer;
	private PlayInfoViewer playInfoViewer;
	private ReservationController reservationController;

	public ReservationViewer(Scanner sc) {
		reservationController = new ReservationController();
		this.sc = sc;
	}

	public void setMovieViewer(MovieViewer movieViewer) {
		this.movieViewer = movieViewer;
	}

	public void setTheaterViewer(TheaterViewer theaterViewer) {
		this.theaterViewer = theaterViewer;
	}

	public void setPlayInfoViewer(PlayInfoViewer playInfoViewer) {
		this.playInfoViewer = playInfoViewer;
	}

	public void setLogIn(UserDTO logInInfo) {
		this.logInInfo = logInInfo;
	}

	public void reservationMenu() {
		int userChoice = ScUtil.nextInt(sc, 1, 3, "예매메뉴입니다. 1.영화 먼저 선택 (2.극장 먼저 선택) 3.뒤로가기");
		if (userChoice == 1) {
			pickMovieFirst();
		} else if (userChoice == 2) {
			pickTheaterFirst();
		}
	}

	public void pickMovieFirst() {
		movieViewer.printMovieListDetail();
		int movieChoice = ScUtil.nextInt(sc, "예매하고싶은 영화코드를 입력하거나 0을입력해 뒤로가기");
		movieViewer.checkValidMovie(movieChoice);
		
	}

	public void pickTheaterFirst() {

	}

	public void pickTheaterWithMovieId(int movieId) {
		theaterViewer.printTheaterListWithMovieId(movieId);
		int theaterChoice = ScUtil.nextInt(sc, "예매할 극장코드를 입력하거나 0을입력해 뒤로가기");
		theaterViewer.checkValidTheaterWithTwoId(movieId, theaterChoice);
	}

	public void pickMovieFirstWithTheaterId(int theaterId) {

	}

	public void checkValidAndGoOn(int playInfoId) {
		boolean reserved = false;
		ArrayList<ReservationDTO> temp = reservationController.selectAll();
		for (ReservationDTO r : temp) {
			if (r.getPlayInfoId() == playInfoId) {
				reserved = true;
			}
		}
		if (reserved == false) {
			ReservationDTO r = new ReservationDTO();
			r.setPlayInfoId(playInfoId);
			r.setUserId(logInInfo.getId());

			playInfoViewer.minusSeatLeft(playInfoId);

			reservationController.insert(r);

			System.out.println("예매가 완료되었습니다.");
		} else {
			System.out.println("이미 예매하셨습니다.");
		}
	}

	public void cancelTicket() {
		System.out.println("-------------------------------------------------");
		printList();
		System.out.println("-------------------------------------------------");
		int cancelChoice = ScUtil.nextInt(sc, "취소할 예매코드를 입력해주시거나 0을눌러 뒤로가기");
		ReservationDTO temp = reservationController.selectOne(cancelChoice);
		while (cancelChoice != 0 && (temp == null || temp.getUserId() != logInInfo.getId())) {
			System.out.println("잘못된 입력입니다.");
			cancelChoice = ScUtil.nextInt(sc, "취소할 예매코드를 입력해주시거나 0을눌러 뒤로가기");
		}
		if (cancelChoice != 0) {
			String yOrN = ScUtil.nextLine(sc, "정말로 취소하시겠습니까? Y/N");
			if (yOrN.equalsIgnoreCase("y")) {
				reservationController.delete(cancelChoice);
				playInfoViewer.plusSeatLeft(temp.getPlayInfoId());
				System.out.println("취소가 완료되었습니다.");
			}
		}
	}

	public void printList() {
		ArrayList<ReservationDTO> tempList = reservationController.selectAll();

		if (tempList.size() != 0) {
			for (ReservationDTO r : tempList) {
				if (r.getUserId() == logInInfo.getId()) {
					playInfoViewer.setLogIn(logInInfo);
					playInfoViewer.printReservedOne(r.getId(), r.getPlayInfoId());
				}
			}
		} else {
			System.out.println("예매내역이 없습니다.");
		}
	}
}

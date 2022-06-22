package viewer;

import java.util.Scanner;
import controller.MovieController;
import model.UserDTO;
import util.ScUtil;
import model.MovieDTO;

public class MovieViewer {
	private Scanner sc;
	private UserDTO logInInfo;
	private MovieController movieController;
	private ReviewViewer reviewViewer;
	private ReservationViewer reservationViewer;
	
	public MovieViewer(Scanner sc) {
		movieController=new MovieController();
		this.sc=sc;
	}
	public void setReviewViewer(ReviewViewer reviewViewer) {
		this.reviewViewer=reviewViewer;
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
			System.out.println("영화코드\t평점\t영화제목");
			System.out.println("=================================================");
			
			for(MovieDTO m:movieController.selectAll()) {
				System.out.printf("%d\t",m.getId());
				reviewViewer.printAverageScoreOfAll(m.getId());
				System.out.printf("\t%s\n",m.getTitle());
			}
			int movieChoice=ScUtil.nextInt(sc, "열람할 영화의 코드를 입력하시거나 0을눌러 뒤로가기");
			
			while(movieChoice!=0&&movieController.selectOne(movieChoice)==null) {
				System.out.println("잘못된 입력입니다. 다시 입력해주세요");
				movieChoice=ScUtil.nextInt(sc, "열람할 영화의 코드를 입력하시거나 0을눌러 뒤로가기");
			}
			if(movieChoice!=0) {
				printMovie(movieChoice);
			} else {
				break;
			}
		}
	}
	public void printMovie(int movieId) {
		MovieDTO temp=movieController.selectOne(movieId);
		System.out.println("=================================================");
		System.out.printf("영화코드:%d\n영화제목:%s\n",temp.getId(),temp.getTitle());
		System.out.printf("영화등급:%s\n",filmRateString(temp.getFilmrate()));
		System.out.printf("줄거리:%s\n",temp.getSynopsis());
		System.out.println("-------------------------------------------------");
		reviewViewer.setLogIn(logInInfo);
		System.out.print("전체 평점평균:");
		reviewViewer.printAverageScoreOfAll(movieId);
		System.out.print("\n일반관람객 평점평균:");
		reviewViewer.printAverageScoreOfPublic(movieId);
		System.out.print("\n전문평론가 평점평균:");
		reviewViewer.printAverageScoreOfCritic(movieId);
		System.out.println();
		System.out.println();
		reviewViewer.printReview(movieId);
		System.out.println("=================================================");
		
		int userChoice;
		if(logInInfo.getGrade()==1) {
			userChoice=ScUtil.nextInt(sc,1,5,"1.평점 등록하기 2.이 영화 평점 삭제하기 3. 이 영화 평점 수정하기 4.이 영화 예매하기 5.뒤로가기");
		} else if(logInInfo.getGrade()==2) {
			userChoice=ScUtil.nextInt(sc,1,5,"1.평점&평론 등록하기 2.이 영화 평점&평론 삭제하기 3. 이 영화 평점&평론 수정하기 4.이 영화 예매하기 5.뒤로가기");
		} else {
			userChoice=ScUtil.nextInt(sc, 1, 3, "1.이 영화정보 삭제하기(관) 2.이 영화정보 수정하기(관) 3.뒤로가기");
		}
		
		if(userChoice==1) {
			if(logInInfo.getGrade()==3) {
				deleteMovie(movieId);
			} else {
				reviewViewer.addReview(movieId);
			}
		} else if(userChoice==2) {
			if(logInInfo.getGrade()==3) {
				updateMovie(movieId);
			} else {
				reviewViewer.deleteReview(movieId);
			}
		} else if(userChoice==3) {
			if(logInInfo.getGrade()!=3) {
				reviewViewer.updateReview(movieId);
			}
		} else if(userChoice==4) {
			if(logInInfo.getGrade()!=3) {
//				reservationViewer.pickMovieFirst(movieChoice);
			}
		}
	}
	public void addMovie() {
		MovieDTO temp=new MovieDTO();
		temp.setTitle(ScUtil.nextLine(sc, "등록할 영화제목을 입력해주세요"));
		temp.setFilmrate(ScUtil.nextInt(sc, 1, 4, "영화의 등급을 입력해주세요 1.전체 2.12세 3.15세 4.청불"));
		temp.setSynopsis(ScUtil.nextLine(sc, "영화의 줄거리를 입력해주세요"));
		
		movieController.insert(temp);
		System.out.println("새로운 영화정보 등록이 완료되었습니다.");
	}
	public void deleteMovie(int movieId) {
		String yOrN=ScUtil.nextLine(sc, "정말로 삭제하시겠습니까? Y/N");
		if(yOrN.equalsIgnoreCase("y")) {
			movieController.delete(movieId);
			System.out.println("영화정보가 삭제되었습니다.");
		}
	}
	public void updateMovie(int movieId) {
		MovieDTO temp=new MovieDTO(movieId);
		temp.setTitle(ScUtil.nextLine(sc, "변경될 영화제목을 입력해주세요"));
		temp.setFilmrate(ScUtil.nextInt(sc,1,2, "변경될 영화등급을 입력해주세요 1.전체 2.12세 3.15세 4.청불"));
		temp.setSynopsis(ScUtil.nextLine(sc, "변경될 줄거리를 입력해주세요"));		
		
		movieController.update(temp);
		System.out.println("영화정보 수정이 완료되었습니다.");
	}
	public String filmRateString(int filmRate) {
		if(filmRate==1) {
			return "전체관람가";
		} else if(filmRate==2) {
			return "12세이상관람가";
		} else if(filmRate==3) {
			return "15세이상관람가";
		} else {
			return "청소년관람불가";
		}
	}
	public void printMovieIdToTitle(int movieId) {
		System.out.print(movieController.selectOne(movieId).getTitle());
	}
	public void printMovieListBrief() {
		System.out.println("현재 상영중인 영화의 목록을 출력합니다.");
		for(MovieDTO m:movieController.selectAll()) {
			System.out.printf("영화코드:%d 영화제목:%s\n",m.getId(),m.getTitle());
		}
	}
}

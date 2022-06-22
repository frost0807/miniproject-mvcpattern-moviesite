package main;

import java.util.Scanner;
import viewer.UserViewer;
import viewer.MovieViewer;
import viewer.ReviewViewer;
import viewer.ScreenViewer;
import viewer.TheaterViewer;
import viewer.PlayInfoViewer;
import viewer.ReservationViewer;

public class SiteMain {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		MovieViewer movieViewer=new MovieViewer(sc);
		ReviewViewer reviewViewer=new ReviewViewer(sc);
		ScreenViewer screenViewer=new ScreenViewer(sc);
		TheaterViewer theaterViewer=new TheaterViewer(sc);
		PlayInfoViewer playInfoViewer=new PlayInfoViewer(sc);
		ReservationViewer reservationViewer=new ReservationViewer(sc);
		UserViewer userViewer=new UserViewer(sc);
		
		userViewer.setMovieViewer(movieViewer);
		userViewer.setTheaterViewer(theaterViewer);
		userViewer.setReservationViewer(reservationViewer);
		
		movieViewer.setReviewViewer(reviewViewer);
		movieViewer.setReservationViewer(reservationViewer);
		
		reviewViewer.setUserViewer(userViewer);
		
		theaterViewer.setScreenViewer(screenViewer);
		theaterViewer.setPlayInfoViewer(playInfoViewer);
		theaterViewer.setReservationViewer(reservationViewer);
		
		screenViewer.setMovieViewer(movieViewer);
		screenViewer.setPlayInfoViewer(playInfoViewer);
		
		playInfoViewer.setMovieViewer(movieViewer);
		
		userViewer.showIndex();
	}

}

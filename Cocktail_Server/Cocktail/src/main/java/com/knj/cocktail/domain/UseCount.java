package com.knj.cocktail.domain;

public class UseCount {

	
	public UseCount(int cinema, int exhibition, int library) {
		
		this.cinema = cinema;
		this.exhibition = exhibition;
		this.library = library;
	}
	private int cinema;
	private int exhibition;
	private int library;
	private int cinemaCompute;
	private int exhibitionCompute;
	private int libraryCompute;
	
	public UseCount() {
	}
	public int getCinema() {
		return cinema;
	}
	public void setCinema(int cinema) {
		this.cinema = cinema;
	}
	public int getExhibition() {
		return exhibition;
	}
	public void setExhibition(int exhibition) {
		this.exhibition = exhibition;
	}
	public int getLibrary() {
		return library;
	}
	public void setLibrary(int library) {
		this.library = library;
	}
	
	public int getCinemaCompute() {
		return cinemaCompute;
	}
	public void setCinemaCompute(int cinemaCompute) {
		this.cinemaCompute = cinemaCompute;
	}
	public int getExhibitionCompute() {
		return exhibitionCompute;
	}
	public void setExhibitionCompute(int exhibitionCompute) {
		this.exhibitionCompute = exhibitionCompute;
	}
	public int getLibraryCompute() {
		return libraryCompute;
	}
	public void setLibraryCompute(int libraryCompute) {
		this.libraryCompute = libraryCompute;
	}
	
}

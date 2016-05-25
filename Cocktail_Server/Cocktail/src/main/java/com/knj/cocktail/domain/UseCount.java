package com.knj.cocktail.domain;

public class UseCount {

	
	public UseCount(double cinema, double exhibition, double library) {
		
		this.cinema = cinema;
		this.exhibition = exhibition;
		this.library = library;
	}
	private double cinema;
	private double exhibition;
	private double library;
	private int cinemaCompute;
	private int exhibitionCompute;
	private int libraryCompute;
	
	public UseCount() {
	}
	public double getCinema() {
		return cinema;
	}
	public void setCinema(double cinema) {
		this.cinema = cinema;
	}
	public double getExhibition() {
		return exhibition;
	}
	public void setExhibition(double exhibition) {
		this.exhibition = exhibition;
	}
	public double getLibrary() {
		return library;
	}
	public void setLibrary(double library) {
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

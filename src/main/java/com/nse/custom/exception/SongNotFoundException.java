package com.nse.custom.exception;

/**
 *@Author: sanjeevkumar<br>
 *@Time: 12:48:53 am<br>
 *@Date: 12-Sep-2024 <br>
 */
public class SongNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SongNotFoundException() {
		super();
		System.out.println("Song not found exception");
	}
	

}

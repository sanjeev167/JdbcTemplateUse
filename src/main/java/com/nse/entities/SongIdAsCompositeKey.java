package com.nse.entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

/**
 * @Author: sanjeevkumar<br>
 * @Time: 11:13:57 pm<br>
 * @Date: 09-Sep-2024 <br>
 */
@Embeddable
public class SongIdAsCompositeKey implements Serializable {
	private static final long serialVersionUID = 1L;
    
	private String name;
	private String album;
	private String artist;

	public SongIdAsCompositeKey(String name, String album, String artist) {
        this.name = name;
        this.album = album;
        this.artist = artist;
    }

	public SongIdAsCompositeKey() {
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

}

package com.nse.repo;



import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;


import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.nse.custom.exception.SongNotFoundException;
import com.nse.entities.SongEntityWithEmbeddedCompositeKey;
import com.nse.entities.SongIdAsCompositeKey;

/**
 * @author sanjeevkumar
 * 12-Sep-2024
 * 11:50:26 am 
 * Objective: 
 */
@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@AutoConfigureTestDatabase
class SongsRepositoryWithEmbeddableTest {

	@Autowired SongsRepositoryWithEmbeddable repository;
    @Autowired TestEntityManager testEntityManager;

    @Test
    public void repoCorrectlySavesGivenSong() {  	
    	
        SongIdAsCompositeKey songIdAsCompositeKey = new SongIdAsCompositeKey("test_name", "test_album", "test_artist");
        
        SongEntityWithEmbeddedCompositeKey songEntity = new SongEntityWithEmbeddedCompositeKey(songIdAsCompositeKey,"Hindi",
        		                                                                                                    "Five_Star" );        
        repository.save(songEntity);
       
        //After saving fetch the record using composite key
        SongEntityWithEmbeddedCompositeKey result = testEntityManager.find(SongEntityWithEmbeddedCompositeKey.class, songIdAsCompositeKey);
       //Now, test whether it is returning correct result or not
        assertTrue(result.getSongIdAsCompositeKey().getAlbum().equals(songEntity.getSongIdAsCompositeKey().getAlbum()));  
        
        System.out.println("Sanjeev "+result.getSongIdAsCompositeKey().getAlbum());
        assertTrue(result.getSongIdAsCompositeKey().getName().equals(songEntity.getSongIdAsCompositeKey().getName()));
        assertTrue(result.getSongIdAsCompositeKey().getArtist().equals(songEntity.getSongIdAsCompositeKey().getArtist()));
    }
    
    @Test
    public void checkRepoCorrectlyFindsSongByCompositeId() throws SongNotFoundException {
        List<SongEntityWithEmbeddedCompositeKey> songs = insertFourSongsInDataBase();
        int testSongIndex = 3;

        SongIdAsCompositeKey idToRetrieve = songs.get(testSongIndex).getSongIdAsCompositeKey();

        SongEntityWithEmbeddedCompositeKey result = repository.findById(idToRetrieve).orElseThrow(SongNotFoundException::new);

        assertTrue(result.getSongIdAsCompositeKey().getAlbum().equals(songs.get(testSongIndex).getSongIdAsCompositeKey().getAlbum()));
        assertTrue(result.getSongIdAsCompositeKey().getName().equals(songs.get(testSongIndex).getSongIdAsCompositeKey().getName()));
        assertTrue(result.getSongIdAsCompositeKey().getArtist().equals(songs.get(testSongIndex).getSongIdAsCompositeKey().getArtist()));

        assertTrue(result.getGenre() == songs.get(testSongIndex).getGenre());
        assertTrue(result.getRating() == songs.get(testSongIndex).getRating());
        
    }

	private List<SongEntityWithEmbeddedCompositeKey> insertFourSongsInDataBase() {
		SongIdAsCompositeKey songId1 = new SongIdAsCompositeKey("test_name1", "test_album1", "test_artist1");
		SongEntityWithEmbeddedCompositeKey song1 = new SongEntityWithEmbeddedCompositeKey(songId1,"Hindi","Five_Star");

		SongIdAsCompositeKey songId2 = new SongIdAsCompositeKey("test_name2", "test_album2", "test_artist2");
        SongEntityWithEmbeddedCompositeKey song2 = new SongEntityWithEmbeddedCompositeKey(songId2,"English","Four_Star");

        SongIdAsCompositeKey songId3 = new SongIdAsCompositeKey("test_name3", "test_album3", "test_artist3");
        SongEntityWithEmbeddedCompositeKey song3 = new SongEntityWithEmbeddedCompositeKey(songId3,"Punjabi","Three_Star");

        SongIdAsCompositeKey songId4 = new SongIdAsCompositeKey("test_name4", "test_album4", "test_artist4");
        SongEntityWithEmbeddedCompositeKey song4 = new SongEntityWithEmbeddedCompositeKey(songId4,"Marathi","Two_Star");

        List<SongEntityWithEmbeddedCompositeKey> songs = new ArrayList<>();
        songs.add(song1);
        songs.add(song2);
        songs.add(song3);
        songs.add(song4);

        testEntityManager.persistAndFlush(song1);
        testEntityManager.persistAndFlush(song2);
        testEntityManager.persistAndFlush(song3);
        testEntityManager.persistAndFlush(song4);  
        
        return songs;
	}

}

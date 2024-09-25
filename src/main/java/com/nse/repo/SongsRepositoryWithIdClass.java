package com.nse.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nse.entities.SongEntityWithEmbeddedCompositeKey;
import com.nse.entities.SongEntityWithIdClass;
import com.nse.entities.SongIdAsIdClass;
import com.nse.entities.SongIdAsCompositeKey;

/**
 *@Author: sanjeevkumar<br>
 *@Time: 12:00:25 am<br>
 *@Date: 12-Sep-2024 <br>
 */
@Repository
public interface SongsRepositoryWithIdClass extends CrudRepository<SongEntityWithIdClass, SongIdAsIdClass>{

}

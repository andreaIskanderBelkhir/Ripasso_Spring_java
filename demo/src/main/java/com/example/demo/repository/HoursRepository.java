package com.example.demo.repository;

import com.example.demo.entity.HourGame;
import com.example.demo.entity.Library;
import com.example.demo.entity.Hourssumm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
/**
 * class based on spring jpa-data, in this class the methods interact with the db
 */
public interface HoursRepository extends JpaRepository<HourGame, Library> {

    /**
     * method based on JpaRepository using the @QUery annotation, this method find the id of games with more then 0 hours played of a specific user
     * @param id the id of the user
     * @return a List of long, the id of the games
     */
    @Query(value="SELECT h.game_id FROM steam.hours h WHERE h.user_id =:id and h.hour > 0 ",nativeQuery = true
    )
    List<Long> findplayedgame(@Param("id") long id);

    /**
     * method based on JpaRepository using the @QUery annotation, this method aggregate the game and summs the hours from all user
     * @return a list of the ennum Hourssum
     */
    /*@Query(value="SELECT new Hourssumm(h.game_id, SUM(h.hour)) "
            + "FROM steam.hours h GROUP BY h.game_id ORDER BY h.hour DESC ",nativeQuery = true )*/
    @Query(value="SELECT h.game_id as id_game ,sum(h.hour) as hourtotal "
            + "FROM steam.hours h GROUP BY h.game_id ",nativeQuery = true )
    List<Hourssumm> summhourBygame();
}

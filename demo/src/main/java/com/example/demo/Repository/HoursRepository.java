package com.example.demo.Repository;

import com.example.demo.entita.HourGame;
import com.example.demo.entita.Library;
import com.example.demo.entita.Hourssumm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HoursRepository extends JpaRepository<HourGame, Library> {

    @Query(value="SELECT h.game_id FROM steam.hours h WHERE h.user_id =:id and h.hour > 0 ",nativeQuery = true
    )
    List<Long> findplayedgame(@Param("id") long id);

    /*@Query(value="SELECT new Hourssumm(h.game_id, SUM(h.hour)) "
            + "FROM steam.hours h GROUP BY h.game_id ORDER BY h.hour DESC ",nativeQuery = true )*/
    @Query(value="SELECT h.game_id as id_game ,sum(h.hour) as hourtotal "
            + "FROM steam.hours h GROUP BY h.game_id ",nativeQuery = true )
    List<Hourssumm> summhourBygame();
}

package com.jovana.nsibackend.repository;

import com.jovana.nsibackend.model.OptionVotes;
import com.jovana.nsibackend.model.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by jovana on 05.11.2018
 */
@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    @Query("select new com.jovana.nsibackend.model.OptionVotes(v.option.id, count(v.id)) " +
            "from Vote v where v.poll.id in :pollIds group by v.option.id")
    List<OptionVotes> countByPollIdInGroupByOptionId(@Param("pollIds") List<Long> pollIds);

    @Query("select new com.jovana.nsibackend.model.OptionVotes(v.option.id, count(v.id)) " +
            "from Vote v where v.poll.id = :pollId group by v.option.id")
    List<OptionVotes> countByPollIdGroupByOptionId(@Param("pollId") Long pollId);

    @Query("select v from Vote v where v.user.id = :userId and v.poll.id in :pollIds")
    List<Vote> findByUserIdAndPollIdIn(@Param("userId") Long userId, @Param("pollIds") List<Long> pollIds);

    @Query("select v from Vote v where v.user.id = :userId and v.poll.id = :pollId")
    Vote findByUserIdAndPollId(@Param("userId") Long userId, @Param("pollId") Long pollId);

    @Query("select count(v.id) from Vote v where v.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);

    @Query("select v.poll.id from Vote v where v.user.id = :userId")
    Page<Long> findVotedPollIdsByUserId(@Param("userId") Long userId, Pageable pageable);
}

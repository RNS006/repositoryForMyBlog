package com.org.coop.society.data.admin.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.org.coop.society.data.admin.entities.SecurityQuestion;

public interface SecurityQuestionRepository extends JpaRepository<SecurityQuestion, Integer> {
	public SecurityQuestion findByQuestionId(int questionId);
	
	/**
	 * This query retrieves records by limit randomly.
	 * Pageable takes two parameters. First argument is offset which is 0 in our case and the second parameter is the number of records
	 * @param pageable
	 * @return
	 */
	@Query("select sq from SecurityQuestion sq order by rand()")
	public List<SecurityQuestion> getRandomQuestionsByLimit(Pageable pageable);
}

package com.challenge.school.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.challenge.school.domain.School;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long>, JpaSpecificationExecutor<School> {
	
    Optional<School> findByName(String name);
    
    int countByName(String name);
    
    int countByNameAndIdNotIn(String name, List<Long> excludedIds);
}

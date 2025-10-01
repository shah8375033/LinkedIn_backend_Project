package com.linkedInProject.ConnectionService.repository;

import com.linkedInProject.ConnectionService.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends Neo4jRepository<Person,Long>{
    Optional<Person> findByUserId(Long userId);
    @Query("MATCH (personA:Person)-[:CONNECTED_TO]-(personB:Person) " +
            "WHERE personA.userid= $userId " +
            "RETURN personB")
    List<Person> getFirstDegreeConnections(Long userId);
}
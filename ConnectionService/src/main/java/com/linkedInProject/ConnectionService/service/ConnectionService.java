package com.linkedInProject.ConnectionService.service;

import com.linkedInProject.ConnectionService.entity.Person;
import com.linkedInProject.ConnectionService.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionService {
    final PersonRepository personRepository;

    public List<Person> getFirstDegreeConnection(Long userId) {
        log.info("Getting first degree connections for user {}", userId);

        return personRepository.getFirstDegreeConnections(userId);
    }
}

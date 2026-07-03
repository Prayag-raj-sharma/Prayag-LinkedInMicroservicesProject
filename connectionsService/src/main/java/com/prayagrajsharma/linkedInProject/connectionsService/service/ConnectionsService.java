package com.prayagrajsharma.linkedInProject.connectionsService.service;

import com.prayagrajsharma.linkedInProject.connectionsService.entity.Person;
import com.prayagrajsharma.linkedInProject.connectionsService.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionsService {

    private final PersonRepository personRepository;

    public List<Person> getFirstDegreeConnectionsOfUser(Long userId) {
        log.info("Fetching the first degree connection of user with userId: {}", userId);

        return personRepository.getFirstDegreeConnections(userId);
    }
}

package com.prayagrajsharma.linkedInProject.connectionsService.service;

import com.prayagrajsharma.linkedInProject.connectionsService.entity.Person;
import com.prayagrajsharma.linkedInProject.connectionsService.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public void createPerson(Long userId, String name) {
        Person person = Person.builder().userId(userId).name(name).build();
        personRepository.save(person);
    }
}

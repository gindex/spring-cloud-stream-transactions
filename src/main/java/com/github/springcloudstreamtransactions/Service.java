package com.github.springcloudstreamtransactions;

import com.github.springcloudstreamtransactions.model.Person;
import com.github.springcloudstreamtransactions.model.PersonRepo;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class Service {

    private PersonRepo personRepo;

    @Transactional
    public String process(String name) {
        Optional<Person> personEntity = personRepo.findAll().stream()
                .filter(person -> person.getName().equals(name))
                .findAny();

        return personEntity.map(person -> {
            var address = person.getAddress();
            return address.getAddress();
        }).orElseThrow();
    }
}


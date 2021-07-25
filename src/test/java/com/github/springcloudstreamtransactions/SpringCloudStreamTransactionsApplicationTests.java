package com.github.springcloudstreamtransactions;

import com.github.springcloudstreamtransactions.model.Address;
import com.github.springcloudstreamtransactions.model.AddressRepo;
import com.github.springcloudstreamtransactions.model.Person;
import com.github.springcloudstreamtransactions.model.PersonRepo;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("integration")
@Import(TestChannelBinderConfiguration.class)
@SpringBootTest
class SpringCloudStreamTransactionsApplicationTests {

    @Autowired
    private InputDestination inputDestination;

    @Autowired
    private OutputDestination outputDestination;

    @Autowired
    private PersonRepo personRepo;

    @Autowired
    private AddressRepo addressRepo;

    private String NAME = "name";
    private String ADDRESS = "My Address";

    @BeforeEach
    void beforeEach() {
        var person = new Person();
        var address = new Address();

        address.setAddress(ADDRESS);
        address = addressRepo.saveAndFlush(address);

        person.setName(NAME);
        person.setAddress(address);
        personRepo.saveAndFlush(person);
    }

    @Test
    void testTransactionInFunctionalBinding() {
        inputDestination.send(MessageBuilder.withPayload(NAME).build(), "consume");
        byte[] payload = outputDestination.receive(100).getPayload();
        var receivedAddress = new String(payload, StandardCharsets.UTF_8);

        assertEquals(ADDRESS, receivedAddress);
    }
}

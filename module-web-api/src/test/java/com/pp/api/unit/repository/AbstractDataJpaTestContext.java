package com.pp.api.unit.repository;

import com.pp.api.configuration.JpaConfiguration;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@Disabled
@DataJpaTest
@Import(value = JpaConfiguration.class)
@ActiveProfiles(value = "test")
abstract class AbstractDataJpaTestContext {

    @Autowired
    EntityManager entityManager;

}

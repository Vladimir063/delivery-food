package com.vladimir.deliveryfood.repository;

import com.vladimir.deliveryfood.entity.DishEntity;
import com.vladimir.deliveryfood.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // для использования БД не из памяти
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void findByEmail() {
        Optional<User> maybeUser = userRepository.findByEmail("user@mail.ru");

        Assertions.assertThat(maybeUser.get().getEmail()).isEqualTo("user@mail.ru");

    }
}
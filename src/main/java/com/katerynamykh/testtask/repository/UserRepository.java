package com.katerynamykh.testtask.repository;

import com.katerynamykh.testtask.model.User;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.birthDate BETWEEN :from AND :to")
    List<User> getAllByBirthDateRange(final @Param(value = "from") LocalDate from,
            final @Param(value = "to") LocalDate to);
}

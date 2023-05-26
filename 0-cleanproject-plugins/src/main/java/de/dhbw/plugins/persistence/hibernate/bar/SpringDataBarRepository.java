package de.dhbw.plugins.persistence.hibernate.bar;

import de.dhbw.cleanproject.domain.bar.Bar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringDataBarRepository extends JpaRepository<Bar, UUID> {

    Bar findBarByAdministrator(UUID administrator);

    Bar findBarById(UUID id);

    List<Bar> findBarsByTitleContaining(String title);

    Bar findBarByTitle(String title);
}

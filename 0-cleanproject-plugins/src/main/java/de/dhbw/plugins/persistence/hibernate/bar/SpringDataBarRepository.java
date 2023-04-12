package de.dhbw.plugins.persistence.hibernate.bar;

import de.dhbw.cleanproject.domain.bar.Bar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataBarRepository extends JpaRepository<Bar, UUID> {

    Bar findBarByAdministrator(UUID administrator);
}

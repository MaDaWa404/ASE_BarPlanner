package de.dhbw.plugins.persistence.hibernate.drink;

import de.dhbw.cleanproject.domain.drink.Drink;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataDrinkRepository extends JpaRepository<Drink, UUID> {

    List<Drink> findDrinkByTitle(final String title);

}

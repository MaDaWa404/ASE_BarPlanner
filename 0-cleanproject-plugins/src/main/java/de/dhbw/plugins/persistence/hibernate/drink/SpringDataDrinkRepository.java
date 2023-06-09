package de.dhbw.plugins.persistence.hibernate.drink;

import de.dhbw.cleanproject.domain.drink.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringDataDrinkRepository extends JpaRepository<Drink, UUID> {

    List<Drink> findAllByTitleContaining(final String title);

    List<Drink> findAllByBar(final UUID bar);

    List<Drink> findAllByBarAndTitleContaining(final UUID bar, final String title);

    Drink findDrinkByBarAndTitle(final UUID bar, final String title);

    Drink findByTitle(final String title);

}

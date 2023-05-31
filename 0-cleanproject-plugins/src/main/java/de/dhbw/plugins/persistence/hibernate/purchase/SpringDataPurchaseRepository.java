package de.dhbw.plugins.persistence.hibernate.purchase;

import de.dhbw.cleanproject.domain.purchase.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringDataPurchaseRepository extends JpaRepository<Purchase, UUID> {
    List<Purchase> findPurchasesByPerson(UUID person);

}

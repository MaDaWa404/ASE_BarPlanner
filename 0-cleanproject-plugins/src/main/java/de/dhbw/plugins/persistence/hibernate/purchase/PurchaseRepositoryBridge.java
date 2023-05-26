package de.dhbw.plugins.persistence.hibernate.purchase;


import de.dhbw.cleanproject.domain.purchase.Purchase;
import de.dhbw.cleanproject.domain.purchase.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PurchaseRepositoryBridge implements PurchaseRepository {

    private final SpringDataPurchaseRepository springDataPurchaseRepository;

    @Autowired
    public PurchaseRepositoryBridge(final SpringDataPurchaseRepository springDataPurchaseRepository) {
        this.springDataPurchaseRepository = springDataPurchaseRepository;
    }

    @Override
    public void save(Purchase p) {
        springDataPurchaseRepository.save(p);
    }
}

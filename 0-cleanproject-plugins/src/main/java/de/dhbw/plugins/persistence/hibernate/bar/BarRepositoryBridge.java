package de.dhbw.plugins.persistence.hibernate.bar;


import de.dhbw.cleanproject.domain.bar.Bar;
import de.dhbw.cleanproject.domain.bar.BarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class BarRepositoryBridge implements BarRepository {

    private final SpringDataBarRepository springDataBarRepository;

    @Autowired
    public BarRepositoryBridge(final SpringDataBarRepository springDataBarRepository) {
        this.springDataBarRepository = springDataBarRepository;
    }

    @Override
    public List<Bar> findAllBars() {
        return this.springDataBarRepository.findAll();
    }

    @Override
    public Bar findBarByAdministrator(UUID administrator) {
        return this.springDataBarRepository.findBarByAdministrator(administrator);
    }

    @Override
    public Bar save(Bar bar) {
        return this.springDataBarRepository.save(bar);
    }

    @Override
    public void delete(Bar bar) {
        this.springDataBarRepository.delete(bar);
    }
}

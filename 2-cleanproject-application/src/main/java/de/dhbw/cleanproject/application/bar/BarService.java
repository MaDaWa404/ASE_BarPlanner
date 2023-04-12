package de.dhbw.cleanproject.application.bar;

import de.dhbw.cleanproject.domain.bar.Bar;
import de.dhbw.cleanproject.domain.bar.BarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BarService {

    private final BarRepository barRepository;

    @Autowired
    public BarService(final BarRepository barRepository) {
        this.barRepository = barRepository;
    }

    public Bar findBarByAdministrator(UUID id) {
        return barRepository.findBarByAdministrator(id);
    }

    public Bar save(Bar bar) {
        return barRepository.save(bar);
    }

    public void delete(Bar bar) {
        barRepository.delete(bar);
    }
}
package de.dhbw.cleanproject.application.bar;

import de.dhbw.cleanproject.application.exceptions.MyErrorCode;
import de.dhbw.cleanproject.application.exceptions.MyException;
import de.dhbw.cleanproject.domain.bar.Bar;
import de.dhbw.cleanproject.domain.bar.BarRepository;
import de.dhbw.cleanproject.domain.person.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BarService {

    private final BarRepository barRepository;

    @Autowired
    public BarService(final BarRepository barRepository) {
        this.barRepository = barRepository;
    }

    public List<Bar> findAll() {
        return barRepository.findAllBars();
    }

    public Bar findBarById(UUID id) {
        return barRepository.findBarById(id);
    }

    public Bar findBarByAdministrator(UUID id) {
        return barRepository.findBarByAdministrator(id);
    }

    public List<Bar> findBarsByTitleContaining(String title) {
        return barRepository.findBarsByTitleContaining(title);
    }

    public Bar save(Bar bar) {
        return barRepository.save(bar);
    }

    public void delete(Bar bar) {
        barRepository.delete(bar);
    }

    public Bar getBarFromPerson(Person p) throws MyException {
        Bar b = findBarByAdministrator(p.getId());
        if (b == null) throw new MyException(MyErrorCode.NO_BAR);
        return b;
    }
}
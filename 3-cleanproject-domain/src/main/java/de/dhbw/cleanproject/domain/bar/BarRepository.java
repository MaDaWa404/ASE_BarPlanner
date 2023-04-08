package de.dhbw.cleanproject.domain.bar;

import java.util.List;
import java.util.UUID;

public interface BarRepository {

    List<Bar> findAllBars();

    Bar findBarByAdministrator(UUID administrator);

    Bar save(Bar bar);

    void delete(Bar bar);
}

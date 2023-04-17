package de.dhbw.cleanproject.adapter.bar;

import de.dhbw.cleanproject.domain.bar.Bar;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class BarToBarResourceMapper implements Function<Bar, BarResource> {

    @Override
    public BarResource apply(final Bar bar) {
        return map(bar);
    }

    private BarResource map(final Bar bar) {
        return new BarResource(bar.getId(), bar.getTitle(), bar.getZip(), bar.getCity(), bar.getStreet(), bar.getNumber());
    }
}

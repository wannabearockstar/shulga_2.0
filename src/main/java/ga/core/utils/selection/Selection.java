package ga.core.utils.selection;

import ga.core.model.Population;

public interface Selection {
    Population get(Population population, int selectionSize);
}

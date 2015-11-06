package ga.model.comparator;

import ga.model.schedule.Group;

import java.util.Comparator;

public class GroupComparator implements Comparator<Group> {

    @Override
    public int compare(Group fst, Group snd) {
        return Integer.compare(fst.getId(), snd.getId());
    }
}

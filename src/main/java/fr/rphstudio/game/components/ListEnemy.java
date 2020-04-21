package fr.rphstudio.game.components;

import fr.rphstudio.ecs.core.interf.IComponent;
import fr.rphstudio.ecs.core.utils.CoreUtils;

public class ListEnemy implements IComponent {
    private final long     id;
    private final String   name;

    public ListEnemy() {        // Store name
        this.name = getClass().getName();
        // Get unique ID
        this.id = CoreUtils.getNewID();
    }

    public long getID() {
        return id;
    }

    public String getName() {
        return name;
    }
}

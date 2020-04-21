package fr.rphstudio.game.scripts;

import fr.rphstudio.ecs.component.common.Life;
import fr.rphstudio.ecs.component.common.Score;
import fr.rphstudio.ecs.component.physic.Physic2D;
import fr.rphstudio.ecs.component.render.RenderText;
import fr.rphstudio.ecs.core.Entity;
import fr.rphstudio.ecs.core.interf.IComponent;
import fr.rphstudio.ecs.core.interf.IScript;
import fr.rphstudio.ecs.core.utils.CoreUtils;
import fr.rphstudio.game.components.EnemyState;
import fr.rphstudio.launcher.Common;
import java.util.List;
import org.dyn4j.dynamics.Body;
import org.newdawn.slick.SlickException;


public class ScriptEndGame implements IComponent, IScript {
    
    private final long     id;
    private final String   name;
    
    private Life         life;
    private Physic2D     topPhysic;
    

    public ScriptEndGame(Life lif, Physic2D topPhy)
    {
        // Get Name
        this.name = getClass().getName();
        // Get unique ID
        this.id = CoreUtils.getNewID();
    
        // Store private fields
        this.life      = lif;
        this.topPhysic = topPhy;
    }

    public long getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public void update(int delta) throws SlickException
    {
        // Set the life value to zero if there are too many cells touching the top bar
        if(this.life != null && this.topPhysic != null)
        {
            if(this.topPhysic.getBody().getContacts(false).size() >= Common.END_GAME_NB_CONTACTS)
            {
                this.life.setLife(0);
            }
        }
    }
    
    
}

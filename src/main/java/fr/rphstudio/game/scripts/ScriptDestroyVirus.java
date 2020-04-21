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
import java.util.List;
import org.dyn4j.dynamics.Body;
import org.newdawn.slick.SlickException;


public class ScriptDestroyVirus implements IComponent, IScript {
    
    private final long     id;
    private final String   name;
    
    private Score        score;
    private Physic2D     bottomPhysic;
    private List<Entity> virusList;
    private RenderText   renderScore;
    

    public ScriptDestroyVirus(Score scor, Physic2D botPhy, List<Entity> virus, RenderText renScore )
    {
        // Get Name
        this.name = getClass().getName();
        // Get unique ID
        this.id = CoreUtils.getNewID();
    
        // Store private fields
        this.score        = scor;
        this.bottomPhysic = botPhy;
        this.virusList    = virus;
        this.renderScore  = renScore;
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
        // DESTROY WHEN ALL VIRUS HAVE BEEN CLEANED
        boolean mustBeDestroyed = false;
        for(Entity ent : this.virusList)
        {
            List<IComponent> compList = ent.getComponent(EnemyState.class);
            if(compList.size() > 0)
            {
                // Init status to destroy
                mustBeDestroyed = true;
                // For each status component
                for(IComponent cmp:compList)
                {
                    // Check the component has been cleaned or not
                    if( ((EnemyState)(cmp)).isCorrupted() )
                    {
                        mustBeDestroyed = false;
                        break;
                    }
                }
                // Destroy the entity if all shapes have been cleaned
                if(mustBeDestroyed)
                {
                    if(ent.getDestroyRequest() == false)
                    {
                        // Set destroy request
                        ent.setDestroyRequest();
                        // Increase score
                        this.score.increaseValue(1);
                    }
                }
            }
        }
        // DISPLAY AND SCORE
        this.renderScore.setText("SCORE = "+Integer.toString((int)this.score.getValue()));                                
    }
    
    
}

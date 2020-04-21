package fr.rphstudio.game.scripts;

import fr.rphstudio.ecs.component.render.RenderAnimations;
import fr.rphstudio.ecs.core.ECSWorld;
import fr.rphstudio.ecs.core.Entity;
import fr.rphstudio.ecs.core.interf.IComponent;
import fr.rphstudio.ecs.core.interf.IScript;
import fr.rphstudio.ecs.core.utils.CoreUtils;
import fr.rphstudio.game.factories.FactoryEnemy;
import fr.rphstudio.launcher.Common;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.SlickException;

public class ScriptGenerateEnemy implements IComponent, IScript {
    private final long     id;
    private final String   name;

    private ECSWorld world;
    private long startTime;
    private long timeInterval;
    private int enemyID;
    private List<Entity> virusList;
    private List<Entity> playerList;
    private List<RenderAnimations> animPlayerList;
    
    
    public ScriptGenerateEnemy(ECSWorld wrld, List<Entity> players, List<Entity> virus ){        // Store name
        this.name = getClass().getName();
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // init time
        this.timeInterval      = Common.GENERATION_MAX_INTERVAL_MS;
        this.startTime         = 0;
        this.world             = wrld;
        this.enemyID           = 1;
        this.playerList        = players;
        this.virusList         = virus;    
        // Store all player rendering animation components
        this.animPlayerList = new ArrayList<RenderAnimations>();
        for(Entity p:this.playerList)
        {
            List<IComponent> listRender;
            listRender = p.getComponent(RenderAnimations.class);
            if(listRender.size()==1)
            {
                if(listRender.get(0) instanceof RenderAnimations)
                {
                    this.animPlayerList.add( (RenderAnimations)(listRender.get(0)) );
                }
            }
        }
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
        // Check if time is elapsed
        long endTime = System.currentTimeMillis();
        if( (endTime-this.startTime) >= this.timeInterval )
        {
            // Keep rest of the time (if exceed)
            this.startTime = endTime;
            // Decrease timeInterval if possible
            this.timeInterval = Math.max( this.timeInterval-Common.GENERATION_STEP_INTERVAL_MS, Common.GENERATION_MIN_INTERVAL_MS);
            // Create Enemy
            Entity myVirus = FactoryEnemy.create( this.world, this.enemyID, this.animPlayerList, (int)(Math.random()*this.playerList.size())+1, this.playerList.size() );
            // Add enemy to the ECS world
            this.world.addEntity(myVirus);
            this.enemyID += Common.SHAPE_INDEX_NB;
            // Add virus into the list
            this.virusList.add(myVirus);
        }
    }
}

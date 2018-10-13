package com.spitfire.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Pool;
import com.spitfire.game.controller.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A wrapper class for the Box2D physics world that includes pools for memory management and levels.
 */
public class MyWorld {

    //-----Fields
    private static final float TIME_STEP = 1/45f;
    private static final int VELOCITY_ITERATIONS = 6;
    private static final int POSITION_ITERATIONS = 2;

    protected final MyGame game; //Application this world is apart of

    private Pool<Projectile> projectile_pool = null; //Contains a pool of projectiles to be generated
    private Pool<Enemy> enemy_pool = null; //Contains a pool of enemies to be generated

    private List<Projectile> active_projectiles = null; //Projectiles that are currently being used
    private List<Enemy> active_enemies = null; //Enemies that are currently being used

    private static final ArrayList<Explosion> EXPLOSION_LIST = new ArrayList<Explosion>();

    World world = null; //The physical world all the object bodies exist on

    private static final Vector2 GRAVITY = new Vector2(0,0); //The gravity of the world

    private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    //-----Constructors

    /**
     * Default constructor.
     * @param g game: A reference to the game the world is apart of.
     */
    public MyWorld(final MyGame g) {
        game = g;
    }

    //-----Methods

    /**
     * Initializes the world and pools for the current level.
     */
    public void init() {
        if (world != null)
            world.dispose();

        world = new World(GRAVITY, true);

        world.setContactListener(new DamageListener());

        if (projectile_pool != null)
            projectile_pool.clear();
        else {
            projectile_pool = new Pool<Projectile>() {
                @Override
                protected Projectile newObject() {
                    return new Projectile();
                }
            };
        }

        if (enemy_pool != null)
            enemy_pool.clear();
        else {
            enemy_pool = new Pool<Enemy>() {
                @Override
                protected Enemy newObject() {
                    return new Enemy();
                }
            };
        }

        if (active_projectiles != null)
            active_projectiles.clear();
        else
            active_projectiles = Collections.synchronizedList(new ArrayList<Projectile>());

        if (active_enemies != null)
            active_enemies.clear();
        else
            active_enemies = Collections.synchronizedList(new ArrayList<Enemy>());
    }

    /**
     * Populates the current world based on the current level.
     * @param level: to generate and populate the world.
     */
    public void populate(Level level) {
        Formation formation = level.getFormation();

        for (Position p: formation.createFormation()) {
            EnemyDef enemy_def;
            if ((enemy_def = game.resource_manager.getAsset(
                    p.name,
                    EnemyDef.class)) != null) {
                enemy_def.width = p.x_pos;
                enemy_def.height = p.y_pos;
                createEnemy(enemy_def);
            }
        }
    }

    public void step() {
        /*Step the world*/
        debugRenderer.render(world, game.camera.combined);
        world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
    }

    /**
     * Adds a new projectile to the world based off the given projectile definition. This is obtained
     * from the projectile pool to be later freed.
     * @param pd ProjectileDef: The descriptor for the projectile to be created.
     */
    public void createProjectile(ProjectileDef pd) {
        Projectile newProjectile = projectile_pool.obtain();
        newProjectile.init(pd, this);
        active_projectiles.add(newProjectile);
    }

    /**
     * Adds a new enemy to the world based off the given enemy definition. This is obtained from
     * the enemy pool to be later freed.
     * @param ed EnemyDef: The descriptor for the enemy to be created.
     */
    private void createEnemy(EnemyDef ed) {
        Enemy newEnemy = enemy_pool.obtain();
        newEnemy.init(ed, this);
        active_enemies.add(newEnemy);
    }

    /**
     * Sends all the active components of the world to be rendered.
     * @return Returns the active components of the world to the game.
     */
    public List<Entity> getActiveComponents() {
        List<Entity> master_list = new ArrayList<Entity>();

        master_list.addAll(active_projectiles);
        master_list.addAll(active_enemies);

        return master_list;
    }

    public void clean() {
        /*Check active list to see if anyone is no longer active*/
        Iterator iter = active_projectiles.iterator();

        while (iter.hasNext()) {
            Projectile projectile = (Projectile) iter.next();
            if (!projectile.isActive()) {
                iter.remove();
                EXPLOSION_LIST.add(new Explosion(
                        projectile.body.getPosition(),
                        4000f));
                world.destroyBody(projectile.body);
                projectile_pool.free(projectile);
            }
        }

        iter = active_enemies.iterator();

        while (iter.hasNext()) {
            Enemy enemy = (Enemy) iter.next();

            if (!enemy.isActive()) {
                iter.remove();
                EXPLOSION_LIST.add(new Explosion(
                        enemy.body.getPosition(),
                        4000f));
                world.destroyBody(enemy.body);
                enemy_pool.free(enemy);
            }
        }

        iter = EXPLOSION_LIST.iterator();

        while (iter.hasNext()) {
            Explosion explosion = (Explosion) iter.next();
            for (Entity entity : this.getActiveComponents()) {
                if (explosion.contains(entity.body.getWorldCenter())) {
                    ((Entity)entity.body.getUserData()).explode(explosion);
                }
            }
        }

        EXPLOSION_LIST.clear();
    }

    /**
     * Disposes and cleans up the world.
     */
    public void dispose() {
        projectile_pool.clear();
        enemy_pool.clear();
        active_projectiles.clear();
        active_enemies.clear();
        world.dispose();
    }
    //-----Getters and Setters
}

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
    private static final int BARRIER_HEIGHT = 280;
    private static final int BARRER_WIDTH = 128;

    protected final MyGame game; //Application this world is apart of

    private Pool<Projectile> projectile_pool = null; //Contains a pool of projectiles to be generated
    private Pool<Enemy> enemy_pool = null; //Contains a pool of enemies to be generated

    private List<Projectile> active_projectiles = null; //Projectiles that are currently being used
    private List<Enemy> active_enemies = null; //Enemies that are currently being used

    private Body top_barrier;
    private Fixture top_fix;
    private Body bottom_barrier;
    private Fixture bottom_fix;
    private Body left_barrier;
    private Fixture left_fix;
    private Body right_barrier;
    private Fixture right_fix;

    private final ArrayList<Explosion> EXPLOSION_LIST = new ArrayList<Explosion>();

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

        world.setContactListener(new DamageListener(game));

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

        /* Define the upper and lower limits of the map */
        BodyDef barrier_body = new BodyDef();
        FixtureDef barrier_fix = new FixtureDef();
        barrier_body.type = BodyDef.BodyType.StaticBody;
        barrier_fix.filter.categoryBits = EnumManager.EntityType.getVal(EnumManager.EntityType.WALL);
        barrier_fix.filter.maskBits = EnumManager.EntityType.getVal(EnumManager.EntityType.ALL);
        barrier_fix.friction = 1f;
        barrier_fix.density = 1f;
        barrier_fix.restitution = 1f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(game.camera.viewportWidth + BARRER_WIDTH, BARRIER_HEIGHT);
        barrier_fix.shape = shape;

        barrier_body.position.set(new Vector2(0, game.camera.viewportHeight));
        top_barrier = world.createBody(barrier_body);
        top_fix = top_barrier.createFixture(barrier_fix);
        barrier_body.position.set(new Vector2(0, 0));
        bottom_barrier = world.createBody(barrier_body);
        bottom_fix = bottom_barrier.createFixture(barrier_fix);

        /*Define the right and left limits of the map */
        shape.setAsBox(1, game.camera.viewportHeight);
        barrier_fix.shape = shape;
        barrier_fix.filter.categoryBits = EnumManager.EntityType.getVal(EnumManager.EntityType.BOUND);

        barrier_body.position.set(new Vector2(-BARRER_WIDTH, 0));
        left_barrier = world.createBody(barrier_body);
        left_fix = left_barrier.createFixture(barrier_fix);
        left_barrier.setUserData(true);
        barrier_body.position.set(new Vector2(game.camera.viewportWidth+BARRER_WIDTH, 0));
        right_barrier = world.createBody(barrier_body);
        right_fix = right_barrier.createFixture(barrier_fix);
        right_barrier.setUserData(false);

        shape.dispose();
    }

    /**
     * Populates the current world based on the current level.
     * @param level: to generate and populate the world.
     */
    public void populate(Level level) {
        Formation formation = level.getFormation();

        if (formation == null) {
            game.completed();
            return;
        }

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
        this.update();
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

    private void update() {
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

        /* Create explosions for those who have died */
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

        /* Check the number of live enemies */
        if (active_enemies.size() <= 0) {
            if (game.isPlaying)
                this.populate(game.current_level);
        }
    }

    /**
     * Disposes and cleans up the world.
     */
    public void dispose() {
        projectile_pool.clear();
        enemy_pool.clear();
        active_projectiles.clear();
        active_enemies.clear();
        top_barrier.destroyFixture(top_fix);
        world.destroyBody(top_barrier);
        bottom_barrier.destroyFixture(bottom_fix);
        world.destroyBody(bottom_barrier);
        left_barrier.destroyFixture(left_fix);
        world.destroyBody(left_barrier);
        right_barrier.destroyFixture(right_fix);
        world.destroyBody(right_barrier);
        world.dispose();
    }
    //-----Getters and Setters
}

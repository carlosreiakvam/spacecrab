package com.maincrab;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.ArrayList;

public class BaseActor extends Group {

    private Animation<TextureRegion> animation;
    private float elapsedTime;
    private boolean animationPaused;

    private Vector2 velocityVec;
    private Vector2 accelerationVec;
    private float acceleration;
    private float maxSpeed;
    private float deceleration;

    private Polygon boundaryPolygon;
    private static Rectangle worldBounds;

    private static boolean isHit;

    public int hp;


    public BaseActor(float x, float y, Stage s) {
        // call constructor from Actor class
        super();

        // perform additional initialization tasks
        setPosition(x, y);
        s.addActor(this);

        // initialize animation data
        animation = null;
        elapsedTime = 0;
        animationPaused = false;

        // initialize physics data
        velocityVec = new Vector2(0, 0);
        accelerationVec = new Vector2(0, 0);
        acceleration = 0;
        maxSpeed = 1000;
        deceleration = 0;

        boundaryPolygon = null;
        hp = 100;

    }



   // CAMERA

    public void alignCamera() {
        Camera cam = this.getStage().getCamera();
        Viewport v = this.getStage().getViewport();

        // center camera on actor
        cam.position.set(this.getX() + this.getOriginX(), this.getY() + this.getOriginY(), 0 );

        // bound camera to layout
        cam.position.x = MathUtils.clamp(cam.position.x,
                cam.viewportWidth/2, worldBounds.width - cam.viewportWidth/2);
        cam.position.y = MathUtils.clamp(cam.position.y,
                cam.viewportHeight/2, worldBounds.height - cam.viewportHeight/2);
        cam.update();
    }



   //DEALING WITH MULTIPLE ACTORS

    public static ArrayList<BaseActor> getList(Stage stage, String className) {
        ArrayList<BaseActor> list = new ArrayList<BaseActor>();

        Class theClass = null;
        try {
            theClass = Class.forName(className);
        } catch (Exception error) {
            error.printStackTrace();
        }

        for (Actor a : stage.getActors()) {
            if (theClass.isInstance(a))
                list.add((BaseActor) a);
        }
        return list;
    }

    public static int count(Stage stage, String className) {
        return getList(stage, className).size();
    }



    // ACTOR PLACEMENT METHODS

    /**
     * Align center of actor at given position coordinates.
     *
     * @param x x-coordinate to center at
     * @param y y-coordinate to center at
     */
    public void centerAtPosition(float x, float y) {
        setPosition(x - getWidth() / 2, y - getHeight() / 2);
    }

    /**
     * Repositions this BaseActor so its center is aligned
     * with center of other BaseActor. Useful when one BaseActor spawns another.
     *
     * @param other BaseActor to align this BaseActor with
     */
    public void centerAtActor(BaseActor other) {
        centerAtPosition(other.getX() + other.getWidth() / 2, other.getY() + other.getHeight() / 2);
    }



    // ANIMATION METHODS

    /**
     * Sets the animation used when rendering this actor; also sets actor size.
     *
     * @param anim animation that will be drawn when actor is rendered
     */
    public void setAnimation(Animation<TextureRegion> anim) {
        animation = anim;
        TextureRegion tr = animation.getKeyFrame(0);
        float w = tr.getRegionWidth();
        float h = tr.getRegionHeight();
        setSize(w, h);
        setOrigin(w / 2, h / 2);

        if (boundaryPolygon == null)
            setBoundaryRectangle();
    }

    /**
     * Creates an animation from images stored in separate files.
     *
     * @param fileNames     array of names of files containing animation images
     * @param frameDuration how long each frame should be displayed
     * @param loop          should the animation loop
     * @return animation created (useful for storing multiple animations)
     */
    public Animation<TextureRegion> loadAnimationFromFiles(String[] fileNames,
                                                           float frameDuration, boolean loop) {

        int fileCount = fileNames.length;
        Array<TextureRegion> textureArray = new Array<TextureRegion>();

        for (int n = 0; n < fileCount; n++) {
            String fileName = fileNames[n];
            Texture texture = new Texture(Gdx.files.internal(fileName));
            texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
            textureArray.add(new TextureRegion(texture));
        }

        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, textureArray);

        if (loop)
            anim.setPlayMode(PlayMode.LOOP);
        else
            anim.setPlayMode(PlayMode.NORMAL);

        if (animation == null)
            setAnimation(anim);

        return anim;
    }

    /**
     * Creates an animation from a spritesheet: a rectangular grid of images stored in a single file.
     *
     * @param fileName      name of file containing spritesheet
     * @param rows          number of rows of images in spritesheet
     * @param cols          number of columns of images in spritesheet
     * @param frameDuration how long each frame should be displayed
     * @param loop          should the animation loop
     * @return animation created (useful for storing multiple animations)
     */
    public Animation<TextureRegion> loadAnimationFromSheet(String fileName, int rows, int cols,
                                                           float frameDuration, boolean loop) {
        Texture texture = new Texture(Gdx.files.internal(fileName), true);
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        int frameWidth = texture.getWidth() / cols;
        int frameHeight = texture.getHeight() / rows;

        TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);

        Array<TextureRegion> textureArray = new Array<TextureRegion>();

        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                textureArray.add(temp[r][c]);

        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration,
                textureArray);

        if (loop)
            anim.setPlayMode(PlayMode.LOOP);
        else
            anim.setPlayMode(PlayMode.NORMAL);

        if (animation == null)
            setAnimation(anim);
        return anim;
    }

    /**
     * Convenience method for creating a 1-frame animation from a single texture.
     *
     * @param fileName names of image file
     * @return animation created (useful for storing multiple animations)
     */
    public Animation<TextureRegion> loadTexture(String fileName) {
        String[] fileNames = new String[1];
        fileNames[0] = fileName;
        return loadAnimationFromFiles(fileNames, 1, true);
    }

    /**
     * Set the pause state of the animation.
     *
     * @param pause true to pause animation, false to resume animation
     */
    public void setAnimationPaused(boolean pause) {
        animationPaused = pause;
    }

    public boolean isAnimationFinished() {
        return animation.isAnimationFinished(elapsedTime);
    }

    /**
     * Sets the opacity of this actor.
     *
     * @param opacity value from 0 (transparent) to 1 (opaque)
     */
    public void setOpacity(float opacity) {
        this.getColor().a = opacity;
    }



    // PHYSICS / MOTION METHODS

    public void applyPhysics(float dt) {
        // apply acceleration
        velocityVec.add(accelerationVec.x * dt, accelerationVec.y * dt);

        float speed = getSpeed();

        // decrease speed (decelerate) when not accelerating
        if (accelerationVec.len() == 0)
            speed -= deceleration * dt;

        // keep speed within set bounds
        speed = MathUtils.clamp(speed, 0, maxSpeed);

        // update velocity
        setSpeed(speed);

        // apply velocity
        moveBy(velocityVec.x * dt, velocityVec.y * dt);

        //reset acceleration
        accelerationVec.set(0, 0);
    }

    public void setMaxSpeed(float ms) {
        maxSpeed = ms;
    }

    public void setDeceleration(float dec) {
        deceleration = dec;
    }

    public void setSpeed(float speed) {
        // if length is zero, then assume motion angle is zero degrees
        if (velocityVec.len() == 0)
            velocityVec.set(speed, 0);
        else
            velocityVec.setLength(speed);
    }

    public void setAcceleration(float acc) {
        acceleration = acc;
    }

    public void accelerateAtAngle(float angle) {
        accelerationVec.add(new Vector2(acceleration, 0).setAngle(angle));
    }

    public void accelerateForward() {
        accelerateAtAngle(getRotation());
    }

    public float getSpeed() {
        return velocityVec.len();
    }

    public void setMotionAngle(float angle) {
        velocityVec.setAngle(angle);
    }

    public float getMotionAngle() {
        return velocityVec.angle();
    }

    public boolean isMoving() {
        return (getSpeed() > 0);
    }



    // ACTOR METHODS

    /**
     * Processes all Actions and related code for this object;
     * automatically called by act method in Stage class.
     *
     * @param dt elapsed time (second) since last frame (supplied by Stage act method)
     */
    public void act(float dt) {
        super.act(dt);
        if (!animationPaused)
            elapsedTime += dt;

    }

    /**
     * Draws current frame of animation; automatically called by draw method in Stage class. <br>
     * If color has been set, image will be tinted by that color. <br>
     * If no animation has been set or object is invisible, nothing will be drawn.
     *
     * @param batch       (supplied by Stage draw method)
     * @param parentAlpha (supplied by Stage draw method)
     * @see #setColor
     * @see #setVisible
     */
    public void draw(Batch batch, float parentAlpha) {

        // apply color tint effect
        Color c = getColor();
        batch.setColor(c.r, c.g, c.b, c.a);

        if (animation != null && isVisible())
            batch.draw(animation.getKeyFrame(elapsedTime),
                    getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

        super.draw(batch, parentAlpha);
    }



    // COLLISION POLYGON METHODS

    /**
     * Set rectangular-shaped collision polygon.
     * This method is automatically called when animation is set,
     * provided that the current boundary polygon is null.
     *
     * @see #setAnimation
     */
    public void setBoundaryRectangle() {
        float w = getWidth();
        float h = getHeight();
        float[] vertices = {0, 0, w, 0, w, h, 0, h};
        boundaryPolygon = new Polygon(vertices);
    }

    /**
     * Replace default (rectangle) collision polygon with an n-sided polygon. <br>
     * Vertices of polygon lie on the ellipse contained within bounding rectangle.
     * Note: one vertex will be located at point (0,width);
     * a 4-sided polygon will appear in the orientation of a diamond.
     *
     * @param numSides number of sides of the collision polygon
     */
    public void setBoundaryPolygon(int numSides) {
        float w = getWidth();
        float h = getHeight();
        float[] vertices = new float[2 * numSides];

        for (int i = 0; i < numSides; i++) {
            float angle = i * 6.28f / numSides;
            // x-coordinate
            vertices[2 * i] = w / 2 * MathUtils.cos(angle) + w / 2;
            // y-coordinate
            vertices[2 * i + 1] = h / 2 * MathUtils.sin(angle) + h / 2;
        }
        boundaryPolygon = new Polygon(vertices);
    }

    /**
     * Returns bounding polygon for this BaseActor, adjusted by Actor's current position and rotation.
     *
     * @return bounding polygon for this BaseActor
     */
    public Polygon getBoundaryPolygon() {
        boundaryPolygon.setPosition(getX(), getY());
        boundaryPolygon.setOrigin(getOriginX(), getOriginY());
        boundaryPolygon.setRotation(getRotation());
        boundaryPolygon.setScale(getScaleX(), getScaleY());
        return boundaryPolygon;
    }

    public boolean isOverlaps () {
        return this.overlaps(this);
    }

    /**
     * Determine if this BaseActor overlaps other BaseActor (according to collision polygons).
     *
     * @param other BaseActor to check for overlap
     * @return true if collision polygons of this and other BaseActor overlap
     */
    public boolean overlaps(BaseActor other) {
        Polygon poly1 = this.getBoundaryPolygon();
        Polygon poly2 = other.getBoundaryPolygon();

        // initial test to improve performance
        if (!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle()))
            return false;
        return Intersector.overlapConvexPolygons(poly1, poly2);
    }

    /**
     * Implement a "solid"-like behavior:
     * when there is overlap, move this BaseActor away from other BaseActor
     * along minimum translation vector until there is no overlap.
     *
     * @param other BaseActor to check for overlap
     * @return direction vector by which actor was translated, null if no overlap
     */
    public Vector2 preventOverlap(BaseActor other) {
        Polygon poly1 = this.getBoundaryPolygon();
        Polygon poly2 = other.getBoundaryPolygon();

        // initial test to improve performance
        if (!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle()))
            return null;

        MinimumTranslationVector mtv = new MinimumTranslationVector();
        boolean polygonOverlap = Intersector.overlapConvexPolygons(poly1, poly2, mtv);

        if (!polygonOverlap)
            return null;

        this.moveBy(mtv.normal.x * mtv.depth, mtv.normal.y * mtv.depth);
        return mtv.normal;
    }



    // BOUNDARIES

    public static void setWorldBounds(float width, float height) {
        worldBounds = new Rectangle(0, 0, width, height);
    }

    public static void setWorldBounds(BaseActor ba) {
        setWorldBounds(ba.getWidth(), ba.getHeight());
    }

    public void boundToWorld() {
        //getWidth refers to the instance
        // check left edge
        if (getX() < 0)
            setX(0);

//        System.out.println("worldbounds width: " + worldBounds.getWidth());
        // check right edge
        if (getX() + getWidth() > worldBounds.getWidth())
            setX(worldBounds.width - getWidth() );

//        if (getX() != xCheck) {
//            xCheck = getX();
//            System.out.println("X: " + xCheck);
//        }
//        if (getY() != yCheck) {
//            yCheck = getY();
//            System.out.println("Y: " + yCheck);
//        }


        // check bottom edge
        if (getY() < 0)
            setY(0);
        //check top edge
        if (getY() + getHeight() > worldBounds.height)
            setY(worldBounds.height - getHeight());
    }

    public void wrapAroundWorld() {
        if (getX() + getWidth() < 0)
            setX(worldBounds.width);
        if (getX() > worldBounds.width)
            setX(-getWidth());
        if (getY() + getHeight() < 0)
            setY(worldBounds.height);
        if (getY() > worldBounds.height)
            setY(-getHeight());
    }
}

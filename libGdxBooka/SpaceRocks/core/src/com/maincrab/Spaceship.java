package com.maincrab;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;

import static com.maincrab.LevelScreen.globH;
import static com.maincrab.LevelScreen.globW;

public class Spaceship extends BaseActor {

    private Thrusters thrusters;
    private Shield shield;
    public int shieldPower;

    public Spaceship(float x, float y, Stage s) {
        super(x, y, s);

        loadTexture("spaceship.png");
        setBoundaryPolygon(8);
        setAcceleration(2200);
        setMaxSpeed(400);
        setDeceleration(10);

        thrusters = new Thrusters(0, 0, s);
        addActor(thrusters);
        thrusters.setPosition(-thrusters.getWidth(),
                getHeight() / 2 - thrusters.getHeight() / 2);

        // initialize shield
        shield = new Shield(0, 0, s);
        addActor(shield);
        shield.centerAtPosition(getWidth() / 2, getHeight() / 2);
        shieldPower = 100;
    }

    public void act(float dt) {
        super.act(dt);


        float degreesPerSecond = 720; // rotation speed
        if (Gdx.input.isKeyPressed(Keys.A))
            rotateBy(degreesPerSecond * dt);
        if (Gdx.input.isKeyPressed(Keys.D))
            rotateBy(-degreesPerSecond * dt);
        if (Gdx.input.isKeyPressed(Keys.W)) {
            accelerateAtAngle(getRotation());
            thrusters.setVisible(true);
        } else {
            thrusters.setVisible(false);
        }

        shield.setOpacity(shieldPower / 100f);
        if (shieldPower <= 0)
            shield.setVisible(false);
        applyPhysics(dt);
        wrapAroundWorld();
    }

    public void warp() {
        if (getStage() == null)
            return;

        Warp warp1 = new Warp(0, 0, this.getStage());
        warp1.centerAtActor(this);
        setPosition(MathUtils.random(globW), MathUtils.random(globH));
        Warp warp2 = new Warp(0, 0, this.getStage());
        warp2.centerAtActor(this);
    }

    public void shoot() {
        if (getStage() == null || this.getSpeed() == 0)
            return;
        Laser laser = new Laser(0, 0, this.getStage());
        laser.centerAtActor(this);
        laser.setRotation(this.getRotation());
        laser.setMotionAngle(this.getRotation());
    }

}

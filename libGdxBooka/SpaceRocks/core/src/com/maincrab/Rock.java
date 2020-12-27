package com.maincrab;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.math.MathUtils;


public class Rock extends BaseActor{

    public Rock (float x, float y, Stage s) {
        super(x,y,s);
        loadTexture("rock.png");
        float random = MathUtils.random(350);
        addAction(Actions.forever(Actions.rotateBy(30+random, 1)));

        int baseSpeed = 20;
        setSpeed(baseSpeed+random);
        setMaxSpeed(baseSpeed+random);
        setDeceleration(0);

        setMotionAngle(MathUtils.random(360));
    }

    public void act (float dt) {
        super.act(dt);

        applyPhysics(dt);
        wrapAroundWorld();
    }
}
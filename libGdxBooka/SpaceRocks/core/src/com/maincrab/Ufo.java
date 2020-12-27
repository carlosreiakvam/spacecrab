package com.maincrab;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Ufo extends BaseActor{

    public Ufo (float x, float y, Stage s) {
       super(x,y,s);
       loadTexture("ufo.png");
       addAction(Actions.forever(Actions.rotateBy(50, 1)));
       setSpeed(100);
       setMaxSpeed(1000);
       setDeceleration(0);
       setMotionAngle(MathUtils.random(360));
    }

    public void act (float dt) {
        super.act(dt);

        applyPhysics(dt);
        wrapAroundWorld();
    }
}

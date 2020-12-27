package com.maincrab;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Laser extends BaseActor{
    public Laser (float x,float y, Stage s) {
        super(x,y,s);

        loadTexture("laser.png");

        addAction(Actions.delay(0.1f));
//        addAction(Actions.after(Actions.fadeOut(0.1f)));
        addAction(Actions.after(Actions.removeActor()));

       setSpeed(2000);
       setMaxSpeed(2000);
       setDeceleration(0);
    }

    public void act (float dt) {
       super.act(dt);
       applyPhysics(dt);
       wrapAroundWorld();
    }
}
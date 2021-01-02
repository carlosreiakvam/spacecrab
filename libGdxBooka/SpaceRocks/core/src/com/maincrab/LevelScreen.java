package com.maincrab;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.audio.Sound;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.action;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.addAction;

public abstract class LevelScreen extends BaseScreen {

    private Spaceship spaceship;
    private Ufo ufo;
    private boolean gameOver;
    private boolean gameWin;
    private BaseActor messageLose;
    private BaseActor messageWin;
    private int ufoShrinkAmount = 25;
    private Sound laser;
    private Sound explosion;
    private Sound thruster;
    private Sound spaceshipHit;
    private Sound ufoHit;
    private Sound warp;
    private Music instrumental;

    // global variables
    public static int globW = 1920;
    public static int globH = 1080;

    public void initialize() {
        // Clear Screen
        if (gameOver || gameWin) {
            clearScreen();
        }


        //Initialize
        BaseActor space = new BaseActor(0, 0, mainStage);
        space.loadTexture("space.png");
        space.setSize(globW, globH);
        BaseActor.setWorldBounds(space);
        gameOver = false;
        gameWin = false;

        ufo = new Ufo(1000, 300, mainStage);
        ufo.setSize(400, 400);
        ufo.setBoundaryPolygon(8);
        spaceship = new Spaceship(400, 300, mainStage);

        new Rock(1000, 300, mainStage);
        new Rock(1000, 300, mainStage);
        new Rock(1000, 300, mainStage);
        new Rock(1000, 300, mainStage);
        new Rock(1000, 300, mainStage);
        new Rock(1000, 300, mainStage);
        new Rock(1000, 300, mainStage);
        new Rock(1000, 300, mainStage);
        new Rock(1000, 300, mainStage);
        new Rock(1000, 300, mainStage);
        new Rock(1000, 300, mainStage);
        new Rock(1000, 300, mainStage);
        new Rock(1000, 300, mainStage);
        new Rock(1000, 300, mainStage);

        laser = Gdx.audio.newSound(Gdx.files.internal("laser4.wav"));
        explosion = Gdx.audio.newSound(Gdx.files.internal("explosion1.wav"));
        thruster = Gdx.audio.newSound(Gdx.files.internal("thruster.wav"));
        spaceshipHit = Gdx.audio.newSound(Gdx.files.internal("spaceshipHit.wav"));
        ufoHit = Gdx.audio.newSound(Gdx.files.internal("ufoHit.wav"));
        warp = Gdx.audio.newSound(Gdx.files.internal("warp.wav"));
        instrumental = Gdx.audio.newMusic(Gdx.files.internal("song1.wav"));

        // Play song
        instrumental.setLooping(true);
        instrumental.play();
    }

    public void update(float dt) {

        // Rock hits spaceship
        for (BaseActor rockActor : BaseActor.getList(mainStage,
                "com.maincrab.Rock")) {
            if (rockActor.overlaps(spaceship)) {
                if (spaceship.shieldPower <= 0) {
                    Explosion boom = new Explosion(0, 0, mainStage);
                    explosion.play();
                    spaceshipHit.play();
                    boom.centerAtActor(spaceship);
                    spaceship.remove();
                    spaceship.setPosition(-1000, -1000);
                    gameOver();

                } else {
                    spaceship.shieldPower -= 34;
                    Explosion boom = new Explosion(0, 0, mainStage);
                    boom.centerAtActor(rockActor);
                    spaceshipHit.play();
                    explosion.play();
                    rockActor.setSize(rockActor.getWidth() / 2, rockActor.getHeight() / 2);
                    rockActor.remove();
                }
            }

            // Laser hits rock
            for (BaseActor laserActor : BaseActor.getList(mainStage,
                    "com.maincrab.Laser")) {
                if (laserActor.overlaps(rockActor)) {
                    Explosion boom = new Explosion(0, 0, mainStage);
                    boom.centerAtActor(rockActor);
                    explosion.play();
                    laserActor.remove();
                    rockActor.setSize(rockActor.getWidth() / 2, rockActor.getHeight() / 2);
                    rockActor.hp -= 25;
                    if (rockActor.hp == 0) {
                        rockActor.remove();
                        rockActor.setPosition(-1000, -1000);
                    }
                }

            }
        }

        // Laser hits UFO
        for (BaseActor laserActor : BaseActor.getList(mainStage,
                "com.maincrab.Laser")) {
            if (laserActor.overlaps(ufo) && ufo.getWidth() >= 25) {
                laserActor.remove();
                Explosion boom = new Explosion(0, 0, mainStage);
                ufoHit.play();
                ufo.setSize(ufo.getWidth() - ufoShrinkAmount, ufo.getHeight() - ufoShrinkAmount);
                boom.centerAtActor(laserActor);
                ufo.hp -= 10;
                ufo.setSpeed(ufo.getSpeed() + 75);
            }
        }

        // UFO hits spaceship
        if (ufo.overlaps(spaceship)) {
            Explosion boom = new Explosion(0, 0, mainStage);
            boom.centerAtActor(spaceship);
            spaceship.remove();
            spaceship.setPosition(-1000, -1000);
            gameOver();
        }

        // Winning condition
        if (ufo.hp <= 0 && !gameWin) {
            ufo.remove();
            ufo.setPosition(-1000, -1000);
            gameWin();
        }
    }

    // override default InputProcessor method
    public boolean keyDown(int keycode) {

        if (gameOver && keycode == Keys.SPACE || gameWin && keycode == Keys.SPACE) {
            initialize();
        }

        if (keycode == Keys.Q)
            spaceship.warp();

        if (keycode == Keys.SPACE && spaceship.getSpeed() != 0) {
            spaceship.shoot();
            laser.play();
        }

        if (keycode == Keys.Q && !gameOver)
            warp.play();

        if (keycode == Keys.E) {
            spaceship.setSpeed(0);
            spaceship.setAnimationPaused(true);
        }

        if (keycode == Keys.ESCAPE) {
            Gdx.app.exit();
        }

        return false;
    }

    public void gameOver() {
        if (!gameWin) {
            messageLose = new BaseActor(0, 0, uiStage);
            messageLose.loadTexture("message-lose.png");
            messageLose.centerAtPosition(globW / 2, globH / 2);
            messageLose.setOpacity(0);
//            messageLose.addAction(Actions.fadeIn(1));
            messageLose.addAction(Actions.fadeIn(0));
            gameOver = true;
        }
    }

    public void gameWin() {
        messageWin = new BaseActor(0, 0, uiStage);
        messageWin.loadTexture("message-win.png");
        messageWin.centerAtPosition(globW / 2, globH / 2);
        messageWin.setOpacity(0);
        messageWin.addAction(Actions.fadeIn(0));
        gameWin = true;
    }

    public void clearScreen() {
        for (BaseActor rockActor : BaseActor.getList(mainStage,
                "com.maincrab.Rock")) {
            rockActor.remove();
        }
        if (gameOver) {
            messageLose.addAction(Actions.fadeOut(1));
//            messageLose.remove();
        }
        if (gameWin) {
            messageWin.addAction(Actions.fadeOut(3));
//            messageWin.remove();
        }

        instrumental.dispose();
    }
}
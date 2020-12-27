package com.maincrab;

public class SpaceGame extends BaseGame{

    public void create(){
        super.create();
        setActiveScreen(new LevelScreen() {

            // vet ikke hvorfor men denne ble lagt til etter forslag av Intellij
            @Override
            public boolean scrolled(float amountX, float amountY) {
                return false;
            }
        });
    }
}

package org.andengine.entity.scene.background;

import org.andengine.entity.sprite.Sprite;

public class SpriteBackground extends EntityBackground {
    public SpriteBackground(Sprite pSprite) {
        super(pSprite);
    }

    public SpriteBackground(float pRed, float pGreen, float pBlue, Sprite pSprite) {
        super(pRed, pGreen, pBlue, pSprite);
    }

    public Sprite getSprite() {
        return (Sprite) this.mEntity;
    }
}

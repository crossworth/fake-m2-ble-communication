package org.andengine.entity.particle.initializer;

import org.andengine.entity.IEntity;
import org.andengine.entity.particle.Particle;
import org.andengine.util.color.Color;

public class ColorParticleInitializer<T extends IEntity> extends BaseTripleValueParticleInitializer<T> {
    public ColorParticleInitializer(Color pColor) {
        super(pColor.getRed(), pColor.getRed(), pColor.getGreen(), pColor.getGreen(), pColor.getBlue(), pColor.getBlue());
    }

    public ColorParticleInitializer(float pRed, float pGreen, float pBlue) {
        super(pRed, pRed, pGreen, pGreen, pBlue, pBlue);
    }

    public ColorParticleInitializer(Color pMinColor, Color pMaxColor) {
        super(pMinColor.getRed(), pMaxColor.getRed(), pMinColor.getGreen(), pMaxColor.getGreen(), pMinColor.getBlue(), pMaxColor.getBlue());
    }

    public ColorParticleInitializer(float pMinRed, float pMaxRed, float pMinGreen, float pMaxGreen, float pMinBlue, float pMaxBlue) {
        super(pMinRed, pMaxRed, pMinGreen, pMaxGreen, pMinBlue, pMaxBlue);
    }

    protected void onInitializeParticle(Particle<T> pParticle, float pRed, float pGreen, float pBlue) {
        pParticle.getEntity().setColor(pRed, pGreen, pBlue);
    }
}

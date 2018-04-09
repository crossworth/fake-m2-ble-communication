package org.andengine.entity.modifier;

import org.andengine.entity.modifier.CardinalSplineMoveModifier.CardinalSplineMoveModifierConfig;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.util.modifier.ease.IEaseFunction;

public class CatmullRomSplineMoveModifier extends CardinalSplineMoveModifier {

    public class CatmullRomMoveModifierConfig extends CardinalSplineMoveModifierConfig {
        private static final int CARDINALSPLINE_CATMULLROM_TENSION = 0;

        public CatmullRomMoveModifierConfig(int pControlPointCount) {
            super(pControlPointCount, 0.0f);
        }
    }

    public CatmullRomSplineMoveModifier(float pDuration, CatmullRomMoveModifierConfig pCatmullRomMoveModifierConfig) {
        super(pDuration, pCatmullRomMoveModifierConfig);
    }

    public CatmullRomSplineMoveModifier(float pDuration, CatmullRomMoveModifierConfig pCatmullRomMoveModifierConfig, IEaseFunction pEaseFunction) {
        super(pDuration, (CardinalSplineMoveModifierConfig) pCatmullRomMoveModifierConfig, pEaseFunction);
    }

    public CatmullRomSplineMoveModifier(float pDuration, CatmullRomMoveModifierConfig pCatmullRomMoveModifierConfig, IEntityModifierListener pEntityModifierListener) {
        super(pDuration, (CardinalSplineMoveModifierConfig) pCatmullRomMoveModifierConfig, pEntityModifierListener);
    }

    public CatmullRomSplineMoveModifier(float pDuration, CatmullRomMoveModifierConfig pCatmullRomMoveModifierConfig, IEntityModifierListener pEntityModifierListener, IEaseFunction pEaseFunction) {
        super(pDuration, pCatmullRomMoveModifierConfig, pEntityModifierListener, pEaseFunction);
    }
}

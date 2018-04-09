package org.andengine.entity.particle;

import android.util.FloatMath;
import java.util.ArrayList;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.IEntityFactory;
import org.andengine.entity.particle.emitter.IParticleEmitter;
import org.andengine.entity.particle.initializer.IParticleInitializer;
import org.andengine.entity.particle.modifier.IParticleModifier;
import org.andengine.opengl.util.GLState;
import org.andengine.util.math.MathUtils;

public class ParticleSystem<T extends IEntity> extends Entity {
    private static final float[] POSITION_OFFSET_CONTAINER = new float[2];
    protected final IEntityFactory<T> mEntityFactory;
    protected final IParticleEmitter mParticleEmitter;
    protected final ArrayList<IParticleInitializer<T>> mParticleInitializers;
    protected final ArrayList<IParticleModifier<T>> mParticleModifiers;
    protected final Particle<T>[] mParticles;
    protected int mParticlesAlive;
    private float mParticlesDueToSpawn;
    protected final int mParticlesMaximum;
    private boolean mParticlesSpawnEnabled;
    private final float mRateMaximum;
    private final float mRateMinimum;

    public ParticleSystem(IEntityFactory<T> pEntityFactory, IParticleEmitter pParticleEmitter, float pRateMinimum, float pRateMaximum, int pParticlesMaximum) {
        this(0.0f, 0.0f, pEntityFactory, pParticleEmitter, pRateMinimum, pRateMaximum, pParticlesMaximum);
    }

    public ParticleSystem(float pX, float pY, IEntityFactory<T> pEntityFactory, IParticleEmitter pParticleEmitter, float pRateMinimum, float pRateMaximum, int pParticlesMaximum) {
        super(pX, pY);
        this.mParticleInitializers = new ArrayList();
        this.mParticleModifiers = new ArrayList();
        this.mParticlesSpawnEnabled = true;
        this.mEntityFactory = pEntityFactory;
        this.mParticleEmitter = pParticleEmitter;
        this.mParticles = new Particle[pParticlesMaximum];
        this.mRateMinimum = pRateMinimum;
        this.mRateMaximum = pRateMaximum;
        this.mParticlesMaximum = pParticlesMaximum;
        registerUpdateHandler(this.mParticleEmitter);
    }

    public boolean isParticlesSpawnEnabled() {
        return this.mParticlesSpawnEnabled;
    }

    public void setParticlesSpawnEnabled(boolean pParticlesSpawnEnabled) {
        this.mParticlesSpawnEnabled = pParticlesSpawnEnabled;
    }

    public IEntityFactory<T> getParticleFactory() {
        return this.mEntityFactory;
    }

    public IParticleEmitter getParticleEmitter() {
        return this.mParticleEmitter;
    }

    public void reset() {
        super.reset();
        this.mParticlesDueToSpawn = 0.0f;
        this.mParticlesAlive = 0;
    }

    protected void onManagedDraw(GLState pGLState, Camera pCamera) {
        for (int i = this.mParticlesAlive - 1; i >= 0; i--) {
            this.mParticles[i].onDraw(pGLState, pCamera);
        }
    }

    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);
        if (isParticlesSpawnEnabled()) {
            spawnParticles(pSecondsElapsed);
        }
        int particleModifierCountMinusOne = this.mParticleModifiers.size() - 1;
        for (int i = this.mParticlesAlive - 1; i >= 0; i--) {
            Particle<T> particle = this.mParticles[i];
            for (int j = particleModifierCountMinusOne; j >= 0; j--) {
                ((IParticleModifier) this.mParticleModifiers.get(j)).onUpdateParticle(particle);
            }
            particle.onUpdate(pSecondsElapsed);
            if (particle.mExpired) {
                this.mParticlesAlive--;
                moveParticleToEnd(i);
            }
        }
    }

    protected void moveParticleToEnd(int pIndex) {
        Particle<T> particle = this.mParticles[pIndex];
        int particlesToCopy = this.mParticlesAlive - pIndex;
        if (particlesToCopy > 0) {
            System.arraycopy(this.mParticles, pIndex + 1, this.mParticles, pIndex, particlesToCopy);
        }
        this.mParticles[this.mParticlesAlive] = particle;
    }

    public void addParticleModifier(IParticleModifier<T> pParticleModifier) {
        this.mParticleModifiers.add(pParticleModifier);
    }

    public void removeParticleModifier(IParticleModifier<T> pParticleModifier) {
        this.mParticleModifiers.remove(pParticleModifier);
    }

    public void addParticleInitializer(IParticleInitializer<T> pParticleInitializer) {
        this.mParticleInitializers.add(pParticleInitializer);
    }

    public void removeParticleInitializer(IParticleInitializer<T> pParticleInitializer) {
        this.mParticleInitializers.remove(pParticleInitializer);
    }

    private void spawnParticles(float pSecondsElapsed) {
        this.mParticlesDueToSpawn += determineCurrentRate() * pSecondsElapsed;
        int particlesToSpawnThisFrame = Math.min(this.mParticlesMaximum - this.mParticlesAlive, (int) FloatMath.floor(this.mParticlesDueToSpawn));
        this.mParticlesDueToSpawn -= (float) particlesToSpawnThisFrame;
        for (int i = 0; i < particlesToSpawnThisFrame; i++) {
            spawnParticle();
        }
    }

    private void spawnParticle() {
        if (this.mParticlesAlive < this.mParticlesMaximum) {
            int i;
            Particle<T> particle = this.mParticles[this.mParticlesAlive];
            this.mParticleEmitter.getPositionOffset(POSITION_OFFSET_CONTAINER);
            float x = POSITION_OFFSET_CONTAINER[0];
            float y = POSITION_OFFSET_CONTAINER[1];
            if (particle == null) {
                particle = new Particle();
                this.mParticles[this.mParticlesAlive] = particle;
                particle.setEntity(this.mEntityFactory.create(x, y));
            } else {
                particle.reset();
                particle.getEntity().setPosition(x, y);
            }
            for (i = this.mParticleInitializers.size() - 1; i >= 0; i--) {
                ((IParticleInitializer) this.mParticleInitializers.get(i)).onInitializeParticle(particle);
            }
            for (i = this.mParticleModifiers.size() - 1; i >= 0; i--) {
                ((IParticleModifier) this.mParticleModifiers.get(i)).onInitializeParticle(particle);
            }
            this.mParticlesAlive++;
        }
    }

    protected float determineCurrentRate() {
        if (this.mRateMinimum == this.mRateMaximum) {
            return this.mRateMinimum;
        }
        return MathUtils.random(this.mRateMinimum, this.mRateMaximum);
    }
}

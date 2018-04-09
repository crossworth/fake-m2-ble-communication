package org.andengine.opengl.shader;

import java.util.ArrayList;
import org.andengine.util.debug.Debug;

public class ShaderProgramManager {
    private final ArrayList<ShaderProgram> mShaderProgramsManaged = new ArrayList();

    public synchronized void onCreate() {
        loadShaderProgram(PositionColorTextureCoordinatesShaderProgram.getInstance());
        loadShaderProgram(PositionTextureCoordinatesShaderProgram.getInstance());
        loadShaderProgram(PositionTextureCoordinatesUniformColorShaderProgram.getInstance());
        loadShaderProgram(PositionColorShaderProgram.getInstance());
        loadShaderProgram(PositionTextureCoordinatesTextureSelectShaderProgram.getInstance());
        loadShaderProgram(C2068xbf9afb3a.getInstance());
    }

    public synchronized void onDestroy() {
        ArrayList<ShaderProgram> managedShaderPrograms = this.mShaderProgramsManaged;
        for (int i = managedShaderPrograms.size() - 1; i >= 0; i--) {
            ((ShaderProgram) managedShaderPrograms.get(i)).setCompiled(false);
        }
        this.mShaderProgramsManaged.clear();
    }

    public synchronized void loadShaderProgram(ShaderProgram pShaderProgram) {
        if (pShaderProgram == null) {
            throw new IllegalArgumentException("pShaderProgram must not be null!");
        }
        if (pShaderProgram.isCompiled()) {
            Debug.m4601w("Loading an already compiled " + ShaderProgram.class.getSimpleName() + ": '" + pShaderProgram.getClass().getSimpleName() + "'. '" + pShaderProgram.getClass().getSimpleName() + "' will be recompiled.");
            pShaderProgram.setCompiled(false);
        }
        if (this.mShaderProgramsManaged.contains(pShaderProgram)) {
            Debug.m4601w("Loading an already loaded " + ShaderProgram.class.getSimpleName() + ": '" + pShaderProgram.getClass().getSimpleName() + "'.");
        } else {
            this.mShaderProgramsManaged.add(pShaderProgram);
        }
    }

    public void loadShaderPrograms(ShaderProgram... pShaderPrograms) {
        for (int i = pShaderPrograms.length - 1; i >= 0; i--) {
            loadShaderProgram(pShaderPrograms[i]);
        }
    }

    public synchronized void onReload() {
        ArrayList<ShaderProgram> managedShaderPrograms = this.mShaderProgramsManaged;
        for (int i = managedShaderPrograms.size() - 1; i >= 0; i--) {
            ((ShaderProgram) managedShaderPrograms.get(i)).setCompiled(false);
        }
    }
}

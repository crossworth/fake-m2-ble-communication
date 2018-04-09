package org.andengine.opengl.shader.source;

import org.andengine.opengl.shader.exception.ShaderProgramException;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.util.criteria.IGLCriteria;

public class CriteriaShaderSource implements IShaderSource {
    private final CriteriaShaderSourceEntry[] mCriteriaShaderSourceEntries;

    public static class CriteriaShaderSourceEntry {
        private final IGLCriteria[] mGLCriterias;
        private final String mShaderSource;

        public CriteriaShaderSourceEntry(String pShaderSource) {
            this(pShaderSource, (IGLCriteria[]) null);
        }

        public CriteriaShaderSourceEntry(String pShaderSource, IGLCriteria... pCriterias) {
            this.mGLCriterias = pCriterias;
            this.mShaderSource = pShaderSource;
        }

        public String getShaderSource() {
            return this.mShaderSource;
        }

        public boolean isMet(GLState pGLState) {
            if (this.mGLCriterias != null) {
                for (IGLCriteria gLCriteria : this.mGLCriterias) {
                    if (!gLCriteria.isMet(pGLState)) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    public CriteriaShaderSource(CriteriaShaderSourceEntry... pCriteriaShaderSourceEntries) {
        this.mCriteriaShaderSourceEntries = pCriteriaShaderSourceEntries;
    }

    public String getShaderSource(GLState pGLState) {
        for (CriteriaShaderSourceEntry criteriaShaderSourceEntry : this.mCriteriaShaderSourceEntries) {
            if (criteriaShaderSourceEntry.isMet(pGLState)) {
                return criteriaShaderSourceEntry.getShaderSource();
            }
        }
        throw new ShaderProgramException("No " + CriteriaShaderSourceEntry.class.getSimpleName() + " met!");
    }
}

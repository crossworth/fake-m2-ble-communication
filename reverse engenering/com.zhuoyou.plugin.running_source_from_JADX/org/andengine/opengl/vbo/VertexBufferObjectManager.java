package org.andengine.opengl.vbo;

import java.util.ArrayList;
import org.andengine.opengl.util.GLState;

public class VertexBufferObjectManager {
    private final ArrayList<IVertexBufferObject> mVertexBufferObjectsLoaded = new ArrayList();
    private final ArrayList<IVertexBufferObject> mVertexBufferObjectsToBeUnloaded = new ArrayList();

    public synchronized int getHeapMemoryByteSize() {
        int byteSize;
        byteSize = 0;
        ArrayList<IVertexBufferObject> vertexBufferObjectsLoaded = this.mVertexBufferObjectsLoaded;
        for (int i = vertexBufferObjectsLoaded.size() - 1; i >= 0; i--) {
            byteSize += ((IVertexBufferObject) vertexBufferObjectsLoaded.get(i)).getHeapMemoryByteSize();
        }
        return byteSize;
    }

    public synchronized int getNativeHeapMemoryByteSize() {
        int byteSize;
        byteSize = 0;
        ArrayList<IVertexBufferObject> vertexBufferObjectsLoaded = this.mVertexBufferObjectsLoaded;
        for (int i = vertexBufferObjectsLoaded.size() - 1; i >= 0; i--) {
            byteSize += ((IVertexBufferObject) vertexBufferObjectsLoaded.get(i)).getNativeHeapMemoryByteSize();
        }
        return byteSize;
    }

    public synchronized int getGPUHeapMemoryByteSize() {
        int byteSize;
        byteSize = 0;
        ArrayList<IVertexBufferObject> vertexBufferObjectsLoaded = this.mVertexBufferObjectsLoaded;
        for (int i = vertexBufferObjectsLoaded.size() - 1; i >= 0; i--) {
            byteSize += ((IVertexBufferObject) vertexBufferObjectsLoaded.get(i)).getGPUMemoryByteSize();
        }
        return byteSize;
    }

    public void onCreate() {
    }

    public synchronized void onDestroy() {
        ArrayList<IVertexBufferObject> vertexBufferObjectsLoaded = this.mVertexBufferObjectsLoaded;
        for (int i = vertexBufferObjectsLoaded.size() - 1; i >= 0; i--) {
            ((IVertexBufferObject) vertexBufferObjectsLoaded.get(i)).setNotLoadedToHardware();
        }
        vertexBufferObjectsLoaded.clear();
    }

    public synchronized void onVertexBufferObjectLoaded(IVertexBufferObject pVertexBufferObject) {
        this.mVertexBufferObjectsLoaded.add(pVertexBufferObject);
    }

    public synchronized void onUnloadVertexBufferObject(IVertexBufferObject pVertexBufferObject) {
        if (this.mVertexBufferObjectsLoaded.remove(pVertexBufferObject)) {
            this.mVertexBufferObjectsToBeUnloaded.add(pVertexBufferObject);
        }
    }

    public synchronized void onReload() {
        ArrayList<IVertexBufferObject> vertexBufferObjectsLoaded = this.mVertexBufferObjectsLoaded;
        for (int i = vertexBufferObjectsLoaded.size() - 1; i >= 0; i--) {
            ((IVertexBufferObject) vertexBufferObjectsLoaded.get(i)).setNotLoadedToHardware();
        }
        vertexBufferObjectsLoaded.clear();
    }

    public synchronized void updateVertexBufferObjects(GLState pGLState) {
        ArrayList<IVertexBufferObject> vertexBufferObjectsLoaded = this.mVertexBufferObjectsLoaded;
        ArrayList<IVertexBufferObject> vertexBufferObjectsToBeUnloaded = this.mVertexBufferObjectsToBeUnloaded;
        for (int i = vertexBufferObjectsToBeUnloaded.size() - 1; i >= 0; i--) {
            IVertexBufferObject vertexBufferObjectToBeUnloaded = (IVertexBufferObject) vertexBufferObjectsToBeUnloaded.remove(i);
            if (vertexBufferObjectToBeUnloaded.isLoadedToHardware()) {
                vertexBufferObjectToBeUnloaded.unloadFromHardware(pGLState);
            }
            vertexBufferObjectsLoaded.remove(vertexBufferObjectToBeUnloaded);
        }
    }
}

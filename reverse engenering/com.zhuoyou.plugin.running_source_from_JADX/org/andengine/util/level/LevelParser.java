package org.andengine.util.level;

import java.util.HashMap;
import org.andengine.entity.IEntity;
import org.andengine.util.adt.list.SmartList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class LevelParser extends DefaultHandler {
    private final IEntityLoader mDefaultEntityLoader;
    private final HashMap<String, IEntityLoader> mEntityLoaders;
    private SmartList<IEntity> mParentEntityStack = new SmartList();

    public LevelParser(IEntityLoader pDefaultEntityLoader, HashMap<String, IEntityLoader> pEntityLoaders) {
        this.mDefaultEntityLoader = pDefaultEntityLoader;
        this.mEntityLoaders = pEntityLoaders;
    }

    public void startElement(String pUri, String pLocalName, String pQualifiedName, Attributes pAttributes) throws SAXException {
        IEntity entity;
        String entityName = pLocalName;
        IEntity parent = this.mParentEntityStack.isEmpty() ? null : (IEntity) this.mParentEntityStack.getLast();
        IEntityLoader entityLoader = (IEntityLoader) this.mEntityLoaders.get(entityName);
        if (entityLoader != null) {
            entity = entityLoader.onLoadEntity(entityName, pAttributes);
        } else if (this.mDefaultEntityLoader != null) {
            entity = this.mDefaultEntityLoader.onLoadEntity(entityName, pAttributes);
        } else {
            throw new IllegalArgumentException("Unexpected tag: '" + entityName + "'.");
        }
        if (!(parent == null || entity == null)) {
            parent.attachChild(entity);
        }
        this.mParentEntityStack.addLast(entity);
    }

    public void endElement(String pUri, String pLocalName, String pQualifiedName) throws SAXException {
        this.mParentEntityStack.removeLast();
    }
}

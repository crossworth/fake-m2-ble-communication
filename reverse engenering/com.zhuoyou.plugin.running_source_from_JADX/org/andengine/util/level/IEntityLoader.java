package org.andengine.util.level;

import org.andengine.entity.IEntity;
import org.xml.sax.Attributes;

public interface IEntityLoader {
    IEntity onLoadEntity(String str, Attributes attributes);
}

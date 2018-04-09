package org.andengine.util.adt.io.in;

import java.io.IOException;
import java.io.InputStream;

public interface IInputStreamOpener {
    InputStream open() throws IOException;
}

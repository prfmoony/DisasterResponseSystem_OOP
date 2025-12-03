package src.core;

import java.io.IOException;

public interface Persistable {
    void load() throws IOException;
    void save() throws IOException;
}

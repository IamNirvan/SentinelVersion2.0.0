package com.sentinelv200.core.middlemen;

public interface MiddleMan {
    boolean locateEngine();
    void createEngine();
    void createForm();
    void addMiddleMan();
    void removeMiddleMan();
    void addEngine();
    void removeEngine();
}

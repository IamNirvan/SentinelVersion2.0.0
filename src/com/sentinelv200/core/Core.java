package com.sentinelv200.core;

import com.sentinelv200.engines.Engine;

import java.util.ArrayList;

public class Core {
    private final static ArrayList<Engine> engines = new ArrayList<>();

    public static void main(String[] args) {

    }

    public static void addEngine(Engine engine) {
        engines.add(engine);
    }

    public static void removeEngine(Engine engine) {
        engines.remove(engine);
    }
}

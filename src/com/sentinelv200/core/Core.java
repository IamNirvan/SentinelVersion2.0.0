package com.sentinelv200.core;

import com.sentinelv200.core.middlemen.MiddleMan;
import com.sentinelv200.engines.Engine;

import java.util.ArrayList;

public class Core {
    private final static ArrayList<Engine> engines = new ArrayList<>();
    private final static ArrayList<MiddleMan> middleMen = new ArrayList<>();

    public static void main(String[] args) {

    }

    public static void addMiddleMan(MiddleMan middleMan) {
        middleMen.add(middleMan);
    }

    public static void removeMiddleMan(MiddleMan middleMan) {
        middleMen.remove(middleMan);
    }

    public static void addEngine(Engine engine) {
        engines.add(engine);
    }

    public static void removeEngine(Engine engine) {
        engines.remove(engine);
    }
}

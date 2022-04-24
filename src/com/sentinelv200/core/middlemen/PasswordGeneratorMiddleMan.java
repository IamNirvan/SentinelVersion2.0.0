package com.sentinelv200.core.middlemen;

import com.sentinelv200.core.Core;
import com.sentinelv200.engines.passwordgenerator.PasswordGenerator;

public class PasswordGeneratorMiddleMan implements MiddleMan{
    public PasswordGenerator passwordGenerator;
    boolean connectionStatus = false;

    @Override
    public boolean locateEngine() {
        return false;
    }

    @Override
    public void createEngine() {

    }

    @Override
    public void createForm() {

    }

    @Override
    public void addEngine() {
        Core.addEngine(passwordGenerator);
    }

    @Override
    public void removeEngine() {
        Core.removeEngine(passwordGenerator);
    }
}

package com.sentinelv200.core.middlemen;

import com.sentinelv200.core.Core;
import com.sentinelv200.engines.passwordgenerator.PasswordGenerator;

//TODO: Build this class

public class PasswordGeneratorMiddleMan implements MiddleMan{
    public PasswordGenerator passwordGenerator;
    boolean connectionStatus = false;

    @Override
    public void addEngine() {
        Core.addEngine(passwordGenerator);
    }

    @Override
    public void removeEngine() {
        Core.removeEngine(passwordGenerator);
    }

    @Override
    public void start() {
        // TODO Auto-generated method stub
        
    }
}

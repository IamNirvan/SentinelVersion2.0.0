package com.sentinelv200.core.middlemen;

import com.sentinelv200.core.Core;
import com.sentinelv200.engines.passwordtester.PasswordTester;

public class PasswordTesterMiddleMan implements MiddleMan{
    PasswordTester passwordTester;

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
        Core.addEngine(passwordTester);        
    }

    @Override
    public void removeEngine() {
        Core.removeEngine(passwordTester);        
    }

    public void handleRequest() {
        /*
        This method can be used when the front end requires the password from the generator.        
        */
    }
}

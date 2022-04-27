package com.sentinelv200.core.middlemen;

import com.sentinelv200.core.Core;
import com.sentinelv200.engines.passwordtester.PasswordTester;

public class PasswordTesterMiddleMan implements MiddleMan{
    PasswordTester passwordTester;

    // The event handler for a button or whatever, will invoke this method.
    public void start() {
        addEngine();
    }

    @Override
    public void addEngine() {
        if(this.passwordTester != null) {
            Core.addEngine(passwordTester);        
        }
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

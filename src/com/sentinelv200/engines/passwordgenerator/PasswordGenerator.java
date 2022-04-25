package com.sentinelv200.engines.passwordgenerator;

import com.sentinelv200.engines.Engine;

public interface PasswordGenerator extends Engine {
    byte[] generatePassword();
}

package com.sentinelv200.engines.passwordtester;

import com.sentinelv200.engines.Engine;

import java.util.ArrayList;

public interface PasswordTester extends Engine {
    ArrayList<Boolean> testPassword(byte[] password);
}

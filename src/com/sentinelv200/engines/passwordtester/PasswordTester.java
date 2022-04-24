package com.sentinelv200.engines.passwordtester;

import java.util.ArrayList;
import com.sentinelv200.engines.Engine;

public interface PasswordTester extends Engine{
    ArrayList<Boolean> testPassword();
}

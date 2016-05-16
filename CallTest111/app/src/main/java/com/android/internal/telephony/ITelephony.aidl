// ITelephony.aidl
package com.android.internal.telephony;

// Declare any non-default types here with import statements
import android.os.Bundle;
interface ITelephony {



    boolean endCall();

    void dial(String number);

    void answerRingingCall();








    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}

package com.dtech;

import com.orm.SugarApp;

/**
 * Created by ADIST on 11/19/2015.
 */
public class MetalAppsGlobalClass extends SugarApp {

    public static float currentLattitude;
    public static float currentLongitude;

    public void onCreate(){
        super.onCreate();
        currentLattitude = currentLongitude = (float) 0.0;
    }

    public void onTerminate(){
        super.onTerminate();
    }

    public static float[] getCurrentLatLong(){
        return new float[]{currentLattitude, currentLongitude};
    }
}

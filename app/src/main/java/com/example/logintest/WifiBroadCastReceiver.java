package com.example.logintest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiManager;

import static android.support.v4.content.ContextCompat.createDeviceProtectedStorageContext;
import static android.support.v4.content.ContextCompat.getSystemService;

class WifiBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String action  = intent.getAction();
        if(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION.equals(action)){
            SupplicantState state = intent.getParcelableExtra(WifiManager.EXTRA_NEW_STATE);

            if(SupplicantState.isValidState(state) && state == SupplicantState.COMPLETED){
                    boolean connected = checkConnectedToDesiredWifi();
            }
        }

    }

    private boolean checkConnectedToDesiredWifi() {

        boolean connected=false; //initially we say it is not connected

        String desiredMacAddress = "";

        return  connected;
    }
}

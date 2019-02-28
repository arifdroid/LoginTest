package com.example.logintest;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RegisterMobileActivity extends AppCompatActivity {

    private Button buttonSignUp,buttonPassword;
    private TextView message;

    private String code =null;

    private EditText editTextPhone, editTextCode;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_mobile);

        //instantiate firebase



        buttonPassword = findViewById(R.id.buttonPasswordID);
        buttonSignUp = findViewById(R.id.buttonLogInID);
        message = findViewById(R.id.textViewMesaggeID);

        editTextCode = findViewById(R.id.editTextCodeID);
        editTextPhone = findViewById(R.id.editTextPhoneID);

     //   loggingIn();

        buttonPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            //    Log.i("checkk"," 1");

                startVerificationProcess();
            }
        });

        if(code!=null){
            message.setText("click log in if code entered v2");
        }
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                message.setText("click log in if code entered");
                verifyingCredential(code);

            }
        });

      //  Log.i("checkk"," 3");
        mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

              //  Log.i("checkk"," 3");

                message.setText("getting verification");
                signInWithPhoneCredential(phoneAuthCredential);


            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }


            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                if(s!=null){

                    code = s;

                    message.setText("code received");
                }


            }
        };



    }

    private void verifyingCredential(String firebasesent) {

        String we_enter = editTextCode.getText().toString();

        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(firebasesent,we_enter);

        signInWithPhoneCredential(phoneAuthCredential);

    }

    private void signInWithPhoneCredential(PhoneAuthCredential phoneAuthCredential) {

        message.setText("verifying credential..");

        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){ //here we can enter user into database.

                    loggingIn();
                }

            }
        });

    }

    private void loggingIn() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null){

            message.setText("user is logged in");

            checkWifiStep1();

        }


    }

    private void checkWifiStep1() {

        //BroadcastReceiver broadcastReceiver = new WifiBroadCastReceiver();

        //2nd try

        Log.i("checkk", "ikan");

        WifiManager wifiManager= (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        String name = wifiInfo.getSSID();

        //String macbabyyy = wifiInfo.getMacAddress();

        String bssiddda = wifiInfo.getBSSID();

        //wifiInfo.get

       // Log.i("checkk mac",macbabyyy);
        Log.i("checkk bssid",bssiddda);
        Log.i("checkk ssid",name);

        Toast.makeText(getApplicationContext(),"wifi ini :"+ name, Toast.LENGTH_LONG).show();

        //asking fine location permission

        String constWifiName = "\"arif0527 2.4GHz@unifi\"";

        if(name.equals(constWifiName)){

            Log.i("checkk BERJAYA : ",bssiddda);
            Toast.makeText(getApplicationContext(),"BERJAYA :"+ name, Toast.LENGTH_LONG).show();
            message.setText("BERJAYA!!");
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==1){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){

                Log.i("checkk "," permission granted");

                Toast.makeText(RegisterMobileActivity.this,"permission granted", Toast.LENGTH_SHORT).show();

                checkWifiStep1();
//                List<ScanResult> list = new ArrayList<>();
//
//                WifiManager wifiManager= (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//
//                for(ScanResult result : WifiManager.getScanResults()){
//                }
            }
        }

    }

//    private void getWIfiAgain() {
//
//        List<ScanResult> list = new ArrayList<>();
//
//        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
//        List<ScanResult> wifiInfo = wifiManager.getScanResults();
//     //   WifiInfo wifiInfo2 = wifiManager.getConnectionInfo();
//
//
//        //for(ArrayList<ScanResult> results : wifiInfo)
//
//        Log.i("checkk TEST SIDE ssid: ","here");
//        String nametest = "";
//
//        for(ScanResult result:wifiInfo){
//
//            nametest = result.BSSID;
//
//
//
//            if(nametest!=null){
//
//                Log.i("checkk TEST SIDE ssid: "," kan : "+nametest);
//                break;
//            }
//        }
//
//        //Log.i("checkk TEST SIDE ssid: ",nametest);
//        Log.i("checkk TEST SIDE ssid: ","here version 2");
//        Toast.makeText(RegisterMobileActivity.this,"mac address test: "+nametest , Toast.LENGTH_LONG).show();
//
//
//     //String nametest = "";
////
////        Log.i("checkk mactest",mactest);
////        Log.i("checkk test", nametest);
////
////        Toast.makeText(RegisterMobileActivity.this,"mac address test: "+nametest +" : "+ mactest, Toast.LENGTH_LONG).show();
//
//
//
//
//
//    }

    private void startVerificationProcess() {

        //Log.i("checkk"," 2");

        message.setText("password request sent..");



        PhoneAuthProvider.getInstance().verifyPhoneNumber(editTextPhone.getText().toString(),

                45,
                TimeUnit.SECONDS,
                RegisterMobileActivity.this,
                mCallBack
                );


    }
}

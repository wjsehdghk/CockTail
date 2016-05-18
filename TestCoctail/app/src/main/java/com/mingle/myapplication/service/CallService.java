package com.mingle.myapplication.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by multimedia on 2016-05-16.
 */
public class CallService extends Service {

    TelephonyManager tm = null;
    private AudioManager mAudioManager;
    SmsManager smsManager;
    Boolean call=false;
    @Override
    public void onCreate() {
        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        tm.listen(new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                Log.d("DEBUG", "incomingNumber:" + incomingNumber);
                switch (state) {
                    case TelephonyManager.CALL_STATE_IDLE: //전화가 끊긴 상태
                        mAudioManager.setRingerMode(mAudioManager.RINGER_MODE_VIBRATE);
                        if (call == true) {
                            SendSMS(incomingNumber, "Sorry");
                            call = false;
                        }
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK: //통화중
                        break;
                    case TelephonyManager.CALL_STATE_RINGING: //전화벨 울리는 중
                        call = true;
                        mAudioManager.setRingerMode(mAudioManager.RINGER_MODE_SILENT);
                        break;
                    default: break;
                }
                super.onCallStateChanged(state, incomingNumber);
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);
        return super.onStartCommand(intent, flags, startId);
    }
    private void SendSMS(String phonenumber, String message) {
        smsManager = SmsManager.getDefault();
        String sendTo = phonenumber;
        String myMessage = message;
        smsManager.sendTextMessage(sendTo, null, myMessage, null, null);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

package wjsehdghk.calltest111;

import android.content.Context;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    TelephonyManager tm = null;
    public static final String TAG = "CallStateListner";
    public int mRingModeBackup = 0;
    public boolean mIsReservedMainActivity = false;
    private AudioManager mAudioManager;
    SmsManager smsManager;
    Boolean call=false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        mIsReservedMainActivity = true;
        Log.d(TAG, "CALL_RINGING_BACKUP_MODE mIsReservedMainActivity : " + mIsReservedMainActivity);
        tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        tm.listen(new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                Log.d("DEBUG", "incomingNumber:" + incomingNumber);

                switch(state) {
                    case TelephonyManager.CALL_STATE_IDLE: //전화가 끊긴 상태
                        Log.d(TAG, "CALL_IDLE");
                        mAudioManager.setRingerMode(mAudioManager.RINGER_MODE_VIBRATE);
                        if(call==true){
                            SendSMS(incomingNumber,"Sorry");
                            call=false;
                        }
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK: //통화중
                        Log.d(TAG, "CALL_OFFHOOK");
                        break;
                    case TelephonyManager.CALL_STATE_RINGING: //전화벨 울리는 중
                        call=true;
                        Log.d(TAG, "CALL_RINGING");
                        Log.d(TAG, "RINGING >> ringMode : " + 55);
                        //setRingMode(getRingMode());
                        mAudioManager.setRingerMode(mAudioManager.RINGER_MODE_SILENT);

                        Log.d(TAG, "RINGING >> Incoming number : " + incomingNumber);


                        break;
                }

                super.onCallStateChanged(state, incomingNumber);
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);
    }
    private void SendSMS(String phonenumber, String message) {
        smsManager = SmsManager.getDefault();
        String sendTo = phonenumber;
        String myMessage = message;
        smsManager.sendTextMessage(sendTo, null, myMessage, null, null);
    }
    @Override
     protected void onResume() {
        // TODO Auto-generated method stub
        GetRingerMode();
        SetStartReservedMain(mIsReservedMainActivity);
        super.onResume();
    }
    public void GetRingerMode(){
        mRingModeBackup = mAudioManager.getRingerMode();
    }
    public void SetStartReservedMain(boolean set){
        Log.d(TAG, "SetStartReservedMain : "+set);
        CallStateListener startReservedMain = new CallStateListener(this);
        startReservedMain.setIsReservedMainActivity(set);
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "CALL_RINGING_BACKUP_MODE 22 : " + mRingModeBackup);
        mAudioManager.setRingerMode(mRingModeBackup);
        mIsReservedMainActivity = false;
        SetStartReservedMain(mIsReservedMainActivity);
        super.onDestroy();
    }

}

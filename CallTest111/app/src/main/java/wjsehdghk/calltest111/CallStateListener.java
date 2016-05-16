package wjsehdghk.calltest111;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.media.AudioManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.internal.telephony.ITelephony;
import java.lang.reflect.Method;
import java.util.List;
public class CallStateListener extends PhoneStateListener {
	private Context         mContext;
	private AudioManager    audioManager;
	public static final String TAG          = "CallStateListner";
	private static boolean     mIsStartMain = false;
	public CallStateListener(Context context) {
		mContext = context;
		audioManager = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
	}
	public void setIsReservedMainActivity(boolean setIs) {
		Log.d(TAG, "setIsReservedMainActivity() setIs : " + setIs);
		mIsStartMain = setIs;
	}
	@Override
	public void onCallStateChanged(int state, String incomingNumber) {
		Log.d(TAG, "onCallStateChanged() mIsStartMain : "+mIsStartMain);
		if(mIsStartMain)
		{
			try{
				Log.d(TAG, "onCallStateChanged() END");

				TelephonyManager mTelephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
				Class<?> mClass = Class.forName(mTelephonyManager.getClass().getName());
				Method mMethod = mClass.getDeclaredMethod("getITelephony");
				mMethod.setAccessible(true);
				ITelephony mITelephony = (ITelephony) mMethod.invoke(mTelephonyManager);
				mITelephony.endCall();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

			return;
		}

		switch(state) {
			case TelephonyManager.CALL_STATE_IDLE:
				Log.d(TAG,"CALL_IDLE");
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				Log.d(TAG, "CALL_OFFHOOK");
				break;
			case TelephonyManager.CALL_STATE_RINGING:
				Log.d(TAG, "CALL_RINGING");
				//Log.d(TAG, "RINGING >> ringMode : " + getRingMode());
			//	setRingMode(getRingMode());
				//Log.d(TAG, "RINGING >> Incoming number : " + incomingNumber);
				break;
		}

		super.onCallStateChanged(state, incomingNumber);
	}
}

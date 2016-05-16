package wjsehdghk.calltest111;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
public class CallStateReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(CallStateListener.TAG, "CallStateReceiver >>>>>>> onReceive");
			CallStateListener callStateListner = new CallStateListener(context);
			TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
			telephony.listen(callStateListner, PhoneStateListener.LISTEN_CALL_STATE);
		}
	}

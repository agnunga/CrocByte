package ke.co.rahisisha.crocbyte.phone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import java.lang.reflect.Method;
import com.android.internal.telephony.ITelephony;
import android.telephony.TelephonyManager;

public class CrocCallReceiver extends BroadcastReceiver
{
    String TEST_NUMBER = "0702 719701";
    private static final String TAG = "AG";
    @Override
    public void onReceive(Context context, Intent intent) {
        ITelephony telephonyService = getTeleService(context);
        if(telephonyService!=null) {
            try {
                String numberCall = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Log.i(TAG, "CALL NUM: " + numberCall);
                if(numberCall.equals(TEST_NUMBER)) {
                    telephonyService.silenceRinger();
                    telephonyService.answerRingingCall();
                    telephonyService.endCall();
                    Toast.makeText(context, "END CALL: " + numberCall, Toast.LENGTH_LONG).show();
                    Log.i(TAG, "END CALL");
                } else {
                    Log.i(TAG, "ACCEPT CALL");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.i(TAG, "CATCH CALL");
            }
        }
    }

    private ITelephony getTeleService(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        try {
            Class c = Class.forName("android.telephony.TelephonyManager");
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            return (ITelephony) m.invoke(tm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

package ke.co.rahisisha.crocbyte.phone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by agunga on 5/27/17.
 */
import java.lang.reflect.Method;
import com.android.internal.telephony.ITelephony;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;
public class CrocCallReceiver extends BroadcastReceiver
{
    Context context = null;
    private static final String TAG = "THIRI THE WUT YEE";
    private ITelephony telephonyService;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.v(TAG, "Receving....");
        TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        try
        {
            Class c = Class.forName(telephony.getClass().getName());
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            telephonyService = (ITelephony) m.invoke(telephony);

            Toast tag = Toast.makeText(context, "Call is not allowed in the meeting!!!", Toast.LENGTH_LONG);
            tag.setDuration(25);
            tag.show();


            telephonyService.silenceRinger();
            telephonyService.endCall();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


}

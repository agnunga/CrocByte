package ke.co.rahisisha.crocbyte.messanger;

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

public class CrocMessageReceiver extends BroadcastReceiver {

    String TAG = "CROC LOG";

    public void onReceive(Context context, Intent intent) {
        Bundle pudsBundle = intent.getExtras();
        Object[] pdus = (Object[]) pudsBundle.get("pdus");
        SmsMessage messages = SmsMessage.createFromPdu((byte[]) pdus[0]);
        Log.i(TAG, messages.getMessageBody());
        if (messages.getMessageBody().contains("Hi")) {
            abortBroadcast();
            Toast.makeText(context, "Message Received", Toast.LENGTH_LONG).show();
        }
    }
}

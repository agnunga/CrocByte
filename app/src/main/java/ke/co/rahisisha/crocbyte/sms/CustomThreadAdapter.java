package ke.co.rahisisha.crocbyte.sms;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ke.co.rahisisha.crocbyte.R;
import ke.co.rahisisha.crocbyte.crocContacts.MyContact;

/**
 * Created by agunga on 6/2/17.
 */

public class CustomThreadAdapter extends ArrayAdapter<MyMessage> {

    public CustomThreadAdapter(Context context, ArrayList<MyMessage> data) {
        super(context, R.layout.fragment_received_msg, data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MyMessage myMessage = getItem(position);
        if(myMessage.getType()== MyMessage.SmsType.RECEIVED){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.fragment_received_msg, parent, false);

            TextView textViewRecMsg  = (TextView) view.findViewById(R.id.textViewRecMsg);
            TextView   message_time  = (TextView) view.findViewById(R.id.message_time);
            TextView   message_phone  = (TextView) view.findViewById(R.id.message_phone);

            MyContact myContact = myMessage.getPhone();
            List<String> phones = myContact.getPhone_no();


            textViewRecMsg.setText(myMessage.getTextMessage());
            message_time.setText(myMessage.getTime());
            message_phone.setText(phones.get(0));

            return view;
        }else {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.fragment_sent_msg, parent, false);

            TextView textViewSentMsg  = (TextView) view.findViewById(R.id.textViewSentMsg);
            TextView   message_time  = (TextView) view.findViewById(R.id.message_time);
            TextView   message_phone  = (TextView) view.findViewById(R.id.message_phone);

            MyContact myContact = myMessage.getPhone();
            List<String> phones = myContact.getPhone_no();


            textViewSentMsg.setText(myMessage.getTextMessage());
            message_time.setText(myMessage.getTime());
            message_phone.setText(phones.get(0));
            return view;
        }
    }
}

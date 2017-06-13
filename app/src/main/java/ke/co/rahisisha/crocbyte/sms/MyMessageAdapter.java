package ke.co.rahisisha.crocbyte.sms;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ke.co.rahisisha.crocbyte.R;

/**
 * Created by agunga on 6/1/17.
 */

public class MyMessageAdapter extends ArrayAdapter<MyMessage> {
    public MyMessageAdapter(Context context, ArrayList<MyMessage> myMessages) {
        super(context, R.layout.croc_message_list, myMessages);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.croc_message_list, parent, false);
        MyMessage message  = getItem(position);

        ImageView profile_entry = (ImageView) customView.findViewById(R.id.profile_entry);
        TextView time_entry = (TextView) customView.findViewById(R.id.time_entry);
        TextView name_entry = (TextView) customView.findViewById(R.id.name_entry);
        TextView message_entry = (TextView) customView.findViewById(R.id.message_entry);

        profile_entry.setImageResource(R.drawable.an_agunga);
        time_entry.setText(message.getTime());
        String phone = message.getPhone_no();
        if(getContactName(getContext(), phone)!=null)
            phone = getContactName(getContext(), phone)+" ("+phone+")";
        name_entry.setText(phone);
        message_entry.setText(message.getTextMessage());
        return customView;
    }


    public String getContactName(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri,
                new String[] { ContactsContract.PhoneLookup.DISPLAY_NAME }, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = null;
        if (cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return contactName;
    }
}

package ke.co.rahisisha.crocbyte.sms;

import android.content.Context;
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
        name_entry.setText(message.getPhone().getName()+" ("+message.getPhone().getPhone_no()+")");
        message_entry.setText(message.getTextMessage());
        return customView;
    }
}

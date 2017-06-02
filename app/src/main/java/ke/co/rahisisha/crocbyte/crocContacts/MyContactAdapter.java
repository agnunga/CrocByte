package ke.co.rahisisha.crocbyte.crocContacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import ke.co.rahisisha.crocbyte.R;

/**
 * Created by agunga on 6/1/17.
 */

public class MyContactAdapter extends BaseAdapter {

    private ArrayList<MyContact> myContacts = new ArrayList<>();
    private HashSet<MyContact> contacts_no = new HashSet<>();
    private Context mContext;
    private static LayoutInflater inflater = null;

    public MyContactAdapter(Context context, ArrayList<MyContact> contacts) {
        myContacts=contacts;
        contacts_no.addAll(myContacts);
        myContacts.clear();
        myContacts.addAll(contacts_no);
        mContext=context;
        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return myContacts.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(convertView==null){
            view= inflater.inflate(R.layout.croc_contact_list, null);
            ImageView profile_pic = (ImageView) view.findViewById(R.id.profile_pic);
            TextView name_entry = (TextView) view.findViewById(R.id.name_entry);
            TextView number_entry = (TextView) view.findViewById(R.id.number_entry);
            ImageView source_pic = (ImageView) view.findViewById(R.id.source_pic);

            MyContact myContact;
            myContact =  myContacts.get(position);
            String phone_nos = "";
            List<String> phones = myContact.getPhone_no();
            for (String phone :phones){
                phone_nos+=phone+" ; ";
            }
            profile_pic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.an_agunga));
            name_entry.setText(myContact.getName());
            number_entry.setText(phone_nos);
            source_pic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.time_passing));
        }
        return view;
    }
}

package ke.co.rahisisha.crocbyte.sms;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import ke.co.rahisisha.crocbyte.R;
import ke.co.rahisisha.crocbyte.crocContacts.MyContact;

public class CrocSmsLanding extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_croc_sms_landing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ArrayList<MyMessage> messages = new ArrayList<>();
        System.out.println("XXXXXXXXXXXXXXXX"+getNumbers());

        for (String number : getNumbers()){
            messages .add(lastSms(number));
        }

        final ListView listView = (ListView) findViewById(R.id.croc_sms_landing);
        MyMessageAdapter myMessageAdapter = new MyMessageAdapter(this, messages);
        listView.setAdapter(myMessageAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(view, "Item: "+listView, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                MyMessage myMessage = (MyMessage) parent.getAdapter().getItem(position);
                String phone_no = myMessage.getPhone_no();
                Intent intent = new Intent(getApplicationContext(), CrocSmsActivity.class);
                intent.putExtra("phone_no", phone_no);
                startActivity(intent);
            }
        });

    }
//    public HashSet<ThreadCount> getLastSms(){
//        Cursor cursor = this.getContentResolver().query(Uri.parse( "content://mms-sms/conversations?simple=true"), null, null, null, "normalized_date desc" );
////        Cursor cursor = getContentResolver().query
////                (Uri.parse ("content://mms-sms/conversations"),
////                        null, null, null, "date DESC");
//
//        while (cursor.moveToNext())
//        {
//            long key = cursor.getLong (cursor.getColumnIndex ("_id"));
//            long threadId = cursor.getLong (cursor.getColumnIndex ("thread_id"));
//            String address = cursor.getString (cursor.getColumnIndex ("address")); // phone #
//            long date = cursor.getLong (cursor.getColumnIndex ("date"));
//            String body = cursor.getString (cursor.getColumnIndex ("body"));
//
//
//        }
//
//        cursor.close();
//    }

    public HashSet<String> getNumbers() {
        HashSet<String> numbers = new HashSet<>();

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(Uri.parse("content://sms"), null, null, null, "date ASC");

        int indexAddress = cursor.getColumnIndex("address");
        while (cursor.moveToNext()){
            String phone = cursor.getString(indexAddress);
//            phone = phone.replace("+", "");
//            phone = phone.replaceAll("\\s","");
//            phone = phone.replace("254", "0");
            numbers.add(phone);
        }
        return numbers;
    }

    public MyMessage lastSms(String number){
        MyMessage myMessage = new MyMessage();
        Uri uri = Uri.parse("content://sms");
        String phoneNumber = number;

        Cursor cursor1 = getContentResolver().query(uri,
                null,
                "address = ?",
                new String[] {phoneNumber},
                "date DESC LIMIT 1");

        if (cursor1 != null && cursor1.moveToFirst()) {
            String body = cursor1.getString(cursor1.getColumnIndex("body"));
            if(body.length()>39){
                body = body.substring(0, 38)+" ...";
            }
            myMessage.setTextMessage(body);
            myMessage.setPhone_no(number);
            myMessage.setTime(cursor1.getString(cursor1.getColumnIndex("date")));
        }
        return myMessage;
    }

}

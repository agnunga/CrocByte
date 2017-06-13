package ke.co.rahisisha.crocbyte.crocContacts;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ke.co.rahisisha.crocbyte.R;
import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class ReadContacts extends AppCompatActivity {

    private ListView contacts_listView;
    private ProgressDialog progressDialog;
    private Handler updateBarHandler;
    Cursor cursor;
    int counter;
    ArrayList<MyContact> contactsList = new ArrayList<>();
    MyContactAdapter myContactAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_contacts);
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

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Reading contacts...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        updateBarHandler =new Handler();
        contacts_listView = (ListView) findViewById(R.id.croc_contact_list);
        // Since reading contacts takes more time, let's run it on a separate thread.
        new Thread(new Runnable() {
            @Override
            public void run() {
                contactsList = findAllContacts();
                loadToAdapter();
            }
        }).start();
        myContactAdapter = new MyContactAdapter(this, contactsList);
        // Set onClickListener to the list item.
        contacts_listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "Clicked : \n"+ contactsList.get(position),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void loadToAdapter() {
            // ListView has to be updated using a ui thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    contacts_listView.setAdapter(myContactAdapter);
                }
            });
            // Dismiss the progressbar after 100 millisecondds
            updateBarHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.cancel();
                }
            }, 100);
        }
   public ArrayList<MyContact> findAllContacts() {
       MyContact myContact = null;
        String phone_nos = "";
       String emails = "";

       Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
       String _ID = ContactsContract.Contacts._ID;
       String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
       String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
       Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
       String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
       String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
       Uri EmailCONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
       String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
       String DATA = ContactsContract.CommonDataKinds.Email.DATA;
       ContentResolver contentResolver = getContentResolver();
       cursor = getContentResolver().query(
               CONTENT_URI,
               null, null, null,
               ContactsContract.Contacts.SORT_KEY_PRIMARY + " ASC");

       // Iterate every contact in the phone
       if (cursor.getCount() > 0) {
           counter = 0;
           while (cursor.moveToNext()) {
               myContact = new MyContact();
               phone_nos = "";
               emails = "";
               // Update the progress message
               updateBarHandler.post(new Runnable() {
                   public void run() {
                       progressDialog.setMessage("Reading contacts : " + counter++ + "/" + cursor.getCount());
                   }
               });
               String contact_id = cursor.getString(cursor.getColumnIndex(_ID));

               myContact.setName(cursor.getString(cursor.getColumnIndex(DISPLAY_NAME)));
               int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));
               if (hasPhoneNumber > 0) {
                   Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[]{contact_id}, null);
                   while (phoneCursor.moveToNext()) {
                       String phone = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                       phone_nos+=phone+"; ";
                   }
                   myContact.setPhone_no(phone_nos);
                   phoneCursor.close();
                   // Read every email id associated with the contact
                   Cursor emailCursor = contentResolver.query(EmailCONTENT_URI, null, EmailCONTACT_ID + " = ?", new String[]{contact_id}, null);
                   while (emailCursor.moveToNext()) {
                       String email = emailCursor.getString(emailCursor.getColumnIndex(DATA));
                       emails+=email+"; ";
                   }
                   myContact.setEmail(emails);
                   emailCursor.close();
               }
               // Add the contact to the ArrayList
               contactsList.add(myContact);
           }
       }
       return contactsList;
   }
}


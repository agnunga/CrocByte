package ke.co.rahisisha.crocbyte.sms;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ke.co.rahisisha.crocbyte.R;
import ke.co.rahisisha.crocbyte.crocContacts.MyContact;

public class CrocSmsLanding extends AppCompatActivity {
    ArrayList<MyMessage> messages = new ArrayList<>();

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
        final ListView listView = (ListView) findViewById(R.id.croc_sms_landing);

        MyContact myContact = new MyContact();
        myContact.setName("Oloo");
        List<String> phones = new ArrayList<>();
        phones.add("0712929181");
        myContact.setPhone_no(phones);

        MyMessage myMessage = new MyMessage();
        myMessage.setPhone(myContact);
        myMessage.setTextMessage("Hello");
        myMessage.setTime("12:30");

        messages.add(myMessage);

        MyMessageAdapter myMessageAdapter = new MyMessageAdapter(this, messages);
        listView.setAdapter(myMessageAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(view, "Item: "+listView.getId(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    public ArrayList<MyMessage> getAllSms(){

        return null;
    }

}

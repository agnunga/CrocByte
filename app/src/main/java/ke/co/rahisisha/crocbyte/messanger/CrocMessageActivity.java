package ke.co.rahisisha.crocbyte.messanger;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import ke.co.rahisisha.crocbyte.R;
import layout.Received_msg;
import layout.Sent_msg;

public class CrocMessageActivity extends AppCompatActivity implements View.OnClickListener,
        layout.Received_msg.OnFragmentInteractionListener
        , Sent_msg.OnFragmentInteractionListener, GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener{

    ImageButton send_button;
    EditText typed_msg;

    TextView textViewRecMsg;
    TextView textViewSentMsg;

    static final String TAG = "MyLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "In onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_croc_message);

        send_button = (ImageButton) findViewById(R.id.send_btn);
        typed_msg = (EditText)findViewById(R.id.typed_msg);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.send_btn:{

            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.i(TAG, "In onFragmentInteraction");

    }

    @Override
    protected void onPostResume() {
        Log.i(TAG, "In onPostResume");
        super.onPostResume();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "In onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "In onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        Log.i(TAG, "In onMenuOpened");
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "In onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "In onResume");
        super.onResume();
    }

    @Override
    protected void onResumeFragments() {
        Log.i(TAG, "In onResumeFragments");
        super.onResumeFragments();
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, "In onRestart");
        super.onRestart();
    }

    public boolean sendMessage(View view){


        System.out.println("Starting Agunga: Connetoin RUN");

        String textMsg = typed_msg.getText().toString();
        if (textMsg.length() > 0) {
            System.out.println("send_button clicked: " + textMsg);
            typed_msg.setText("");

            Toast.makeText(CrocMessageActivity.this, "Message sending...", Toast.LENGTH_SHORT).show();

            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();

            Received_msg received_msg = new Received_msg();
            Sent_msg sent_msg = new Sent_msg();

            LinearLayout lScroll = (LinearLayout) findViewById(R.id.linearScroll);
            RelativeLayout fragment_rec = (RelativeLayout) findViewById(R.id.my_send_layout);
            RelativeLayout fragment_send = (RelativeLayout) findViewById(R.id.my_receive_layout);

            textViewRecMsg = (TextView) findViewById(R.id.textViewRecMsg);//textViewSentMsg
            textViewSentMsg = (TextView) findViewById(R.id.textViewSentMsg);//
//            textViewRecMsg.setText(textMsg);
            textViewSentMsg.setText(textMsg);

//            lScroll.addView(fragment_rec);
//            lScroll.addView(fragment_send);

//            fragmentTransaction.add(R.id.holder, received_msg);
            fragmentTransaction.add(R.id.holder2, sent_msg);
            fragmentTransaction.commit();
        }



        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Agunga: Connetoin RUN");

                String host = "192.168.1.121";
                try
                {
                    Socket socket = new Socket(host, 8081);
                    System.out.println("Agunga: Connetoin A");
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                    out.print("Hello Connected");

                    out.flush();
                } catch (IOException e) {
                    System.out.println("Agunga: EX");
                    e.printStackTrace();
                }
            }
        }).start();
        return true;
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {

        Toast.makeText(CrocMessageActivity.this, "onSingleTapConfirmed", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "In onSingleTapConfirmed");

        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Toast.makeText(CrocMessageActivity.this, "onDoubleTap", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "In onDoubleTap");
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Toast.makeText(CrocMessageActivity.this, "onDoubleTapEvent", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "In onDoubleTapEvent");
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Toast.makeText(CrocMessageActivity.this, "onDown", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "In onDown");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Toast.makeText(CrocMessageActivity.this, "onShowPress", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "In onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Toast.makeText(CrocMessageActivity.this, "onSingleTapUp", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "In onSingleTapUp");
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Toast.makeText(CrocMessageActivity.this, "onScroll", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "In onScroll");
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

        Log.i(TAG, "In onLongPress");
        Toast.makeText(CrocMessageActivity.this, "onLongPress", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        Toast.makeText(CrocMessageActivity.this, "onFling", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "In onFling");
        return true;
    }
}

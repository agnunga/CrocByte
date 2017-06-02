package ke.co.rahisisha.crocbyte.qr;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ke.co.rahisisha.crocbyte.R;

import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class QRViewerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {


    ImageLoader imageLoader;
    ImageView ivQr;
    String copiedStr;
    TextView etQr;
    Button btnMakeQR;
    ClipboardManager clipboardManager;
    ImageButton imgButton;
    private static boolean newScann = true;

    String BASE_QR_URL = "https://chart.googleapis.com/chart?chs=300x300&cht=qr&chld=M&choe=UTF-8&chl=";
    String fullurl = BASE_QR_URL;
    private ZXingScannerView mScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrviewer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crocScanQR();
            }
        });
        btnMakeQR = (Button) findViewById(R.id.btnMakeQR);
        btnMakeQR.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                crocGenerateQRCode();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void crocScanQR(){
        releaseCamScanner();
        mScannerView=new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        Log.i("OLOO", "This run 15000 milliseconds later");
                        if(newScann) {
                            newScann = false;
                            Toast.makeText(QRViewerActivity.this, "Scanning time out.",Toast.LENGTH_LONG).show();
                            releaseCamScanner();
                            qrResultsAlert(true, "");
                        }
                    }
                }, 15000);

    }

    public void crocGenerateQRCode(){
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.qrProgressBar1);
        progressBar.setVisibility(ProgressBar.VISIBLE);

        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(this).build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(imageLoaderConfiguration);
        ivQr = (ImageView)findViewById(R.id.ivQr);
        etQr=(EditText)findViewById(R.id.etQr);

        if(etQr.getText().toString().length()>0){

            copiedStr =etQr.getText().toString();
            try {
                fullurl += URLEncoder.encode(copiedStr, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                qrCreationAlert("Error: ");
            }finally {
            }
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            Log.i("OLOO", "Loading image after 2 seconds.");
                            imageLoader.displayImage(fullurl, ivQr);
                            progressBar.setVisibility(ProgressBar.INVISIBLE);
                        }
                    }, 2000);
        }else {
            Toast.makeText(QRViewerActivity.this, "Test area or clipboard must have content.", Toast.LENGTH_LONG)
                    .show();
            progressBar.setVisibility(ProgressBar.INVISIBLE);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        releaseCamScanner();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseCamScanner();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseCamScanner();
    }

    private void releaseCamScanner(){
        if(mScannerView!=null) {
            mScannerView.stopCamera();
        }
    }

    @Override
    public void handleResult(Result result) {
        releaseCamScanner();
        String qr_content = result.getText().toString();
        Log.v("Handle Results", result.getText());
        qrResultsAlert(false, qr_content);
        //resume scanning
        mScannerView.resumeCameraPreview(this);
    }
    public void qrCreationAlert(String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Error!");
        builder.setCancelable(false);
        builder.setMessage(message+"An unexpected error occured! Check your cinnectivity and try again.");

        // Set up the buttons
        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                crocGenerateQRCode();
            }
        });
        builder.show();
        }

    public void qrResultsAlert(final Boolean isTimeOut, String qr_content){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(isTimeOut?"Time Out!":"QR Code Content");
        builder.setCancelable(false);
        builder.setMessage(isTimeOut?"Scanning taking longer than expected.":qr_content);

        // Set up the buttons
        builder.setPositiveButton(isTimeOut?"Quit":"Copy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(QRViewerActivity.this, isTimeOut?"Time Out!":"Content copied to clipboard.", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        builder.setNegativeButton(isTimeOut?"Try again":"Continue scanning", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newScann=true;
                crocScanQR();
//                dialog.cancel();
            }
        });
        builder.show();
    }
}


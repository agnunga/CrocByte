package ke.co.rahisisha.crocbyte.messanger;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;

import ke.co.rahisisha.crocbyte.R;
import ke.co.rahisisha.crocbyte.messanger.BubbleDrawable;

/**
 * Created by agunga on 4/13/17.
 */

public class DynamicActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);
        BubbleDrawable myBubble = new BubbleDrawable(BubbleDrawable.CENTER);
        myBubble.setCornerRadius(20);
        myBubble.setPointerAlignment(BubbleDrawable.RIGHT);
        myBubble.setPadding(25, 25, 25, 25);
        for(int i=0; i<10;i++){
            setBackground(myBubble);
        }
    }

    public void setBackground(Drawable background) {
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.myLayout);
        linearLayout.setBackgroundDrawable(background);
    }
}

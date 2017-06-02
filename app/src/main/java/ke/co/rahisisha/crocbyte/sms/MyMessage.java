package ke.co.rahisisha.crocbyte.sms;

import java.util.List;

import ke.co.rahisisha.crocbyte.crocContacts.MyContact;

/**
 * Created by agunga on 6/1/17.
 */

public class MyMessage {
    private MyContact phone;
    private String  textMessage;
    private String time;
    private SmsType type;

    public SmsType getType() {
        return type;
    }

    public void setType(SmsType type) {
        this.type = type;
    }

    public MyContact getPhone() {
        return phone;
    }

    public void setPhone(MyContact phone) {
        this.phone = phone;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    enum SmsType{
        RECEIVED,
        SENT
    }
}

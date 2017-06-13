package ke.co.rahisisha.crocbyte.sms;

import ke.co.rahisisha.crocbyte.crocContacts.MyContact;

/**
 * Created by agunga on 6/1/17.
 */

public class MyMessage {
    private String phone_no;
    private String  textMessage;
    private String time;
    private SmsType type;

    public SmsType getType() {
        return type;
    }

    public void setType(SmsType type) {
        this.type = type;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
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

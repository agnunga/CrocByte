package ke.co.rahisisha.crocbyte.crocContacts;

import java.util.List;

/**
 * Created by agunga on 6/1/17.
 */

public class MyContact {
    private String name;
    private List<String> phone_no;
    private List<String> email;

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(List<String> phone_no) {
        this.phone_no = phone_no;
    }
}

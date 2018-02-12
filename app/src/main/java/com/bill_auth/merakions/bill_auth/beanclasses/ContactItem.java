package com.bill_auth.merakions.bill_auth.beanclasses;

/**
 * Created by Hardik on 2/12/2018.
 */

public class ContactItem {

    String contactname, getContactaddress;

    public ContactItem(String contactname, String contactaddress) {
        this.contactname = contactname;
        this.getContactaddress = contactaddress;
    }

    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname;
    }

    public String getGetContactaddress() {
        return getContactaddress;
    }

    public void setGetContactaddress(String getContactaddress) {
        this.getContactaddress = getContactaddress;
    }
}

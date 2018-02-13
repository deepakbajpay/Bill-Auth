package com.bill_auth.merakions.bill_auth.beanclasses;

/**
 * Created by Hardik on 2/11/2018.
 */

public class BillItem {
    public BillItem() {

    }

    public BillItem(String billnumber, String billdate, Boolean verify) {
        this.billnumber = billnumber;
        this.billUrl = billdate;
        this.verify = verify;

    }

    String billnumber;
    String billUrl;
    Boolean verify;
    long timestamp;

    public String getSenderUid() {
        return senderUid;
    }

    public void setSenderUid(String senderUid) {
        this.senderUid = senderUid;
    }

    public String getReceiverUid() {
        return receiverUid;
    }

    public void setReceiverUid(String receiverUid) {
        this.receiverUid = receiverUid;
    }

    String senderUid;
    String receiverUid;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


    public String getBillnumber() {
        return billnumber;
    }

    public void setBillnumber(String billnumber) {
        this.billnumber = billnumber;
    }

    public String getBillUrl() {
        return billUrl;
    }

    public void setBillUrl(String billdate) {
        this.billUrl = billdate;
    }

    public Boolean getVerify() {
        return verify;
    }

    public void setVerify(Boolean verify) {
        this.verify = verify;
    }



}

package com.bill_auth.merakions.bill_auth.beanclasses;

/**
 * Created by Hardik on 2/11/2018.
 */

public class BillItem {
    public BillItem(String billnumber, String billdate, Boolean verify) {
        this.billnumber = billnumber;
        this.billdate = billdate;
        this.verify = verify;
    }

    String billnumber;
    String billdate;
    Boolean verify;

    public String getBillnumber() {
        return billnumber;
    }

    public void setBillnumber(String billnumber) {
        this.billnumber = billnumber;
    }

    public String getBilldate() {
        return billdate;
    }

    public void setBilldate(String billdate) {
        this.billdate = billdate;
    }

    public Boolean getVerify() {
        return verify;
    }

    public void setVerify(Boolean verify) {
        this.verify = verify;
    }



}

package com.medavarsity.user.medavarsity.Global;

public class GlobalProps {
    private static GlobalProps instance = null;

    public String userProfile;
    public String userName;
    public String userEmail;
    public String userContact;
    public String fbId;

    protected GlobalProps(){}

    public static synchronized GlobalProps getInstance() {
        if(null == instance){
            instance = new GlobalProps();
        }
        return instance;
    }
}


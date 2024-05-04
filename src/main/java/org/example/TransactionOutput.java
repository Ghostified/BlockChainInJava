package org.example;
import  java.security.PublicKey;

public class TransactionOutput {
    public  String id;
    public  PublicKey reciepient; //new owner of the coins
    public float value; //amount of coins owned
    public String parentTransactionID; //the id of the transaction this output was created in

    //Constructor
    public TransactionOutput(PublicKey reciepient, float value, String parentTransactionID) {
        this.reciepient = reciepient;
        this.value = value;
        this.parentTransactionID = parentTransactionID;
        this.id = StringUtil.applySha256(StringUtil.getStringFromKey(reciepient) + Float.toString(value) + parentTransactionID);
    }

    //Check if the coins belong to you
    public  boolean isMine (PublicKey publicKey){
        return (publicKey ==  reciepient);
    }
}

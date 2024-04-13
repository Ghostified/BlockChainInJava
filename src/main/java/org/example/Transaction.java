package org.example;
import java.security.*;
import java.util.*;
/*
In a blockchain , each transaction contains:
public key of the sender
public key of the receiver
Value to be transferred
Inputs - references to previous transactions that prove the sender has funds to send
Output - the amount relevant addresses received in the transaction
Cryptographic signature  - Proves the owner of the address is the one sending the transaction and data has not been changed

 */
public class Transaction {

    //Hash of the transaction
    public String transactionID;

    //Senders address public key
    public PublicKey sender;

    //Receivers public key/ address
    public PublicKey reciepient;

    //amount
    public float value;

    //Signature to prevent access from a third party and transaction security
    public byte [] signature;

    public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
    public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

    //A rough count of how many transactions have been generated
    private static int sequence = 0;

    //Constructor
    public Transaction ( PublicKey from, PublicKey to, float value, ArrayList<TransactionInput> inputs) {
        this.sender = from;
        this.reciepient = to;
        this.value = value;
        this.inputs = inputs;
    }

    //This method calculates the transaction hash that will be used as the  transaction ID
    private String calculateHash () {
         //increase the sequence to avoid two identical transactions having the same hash
        sequence ++;
        return  StringUtil.applySha256(
                StringUtil.getStringFromKey(sender) +
                        StringUtil.getStringFromKey(reciepient) +
                        Float.toString(value)
        );
    }
}

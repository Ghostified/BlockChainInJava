package org.example;
import java.security.*;
import java.util.*;
import java.util.ArrayList;
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
    public PublicKey recipient;

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
        this.recipient = to;
        this.value = value;
        this.inputs = inputs;
    }



    //utilizing the signature by appending the generateSignature and verifySignatureMethods
    //This method signs all the data not to be tampered with
    public void generateSignature (PrivateKey privateKey) {
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(recipient) + Float.toString(value);
        signature = StringUtil.applyECDSASig(privateKey, data);
    }

    //Verifies that  a transaction has been signed and not tampered
    public boolean verifySignature  () {
        String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(recipient)  + Float.toString(value) ;
        return StringUtil.verifyECDSASig(sender , data, signature);
    }

    //This method calculates the transaction hash that will be used as the  transaction ID
    private String calculateHash () {
        //increase the sequence to avoid two identical transactions having the same hash
        sequence ++;
        return  StringUtil.applySha256(
                StringUtil.getStringFromKey(sender)
                        + StringUtil.getStringFromKey(recipient) +
                        Float.toString(value)
        );
    }

    //returns true id if new Transactions could be created
    public boolean processTransaction() {
        if (verifySignature() == false ) {
            System.out.println("Transaction signature failed to verify");
            return  false;
        }

        //gather transactions input(making sure they are unspent
        for (TransactionInput i : inputs) {
            i.UTXO = NoobChain.UTXOs.get(i.transactionOutputID);
        }

        //Check if the transaction is valid
        if (getInputsValue() < NoobChain.minimumTransaction) {
            System.out.println("Transaction Inputs too small" + getInputsValue ());
            return  false;
        }

        //generate transaction outputs
        float leftOver = getInputsValue () - value; //get the value of inputs then the left over change
        transactionID = calculateHash();
        outputs.add(new TransactionOutput(this.recipient, value,transactionID)); //send value to reciepent
        outputs.add(new TransactionOutput( this .sender, leftOver, transactionID)); // send the leftover change back to sender

        //add outputs to unspent list
        for (TransactionOutput o : outputs) {
            NoobChain.UTXOs.put(o.id, o);
        }

        //remove transaction inputs from UTXO list as spent
        for (TransactionInput i : inputs) {
            if (i.UTXO == null) continue; //if transaction cant be found skip
            NoobChain.UTXOs.remove(i.UTXO.id);
        }
        return false;
    }

    //returns sum of inputs (UTXO's) values
    public float getInputsValue() {
        float total = 0;
        for (TransactionInput i : inputs) {
            if (i.UTXO == null) continue; //if transaction can not be found skip it
            total += i.UTXO.value;
        }
        return  total;
    }

    //returns the sum of outputs
    public float getOutputsValue () {
        float total = 0;
        for (TransactionOutput o : outputs){
            total += o.value;
        }
        return  total;
    }
}

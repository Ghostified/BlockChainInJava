package org.example;

public class TransactionInput {
    public String transactionOutputID; //Reference to transactionOutputs -> transactionID
    public TransactionOutput UTXO; //contains the unspent transaction  output

    public TransactionInput(String transactionOutputID){
        this.transactionOutputID = transactionOutputID;
    }
}

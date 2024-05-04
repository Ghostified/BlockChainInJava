package org.example;

import java.util.ArrayList;
import java.util.Date;

/*
A blockchain is a list of Blocks
Each block contains its own digital fingerprint , contain fingerprint of the previous block and have some data
Fingerprint = Hash
Each block has its own hash and in part a hash of the previous block
If the data of the previous hash is changed , calculated in part by the data , it affects all chains in the block
Meaning: changing data in this list, will change the signature of the entire block

 */
public class Block {
    public String hash;
    public String previousHash; //hold the previous block hash
    public  String merkleRoot;
    public ArrayList <Transaction> transactions = new ArrayList<Transaction>(); //our data will be a simple message
    private long timeStamp; //as a number in milliseconds
    int nonce;

    //Block Constructor
    public Block (String previousHash) {
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime ();
        //adding the calculatedHash Method
        this.hash = calculateHash();
    }

    //Applying the applySha256 helper to calculate the Hash
    // The Hash must be calculated from all the parts of the Block ie previous hash, data and timestamp

    public String calculateHash () {
        String calculatedHash = StringUtil.applySha256(
                previousHash +
                        Long.toString(timeStamp) +
                        Integer.toString(nonce) +
                        merkleRoot
        );
        return calculatedHash;
    }

    public void mineBlock (int difficulty ) {
        merkleRoot = StringUtil.getMerkleRoot(transactions);
        String target = StringUtil.getDificultyString(difficulty); //create string with difficulty 0
        while (!hash.substring(0,difficulty).equals(target)) {
            nonce++;
            hash =calculateHash();
        }
        System.out.println("Block Mined !! " + hash);
    }

    //Add transaction to this block
    public boolean addTransaction (Transaction transaction){
        //process transaction chgeck if valid , unless block is genesis block then ignore
        if (transaction == null) return  false;
        if ((previousHash != "0")) {
            if ((transaction.processTransaction() != true)) {
                System.out.println("Transaction failed to process . Discartded");
                return  false;
            }
        }
        transactions.add(transaction);
        System.out.println("Transaction successfully added to block");
        return true;
    }
}

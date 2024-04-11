package org.example;

import java.util.Date;

/*
A blockchain is a list of Blocks
Each block contains its own digital fingerprint , contain fingerprint of the previous block and have some data
Fingerprint = Hash
Each block has its own hash and in part a hash of the previous block
If the data of the previous hash is changed , calculated in part by the data , it affects all chains in the block
Meaning: changing data in this list, will change the signaturee of the entire block

 */
public class Block {
    public String hash;
    public String previousHash; //hold the previous block hash
    private String data; //Our data will be a simple message of the block data
    private long timeStamp; //as a number in milliseconds

    //Block Constructor
    public Block (String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime ();
    }
}

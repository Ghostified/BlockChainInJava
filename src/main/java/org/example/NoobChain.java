package org.example;

import java.security.*;
import java.util.*;
import java.util.ArrayList;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class NoobChain {

    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    //list of all transactions
    public static  HashMap<String, TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>();
    public static int difficulty = 0;
    public static Wallet walletA;
    public static Wallet walletB;
    public static void main(String[] args) {
        //set up Bouncey castle as security provider
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        //Create wallets
        walletA = new Wallet();
        walletB = new Wallet();

        //test public and private keys
        System.out.println("Private and Public Keys");
        System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
        System.out.println(StringUtil.getStringFromKey(walletA.privateKey));

        //Create a test transaction from walletA to walletB
        Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 0, null);
        transaction.generateSignature(walletA.privateKey);

        //verify the signature works and verify it from public key
        System.out.println("Is signature verified: ");
        System.out.println(transaction.verifySignature());
    }
}
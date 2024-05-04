package org.example;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
//coin ownership is transferred in the blockchain as a transaction
//Participants can have an address to transfer coins
//Each wallet has a private and public key

public class Wallet {

    //Creating a wallet which stores the public and private keys
    //Private keys are for signing transactions
    //Public keys are the wallet address
    //Public keys are sent with a transaction
    //these keys will be generated in pairs
    public PrivateKey privateKey;
    public PublicKey publicKey;

    public HashMap <String , TransactionOutput> UTXOs = new HashMap<String , TransactionOutput>();

    //generating key pairs for the wallet
    public Wallet () {
        generateKeyPair ();
    }

    public void generateKeyPair () {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");

            //Initialize key generator  and generate key pair
            keyGen.initialize(ecSpec, random);
            KeyPair keyPair = keyGen.generateKeyPair();

            //Set the public and private keys from the key pair
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();

        } catch (Exception e) {
            throw  new RuntimeException(e);
        }
    }

    //returns balance and stores the UTXOs owned by this wallet in this.UTXO's
    public float getBalance() {
     float total = 0;
     for (Map.Entry <String , TransactionOutput> item : NoobChain.UTXOs.entrySet()) {
         TransactionOutput UTXO  = item.getValue();
         if (UTXO.isMine(publicKey)) { //if output belongs to me (if coins belong to me )
             UTXOs.put(UTXO.id,UTXO); //add to our list of unspent transactions
             total += UTXO.value;
         }
     }
     return  total;
    }

    //Generates and returns a new transaction from this wallet
    public Transaction sendFunds(PublicKey _recipient, float value) {
        if (getBalance() < value) { //gather balance and checjk funds
            System.out.println("Not enough funds to send transactions. Transaction Discarded");
            return null;
        }

        //Create Array List of inputs
        ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
        float total = 0;
        for (Map.Entry<String, TransactionOutput> item : UTXOs.entrySet()) {
            TransactionOutput UTXO = item.getValue();
            total += UTXO.value;
            inputs.add(new TransactionInput(UTXO.id));
            if (total > value) break;
        }

        Transaction newTransaction = new Transaction(publicKey, _recipient, value, inputs);
        newTransaction.generateSignature(privateKey);

        for (TransactionInput input : inputs) {
            UTXOs.remove(input.transactionOutputID);
        }
        return newTransaction;
    }

}

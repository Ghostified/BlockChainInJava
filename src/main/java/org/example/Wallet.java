package org.example;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
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

}

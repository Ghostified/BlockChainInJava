package org.example;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficulty = 0;
    public static void main(String[] args) {

        // Create a first Block , the genesisBlock
        //Since it is the first block we enter 0 for the value of the previous hash
        blockchain.add(new Block("Hi Iam the first Block", "0"));
        System.out.println("Trying to mine block one....");
        blockchain.get(0).mineBlock(difficulty);


        blockchain.add(new Block("yo am the second block" , blockchain.get(blockchain.size() -1).hash));
        System.out.println("Trying to mine block 2 ....");
        blockchain.get(1).mineBlock(difficulty);


        blockchain.add(new Block("Hey am the third block", blockchain.get(blockchain.size()-1).hash));
        System.out.println("Trying to mine block 3...");
        blockchain.get(2).mineBlock(difficulty);

        System.out.println("\nBlockchain is valid: " + isChainValid() );

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println("\n The Block chain : ");
        System.out.println(blockchainJson);
    }

    //This method loops through all the blocks in the chain and compares the hashes
    //It will check if the hash variable is  equal to the calculated hash
    //It will check if the previous blocks hash is equal to the previousHash variable
    public static Boolean isChainValid () {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0','0');

        //Loop through blockchain to check hashes
        for (int i = 1; i <blockchain.size(); i++){
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i -1);

            //Compare registered hash and calculated hash
            if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
                System.out.println("Current hash not equals");
                return false;
            }

            //compare previous hash and registered previous hash
            if (!previousBlock.hash.equals(currentBlock.previousHash)) {
                System.out.println("Previous hashes not equal");
                return false;
            }

            //check if hash is solved
            if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)){
                System.out.println("This block has been mined");
                return false;
            }
        }
        return true;
    }
}
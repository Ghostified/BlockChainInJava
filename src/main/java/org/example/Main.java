package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Create a first Block , the genesisBlock since
        //Since its the first block we enter 0 for the value of the previous hash

        Block genesisBlock = new Block("Hello , I am the first Block ", "0");
        System.out.println("Hah For Block 1: " + genesisBlock.hash);

        Block secondBlock = new Block ("Yo, am the second Block  ", genesisBlock.hash);
        System.out.println("Hash for Block 2: " + secondBlock.hash);

        Block thirdBlock = new Block("Hey am the third Block ", secondBlock.hash);
        System.out.println("Hash for Block 3: " + thirdBlock.hash);
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankingapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author Streck
 */
public class BankingApp {

  private static HashMap<String, Accounts> accounts;

  /**
   * @param args the command line arguments
   */
  
  //When we first load app, creates the list of accounts and brings up accounts
  //from the loads method
  public static void initBankApp() {
    accounts = new HashMap<String, Accounts>();
    loadAllAccounts();

  }
  //Saves all accounts to a text file
  public static void saveAllAccounts() {
    String toWrite = "";
    try {
      for (Accounts acc : accounts.values()) {
        BufferedWriter buf = new BufferedWriter(new FileWriter("storedaccounts.txt",false));
        toWrite += acc.getUserName() + " " + acc.getPassword() + " ";
        toWrite += String.format("%.2f", acc.getAccountBalance());
        toWrite += "\n";
        buf.write(toWrite);
        buf.close();
      }
    } catch (IOException ex) {
      System.out.println("Write error");
    }
  }
  //Creates a new account with username and password
  public static void createNewAccount(String un, String pw) {
    Accounts account = new Accounts(un, pw);
    accounts.put(un, account);
  }
  //reads all accounts from the text file and adds them into the hashmap
  public static void credentialFeedback(int check){
    
  }
  public static void loadAllAccounts() {
    try {
      BufferedReader read = new BufferedReader(new FileReader("storedaccounts.txt"));
      String line;
      String[] lines = new String[3];
      while ((line = read.readLine()) != null) {
        lines = line.split(" ");
        String userWrite = lines[0];
        String pwWrite = lines[1];
        float amtWrite = Float.parseFloat(lines[2]);
        createNewAccount(userWrite, pwWrite);
        selectAccount(userWrite).setAccountBalance(amtWrite);
      }
    } catch (IOException ex) {
      System.out.println("Read error");
    }
  }
  //gets the account from the hashmap
  public static Accounts selectAccount(String un) {
    return accounts.get(un);
  }
  //checks to see if the username and password match
  //returns 0 if success, 1 if correct UN and incorrect PW
  //2 if no user found
  public static short checkCredentials(String un, String pw) {
    
    if (accounts.containsKey(un)) {
      Accounts account = accounts.get(un);
      if (account.getPassword().equals(pw)) {
        System.out.println("Login Success");
        return 0;
      }
      System.out.println("Incorrect Password");
      return 1;
    }
    return 2;
  }

  public static void mainLogic(String un, String pw) {
    // TODO code application logic here
    initBankApp();
    Scanner scan = new Scanner(System.in);
    Scanner numScan = new Scanner(System.in);
    boolean run = true;
    
    //start of application requesting login 
    //whole loop to keep getting credentials until the correct credentials are
    //found
    short check = checkCredentials(un, pw);
    credentialFeedback(check);
    while (check != 0) {
      if (check == 1) {
        System.out.print("Please Enter new password: ");
        pw = scan.nextLine();
        scan.reset();
        check = checkCredentials(un, pw);
      } else {
        System.out.println("No User found, would you like to make a new account?");
        String newAct = scan.nextLine();
        scan.reset();
        if (newAct.equals("yes")) {
          createNewAccount(un, pw);
          break;
        }

        if (newAct.equals("no")) {
          System.out.println("Please re-enter username and password");
          System.out.print("Username: ");
          un = scan.nextLine();
          scan.reset();
          System.out.println("Password: ");
          pw = scan.nextLine();
          scan.reset();
        }
      }
    }
    Accounts account = selectAccount(un);
    //main logic where you can do bank functions
    while (run) {
      ATM atm = new ATM();
      System.out.println("What would you like to do? Withdraw, Deposit, Check Balance, Exit");
      String command = scan.nextLine();
      switch (command) {
        case "Withdraw":
          System.out.println("Please Enter an amount in dollars and cents");
          float withdrawAmount = numScan.nextFloat();
          withdrawAmount = atm.makeWithdrawal(withdrawAmount, account);
          System.out.printf("Your new balance is: %.2f\n", withdrawAmount);
          break;
        case "Deposit":
          System.out.println("Please Enter an amount in dollars and cents");
          float depositAmount = numScan.nextFloat();
          depositAmount = atm.makeDeposit(depositAmount, account);
          System.out.printf("Your new balance is: %.2f\n", depositAmount);
          break;
        case "Check Balance":
          float balanceAmount = atm.checkBalance(account);
          System.out.printf("Your balance is: %.2f\n", balanceAmount);
          break;
        case "Exit":
          run = false;
          System.out.println("Have a nice day");

      }

    }
    saveAllAccounts();
  }

}

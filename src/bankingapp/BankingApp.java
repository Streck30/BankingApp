/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankingapp;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Streck
 */
public class BankingApp {

  private static ArrayList<Accounts> accounts;
  /**
   * @param args the command line arguments
   */
  public static void initBankApp(){
    accounts = new ArrayList<Accounts>();
    loadAllAccounts();
    
  }
  public static void createNewAccount(String un, String pw){
    Accounts account = new Accounts(un, pw);
    accounts.add(account);
  }
  public static short checkCredentials(String un, String pw){
    for(int i = 0; i < accounts.size();i++){
      if(un.equals(accounts.get(i).getUserName())){
        if(pw.equals(accounts.get(i).getPassword())){
          System.out.println("Login Success");
          return 0;
        }
        System.out.println("Incorrect Password");
        return 1;
      }
    }
    System.out.println("No User with those credentials");
    return 2;
  }
  public static void main(String[] args) {
    // TODO code application logic here
    initBankApp();
    Scanner scan = new Scanner(System.in);
    boolean run = true;
    System.out.println("Welcome to Bank Account App");
    System.out.print("User Name: ");
    String un = scan.nextLine();
    scan.reset();
    System.out.print("Password: ");
    String pw = scan.nextLine();
    scan.reset();
    short check = checkCredentials(un, pw);
    while(check!= 0){
      if(check == 1){
        System.out.print("Please Enter new password: ");
        pw = scan.nextLine();
        scan.reset();
        check = checkCredentials(un, pw);
      }
      else{
        System.out.println("No User found, would you like to make a new account?");
        String newAct = scan.nextLine();
        scan.reset();
        if(newAct.equals("yes"))
          createNewAccount(un, pw);
        
        if(newAct.equals("no")){
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
    while(run){
      
    }
  }
  
}

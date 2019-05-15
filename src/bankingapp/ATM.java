/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankingapp;

/**
 *
 * @author Streck
 */
public class ATM {
  public static float makeWithdrawal(float amount, Accounts account){
    if(amount > account.getAccountBalance()){
      System.out.println("Insufficient Funds");
      return 0;
    }
    account.setAccountBalance(account.getAccountBalance()-amount);
    return account.getAccountBalance();
  }
  public static float makeDeposit(float amount, Accounts account){
    account.setAccountBalance(account.getAccountBalance()+amount);
    return account.getAccountBalance();
  }
  public static float checkBalance(Accounts account){
    return account.getAccountBalance();
  }
}
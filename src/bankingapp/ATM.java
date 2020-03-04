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
      return 0;
    }
    if(amount == 0){
      return -1;
    }
    account.setAccountBalance(account.getAccountBalance()-amount);
    return amount;
  }
  public static float makeDeposit(float amount, Accounts account){
    if(amount ==0)
      return -1;
    account.setAccountBalance(account.getAccountBalance()+amount);
    return amount;
  }
  public static float checkBalance(Accounts account){
    return account.getAccountBalance();
  }
}

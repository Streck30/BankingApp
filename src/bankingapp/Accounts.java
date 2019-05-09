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
public class Accounts {
  private int AccountNum;
  private String UserName;
  private String Password;
  private float AccountBalance;
  private static int numAccounts;
  
  public float getAccountBalance(){
    return AccountBalance;
  }
  public void setAccountBalance(float newBalance){
    AccountBalance = newBalance;
  }
  public String getUserName(){
    return UserName;
  }
  public String getPassword(){
    return Password;
  }
  public void setUserName(String newUserName){
    UserName = newUserName;
  }
  public void setPassword(String newPassword){
    Password = newPassword;
  }
          
}

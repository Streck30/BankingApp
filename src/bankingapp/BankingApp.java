/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankingapp;

import java.util.ArrayList;

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
  public static void main(String[] args) {
    // TODO code application logic here
    initBankApp();
  }
  
}

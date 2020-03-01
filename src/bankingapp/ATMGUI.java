/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankingapp;

import java.awt.*;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.awt.event.ActionListener;

/**
 *
 * @author Streck
 */
public class ATMGUI extends Application {

  @Override
  public void start(Stage primaryStage) {
    //setting up login screen
    GridPane pane = new GridPane();
    pane.setAlignment(Pos.CENTER);
    pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
    pane.setHgap(5.5);
    pane.setVgap(5.5);
    pane.add(userNameLabel, 0, 0);
    pane.add(userNameField, 1, 0);
    pane.add(passwordLabel, 0, 1);
    pane.add(passwordField, 1, 1);
    pane.add(login, 2, 1);
    LoginButtonHandlerClass loginHandler = new LoginButtonHandlerClass();
    login.setOnAction(loginHandler);
    Scene scene = new Scene(pane);
    primaryStage.setTitle("Login");
    primaryStage.setScene(scene);
    primaryStage.show();

    GridPane pane2 = new GridPane();
    toAddSub.setEditable(false);
    pane2.setAlignment(Pos.CENTER);
    pane2.setPadding(new Insets(10, 10, 10, 10));
    pane2.add(bankAmountField, 0, 1);
    pane2.add(toAddSub, 1, 1);
    pane2.add(AccountNumberField, 0, 0);
    for (int i = 0; i < buttonText.length; i++) {
      buttons[i] = new Button(buttonText[i]);
    }
    for(int i = 0; i < buttons.length;i++){
      buttons[i].setOnAction(loginHandler);
    }
    pane2.add(buttons[10], 2, 1);
    for (int i = 0; i < buttonText.length - 5; i++) {
      pane2.add(buttons[i], i % 3, i / 3 + 2);
    }
    pane2.add(buttons[9],1,5);
    for (int i = 0; i < 3; i++) {
      pane2.add(buttons[i + 11], i, 6);
    }
    Scene scene2 = new Scene(pane2);
    bankFunctionStage.setScene(scene2);

  }

  public void setNewStage() {

    bankAmountField.setText(String.format("Balance: $%.2f", currentAccount.getAccountBalance()));
    AccountNumberField.setText(String.format("Account Number: %06d", currentAccount.getAccountNumber()));

    bankFunctionStage.show();

  }

  @Override
  public void stop() {
    bank.saveAllAccounts();
  }

  //all areas for the initial login page
  private TextField userNameField = new TextField();
  private PasswordField passwordField = new PasswordField();
  private Label userNameLabel = new Label("username");
  private Label passwordLabel = new Label("password");
  private Button login = new Button("Login");
  private static final BankingApp bank = new BankingApp();
  private Accounts currentAccount;
  private ATM atm;

  //all areas for the bank function page
  private Stage bankFunctionStage = new Stage();
  private Label bankAmountField = new Label("");
  private Label AccountNumberField = new Label("");
  private Panel buttonPanel = new Panel();
  private TextField toAddSub = new TextField("");
  private static final String[] buttonText = {"7", "8", "9", "4", "5", "6", "1", "2", "3", "0", "Del", "Withdraw", "Deposit", "Log Out"};
  private Button[] buttons = new Button[buttonText.length];

  public static void main(String[] args) {
    bank.initBankApp();
    Application.launch(args);

  }

  class LoginButtonHandlerClass implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent e) {
      if (e.getSource() == login) {
        String un = userNameField.getText();
        String pw = passwordField.getText();
        short check = bank.checkCredentials(un, pw);
        switch (check) {
          case 0:
            //credentials correct
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Login Success");
            currentAccount = bank.selectAccount(un);           
            setNewStage();
            userNameField.setText("");
            passwordField.setText("");
            alert.show();
            break;
          case 1:
            //password incorrect
            Alert alert2 = new Alert(AlertType.ERROR);
            alert2.setTitle(null);
            alert2.setHeaderText(null);
            alert2.setContentText("Incorrect Password");
            alert2.showAndWait();
            passwordField.setText("");
            break;
          case 2:
            //no user found
            Alert alert3 = new Alert(AlertType.CONFIRMATION);
            alert3.setTitle(null);
            alert3.setHeaderText(null);
            alert3.setContentText("No user found. \nWould you like to make a new account?");
            Optional<ButtonType> result = alert3.showAndWait();
            if (result.get() == ButtonType.OK) {
              bank.createNewAccount(un, pw);
              Alert alert4 = new Alert(AlertType.INFORMATION);
              alert4.setTitle(null);
              alert4.setHeaderText(null);
              alert4.setContentText("Account Creation Successful");
              alert4.showAndWait();
              currentAccount = bank.selectAccount(un);
              setNewStage();
              userNameField.setText("");
              passwordField.setText("");
            }
        }
      }
      //all the button actions for the numbers 0-9
      for (int buttonNums = 0; buttonNums < 10; buttonNums++) {
        if (e.getSource() == buttons[buttonNums]) {
          toAddSub.setText(toAddSub.getText() + buttonText[buttonNums]);
        }
      }
      //the button actions for the delete button
      if (e.getSource() == buttons[10]) {
        if(!toAddSub.getText().equals("")){
        toAddSub.setText(toAddSub.getText().substring(0, toAddSub.getText().length() - 1));
        }
      }
      //the button action for the withdrawal button
      if(e.getSource() == buttons[11]){
        if(toAddSub.getText().equals("")){
          Alert noNumberAlert = new Alert(AlertType.ERROR);
          noNumberAlert.setTitle(null);
          noNumberAlert.setHeaderText(null);
          noNumberAlert.setContentText("Please put a number to Withdraw");
          noNumberAlert.showAndWait();
          return;
        }
        float atmCheck = atm.makeWithdrawal(Float.parseFloat(toAddSub.getText()),currentAccount );
        toAddSub.setText("");
        if(atmCheck == 0){
          Alert WithdrawAlertEmpty = new Alert(AlertType.ERROR);
          WithdrawAlertEmpty.setTitle(null);
          WithdrawAlertEmpty.setHeaderText(null);
          WithdrawAlertEmpty.setContentText("Insufficient funds");
          WithdrawAlertEmpty.showAndWait();
        }
        else{
          Alert WithdrawAlert = new Alert(AlertType.INFORMATION);
          WithdrawAlert.setTitle(null);
          WithdrawAlert.setHeaderText(null);
          WithdrawAlert.setContentText(String.format("Successfully Withdrew: $%.2f", atmCheck));
          WithdrawAlert.showAndWait();
          bankAmountField.setText(String.format("Balance: $%.2f", currentAccount.getAccountBalance()));
          
        }
          
      }
      //the button action for the deposit button
      if(e.getSource() == buttons[12]){
        if(toAddSub.getText().equals("")){
          Alert noNumberAlert = new Alert(AlertType.ERROR);
          noNumberAlert.setTitle(null);
          noNumberAlert.setHeaderText(null);
          noNumberAlert.setContentText("Please put a number to Deposit");
          noNumberAlert.showAndWait();
          return;
        }
        float depositCheck = atm.makeDeposit(Float.parseFloat(toAddSub.getText()),currentAccount );
        toAddSub.setText("");
        
        Alert DepositAlert = new Alert(AlertType.INFORMATION);
        DepositAlert.setTitle(null);
        DepositAlert.setHeaderText(null);
        DepositAlert.setContentText(String.format("Successfully Deposited $%.2f", depositCheck));
        DepositAlert.showAndWait();
        bankAmountField.setText(String.format("Balance: $%.2f", currentAccount.getAccountBalance()));
        
      }
      if(e.getSource()==buttons[13]){
        Alert logoutAlert = new Alert(AlertType.INFORMATION);
        logoutAlert.setTitle(null);
        logoutAlert.setHeaderText(null);
        logoutAlert.setContentText("Logout Successful");
        logoutAlert.showAndWait();
        bankFunctionStage.hide();
      }

    }

  }

}

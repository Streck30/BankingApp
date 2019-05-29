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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Streck
 */
public class ATMGUI extends Application{
  @Override
  public void start(Stage primaryStage){
    //setting up login screen
    GridPane pane = new GridPane();
    pane.setAlignment(Pos.CENTER);
    pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
    pane.setHgap(5.5);
    pane.setVgap(5.5);
    pane.add(userNameLabel,0,0);
    pane.add(userNameField, 1,0);
    pane.add(passwordLabel,0,1);
    pane.add(passwordField,1,1);
    pane.add(login,2,1);
    LoginButtonHandlerClass loginHandler = new LoginButtonHandlerClass();
    login.setOnAction(loginHandler);
    Scene scene = new Scene(pane);
    primaryStage.setTitle("Login");
    primaryStage.setScene(scene);
    primaryStage.show();
    bankAmountField.setEditable(false);
    
    
    
    
  }
  @Override
  public void stop(){
    bank.saveAllAccounts();
  }
  //all areas for the initial login page
  private TextField userNameField = new TextField();
  private PasswordField passwordField = new PasswordField();
  private Label userNameLabel = new Label("username");
  private Label passwordLabel = new Label("password");
  private Button login = new Button("Login");
  private static final BankingApp bank = new BankingApp();
  
  //all areas for the bank function page
  private Stage bankFunctionStage = new Stage(); 
  private TextField bankAmountField = new TextField();
  
  
  public static void main(String [] args){
    bank.initBankApp();
    Application.launch(args);
    
  }
  class LoginButtonHandlerClass implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent e){
      String un = userNameField.getText();
      String pw = passwordField.getText();
      short check = bank.checkCredentials(un, pw);
      switch(check){
        case 0: 
          //credentials correct
          Alert alert = new Alert(AlertType.INFORMATION);
          alert.setTitle(null);
          alert.setHeaderText(null);
          alert.setContentText("Login Success");
          alert.show();
          Accounts currentAccount = bank.selectAccount(un);
          break;
        case 1:
          //password incorrect
          Alert alert2 = new Alert(AlertType.ERROR);
          alert2.setTitle(null);
          alert2.setHeaderText(null);
          alert2.setContentText("Incorrect Password");
          alert2.showAndWait();
          break;
        case 2:
          //no user found
          Alert alert3 = new Alert(AlertType.CONFIRMATION);
          alert3.setTitle(null);
          alert3.setHeaderText(null);
          alert3.setContentText("No user found. \nWould you like to make a new account?");
          Optional<ButtonType> result = alert3.showAndWait();
          if(result.get() == ButtonType.OK){
            bank.createNewAccount(un,pw);
            Alert alert4 = new Alert(AlertType.INFORMATION);
            alert4.setTitle(null);
            alert4.setHeaderText(null);
            alert4.setContentText("Account Creation Successful");
            alert4.showAndWait();
          }
      }
    }
    
  }
  
  
}

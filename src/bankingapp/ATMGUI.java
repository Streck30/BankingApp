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
    
  }
  private TextField userNameField = new TextField();
  private PasswordField passwordField = new PasswordField();
  private Label userNameLabel = new Label("username");
  private Label passwordLabel = new Label("password");
  private Button login = new Button("Login");
  public static void main(String [] args){
    Application.launch(args);
    
  }
  class LoginButtonHandlerClass implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent e){
      String un = userNameField.getText();
      String pw = passwordField.getText();
      BankingApp bank = new BankingApp();
      short check = bank.mainLogic(un, pw);
      switch(check){
        case 0: 
          Alert alert = new Alert(AlertType.INFORMATION);
          alert.setTitle("Success");
          alert.setHeaderText(null);
          alert.setContentText("Login Success");
          alert.showAndWait();
          break;
        case 1:
          Alert alert2 = new Alert(AlertType.INFORMATION);
          alert2.setTitle("Password");
          alert2.setHeaderText(null);
          alert2.setContentText("Incorrect Password");
          alert2.showAndWait();
          break;
        case 2:
          Alert alert3 = new Alert(AlertType.CONFIRMATION);
          alert3.setTitle("Username");
          alert3.setHeaderText(null);
          alert3.setContentText("No user found. \nWould you like to make a new account?");
          Optional<ButtonType> result = alert3.showAndWait();
          if(result.get() == ButtonType.OK)
            bank.createNewAccount(un,pw);
          else{
            
          }
      }
    }
    
  }
  
  
}

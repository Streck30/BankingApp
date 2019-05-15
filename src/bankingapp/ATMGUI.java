/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bankingapp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Streck
 */
public class ATMGUI extends JFrame implements ActionListener {
  private TextField userName = new TextField();
  private JPasswordField password = new JPasswordField();
  private JLabel userNameLabel = new JLabel("username");
  private JLabel passwordLabel = new JLabel("password");
  private JPanel panel = new JPanel(new GridLayout(2,1));
  private JButton login = new JButton("Login");
  public static void main(String [] args){
    ATMGUI atm = new ATMGUI();
  }
  public ATMGUI(){
    setSize(300,100);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    add(panel, BorderLayout.CENTER);
    panel.add(userNameLabel);
    panel.add(userName);
    panel.add(passwordLabel);
    panel.add(password);
    login.setSize(25, 25);
    add(login, BorderLayout.PAGE_END);
    login.addActionListener(this);
    setVisible(true);
  }
  public void actionPerformed(ActionEvent e) {
    String un = userName.getText();
    String pw = password.getText();
    BankingApp bank = new BankingApp();
    bank.mainLogic(un, pw);
    
  }
  
}

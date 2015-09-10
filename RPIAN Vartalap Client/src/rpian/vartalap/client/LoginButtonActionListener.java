/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpian.vartalap.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 *
 * @author moronkreacionz
 * @since Sep 10, 2015
 */
class LoginButtonActionListener implements ActionListener {

    LoginFrame loginFrame;

    LoginButtonActionListener(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
    }

    public void performSendMessageAction() {
        String strUserName = this.loginFrame.getTfUserName().getText();
        String strPassword = this.loginFrame.getTfPassword().getText();

        // System.out.println("LoginButtonActionListener: Taken username:" + strUserName);
        // System.out.println("LoginButtonActionListener: Taken password:" + strPassword);
        this.loginFrame.getTfUserName().setText("");
        this.loginFrame.getTfPassword().setText("");

        if (!strUserName.equals("") && !strPassword.equals("")) {
            
            loginFrame.incRetryCounter();
            
            // TODO needs to be worked upon as a better protocol of integration is possible here 
            // System.out.println("LoginButtonActionListener: Login Frame Retry Counter : " + loginFrame.getRetryCounter());
            // Counter increased everytime username/password is Sent to Server
            // username/password sent to server in to a single argument (username,password) format
            // May be use something like XMPP Jabber protocol
            // We can change this later to be an xml / json or any other encoded format
            System.out.println("LoginButtonActionListener: Message to nos. :" + strUserName + "," + strPassword);
            RPIANVartalapClient.nos.println(strUserName + "," + strPassword);
            
        } else {
            
            // ask user to enter values again
            this.loginFrame.setNotificationLabel("Login fields are empty, kindly retry..");
            
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        performSendMessageAction();
    }
    
}

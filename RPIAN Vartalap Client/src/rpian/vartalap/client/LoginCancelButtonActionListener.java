/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rpian.vartalap.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author moronkreacionz
 * @since Sep 10, 2015
 */
class LoginCancelButtonActionListener implements ActionListener {
 
    LoginFrame loginFrame;
 
    LoginCancelButtonActionListener(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
    }
    public void performSendMessageAction() {
        this.loginFrame.getTfUserName().setText("");
        this.loginFrame.getTfPassword().setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        performSendMessageAction();
    }

 
}
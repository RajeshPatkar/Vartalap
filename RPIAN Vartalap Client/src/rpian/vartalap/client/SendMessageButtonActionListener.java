/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpian.vartalap.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author moronkreacionz
 * @since Sep 10, 2015
 */
class SendMessageButtonActionListener implements ActionListener, KeyListener {

    JTextField tfMessage;
    JTextArea taMessageArea;
    
    SendMessageButtonActionListener(JTextField tfMessage, JTextArea taMessageArea) {
        this.tfMessage = tfMessage;
        this.taMessageArea = taMessageArea;
    }

    public void performSendMessageAction() {

        // Accepting user input from the TextField into this String on button press event
        String strMessage = tfMessage.getText();

        // displaying user input strings in TextArea at each input
        taMessageArea.append("Self: " + strMessage + "\n");

        // resetting it to empty to start accepting new inputs from the user 
        tfMessage.setText("");
        
        RPIANVartalapClient.nos.println(strMessage);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        performSendMessageAction();
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }// key pressed event ends here 

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {

            case KeyEvent.VK_ENTER:
                performSendMessageAction();
                break;

            case KeyEvent.VK_ESCAPE:
                // empty the message text field
                tfMessage.setText("");
                break;
            default:
                // no action
                break;
        } // switch case ends here 
    }
}

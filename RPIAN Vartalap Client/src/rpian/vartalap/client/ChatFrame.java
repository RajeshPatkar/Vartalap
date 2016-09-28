/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpian.vartalap.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author moronkreacionz
 * @since Sep 10, 2015
 */
public class ChatFrame {

    private JTextField tfMessage;
    private JButton buttonSend;
    private JButton signout;
    private JPanel jpMessageBar;

    private JTextArea taMessageArea;
    private JScrollPane scrollMessageArea;
    private JPanel jpMessageArea;

    private JFrame frameChatWindow;

    public void setupFrame() {
        Thread t = Thread.currentThread();

        // creating a panel 
        jpMessageBar = new JPanel();
        tfMessage = new JTextField(38);
        tfMessage.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        jpMessageBar.add(tfMessage);

        // send button for user to submit the message string 
        buttonSend = new JButton("Send Message");
         signout = new JButton("Signout");
        jpMessageBar.add(buttonSend);
        jpMessageBar.add(signout);

        // text area to hold the message list 
        taMessageArea = new JTextArea();
        taMessageArea.setEditable(false);
        taMessageArea.setLineWrap(true);
        taMessageArea.setWrapStyleWord(true);
        taMessageArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        // Creating a chat window with frame, textfield, textarea, submit button 
        scrollMessageArea = new JScrollPane(
                taMessageArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollMessageArea.setPreferredSize(new Dimension(600, 340));

        jpMessageArea = new JPanel();
        jpMessageArea.add(scrollMessageArea);

        // Frame for chat conversation 
        frameChatWindow = new JFrame("Client Chat Window - " + t.getName());
        frameChatWindow.setSize(600, 400);
        //frameChatWindow.setBackground(Color.GREEN);
        frameChatWindow.setLocation(new Point(250, 250));
        frameChatWindow.add(BorderLayout.SOUTH, jpMessageBar);
        frameChatWindow.add(jpMessageArea);
        frameChatWindow.setVisible(true);
        
        // Adding button action listener
        SendMessageButtonActionListener sendButtonActionListenerObject = new SendMessageButtonActionListener(tfMessage, taMessageArea);
        buttonSend.addActionListener(sendButtonActionListenerObject);
        tfMessage.addActionListener(sendButtonActionListenerObject);
        signout.addActionListener((e) ->
        {
            RPIANVartalapClient.nos.println("End");
        });

        frameChatWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void messageAreaAppend(String strMessage) {
        taMessageArea.append(strMessage + "\n");
    }
}

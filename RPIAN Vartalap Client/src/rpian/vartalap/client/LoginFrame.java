/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpian.vartalap.client;

/**
 *
 * @author moronkreacionz
 * @since Sep 10, 2015
 */
import java.awt.*;
import java.io.IOException;
import javax.swing.*;
import static javax.swing.SwingConstants.CENTER;

public class LoginFrame {

    private JTextField tfUserName;
    private final JLabel jlUserNameLabel;
    private final JPanel jpUserNamePanel;

    private JPasswordField tfPassword;
    private final JLabel jlPasswordLabel;
    private final JPanel jpPasswordPanel;

    private final JPanel jpLoginTextFieldsPanel;

    private final JButton buttonLogin;
    private final JButton buttonCancel;
    private final JPanel jpLoginButtonsPanel;

    private final JLabel jlNotificationLabel;
    private final JPanel jpNotificationPanel;

    private final JFrame jLoginFrame;
    private int retryCounter = 0;

    LoginFrame() {
        // username UI elements
        tfUserName = new JTextField();
        jlUserNameLabel = new JLabel();
        jpUserNamePanel = new JPanel();

        // password UI elements 
        tfPassword = new JPasswordField();
        jlPasswordLabel = new JLabel();
        jpPasswordPanel = new JPanel();

        // combining user name and password in a panel 
        jpLoginTextFieldsPanel = new JPanel();

        // notification panel for errors and messages
        jlNotificationLabel = new JLabel();
        jpNotificationPanel = new JPanel();

        // buttons for interaction
        buttonLogin = new JButton();
        buttonCancel = new JButton();
        jpLoginButtonsPanel = new JPanel();

        //main frame to hold login ui information
        jLoginFrame = new JFrame();
    }

    public void setupLoginFrame() throws IOException {

        Thread t = Thread.currentThread();

        // TODO : find how to set the size for this text field and use this to avoid doing it in the initializer block
        // Setting up user name text field and label inside a panel 
        tfUserName.setBounds(5, 5, 120, 120);
        tfUserName.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        tfUserName.setPreferredSize(new Dimension(120, 20));
        jlUserNameLabel.setText("Enter UserName:");

        jpUserNamePanel.add(jlUserNameLabel);
        jpUserNamePanel.add(tfUserName);
        jpUserNamePanel.setBackground(new Color(85, 153, 187));
        // jpUserNamePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));

        // Setting up password text field and label inside a panel 
        tfPassword.setBounds(5, 5, 120, 120);
        tfPassword.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        tfPassword.setPreferredSize(new Dimension(120, 20));
        jlPasswordLabel.setText("Enter Password:");

        jpPasswordPanel.add(jlPasswordLabel);
        jpPasswordPanel.add(tfPassword);
        jpPasswordPanel.setBackground(new Color(85, 153, 187));
        //jpPasswordPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));

        // Setting up single panel for login user name and password
        jpLoginTextFieldsPanel.add(jpUserNamePanel);
        jpLoginTextFieldsPanel.add(jpPasswordPanel);
        //jpLoginTextFieldsPanel.setBorder(BorderFactory.createLineBorder(Color.RED,2));

        // send button for user to submit the user name and password
        buttonLogin.setText("Login");
        buttonCancel.setText("Cancel");

        jpLoginButtonsPanel.add(buttonLogin);
        jpLoginButtonsPanel.add(buttonCancel);
        //jpLoginButtonsPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE,2));

        // setting up notification label and panel 
        //jlNotificationLabel.setText("___________________");
        jlNotificationLabel.setOpaque(true);
        jlNotificationLabel.setHorizontalTextPosition(CENTER);
        jpNotificationPanel.add(jlNotificationLabel);
        //jpNotificationPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE,2));

        // Frame for chat conversation 
        jLoginFrame.setTitle("Client Login Window - " + t.getName());
        jLoginFrame.setSize(600, 400);
        jLoginFrame.setBackground(Color.WHITE);
        jLoginFrame.setLocation(new Point(150, 150));

        jLoginFrame.add(BorderLayout.NORTH, jpNotificationPanel);
        jLoginFrame.add(BorderLayout.CENTER, jpLoginTextFieldsPanel);
        jLoginFrame.add(BorderLayout.SOUTH, jpLoginButtonsPanel);

        // Setting up the entire Login frame
        // Adding login button action listener
        LoginButtonActionListener loginButtonActionListener = new LoginButtonActionListener(this);
        buttonLogin.addActionListener(loginButtonActionListener);
        tfUserName.addActionListener(loginButtonActionListener);
        tfPassword.addActionListener(loginButtonActionListener);

        // Adding cancel button action listener
        LoginCancelButtonActionListener loginCancelButtonActionListener = new LoginCancelButtonActionListener(this);
        buttonCancel.addActionListener(loginCancelButtonActionListener);

        // Setting up the Send button ActionListener object for button and textfield, both
        // Assigning Server string in reply to client
        // this is to define the close action as soon as exit is pressed 
        jLoginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }// Setting up frame ends here   

    public void showLoginFrame() {
        jLoginFrame.setVisible(true);
    }

    public void hideLoginFrame() {
        jLoginFrame.setVisible(false);
    }

    public void setNotificationLabel(String strMessage) {
        jlNotificationLabel.setText(strMessage);
        //jpNotificationPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN,2));
        jpNotificationPanel.repaint();
        jpNotificationPanel.revalidate();
        jLoginFrame.repaint();
        jLoginFrame.revalidate();

    }

    public int getRetryCounter() {
        return retryCounter;
    }

    public void incRetryCounter() {
        retryCounter++;
    }

    public JTextField getTfUserName() {
        return tfUserName;
    }

    public void setTfUserName(JTextField tfUserName) {
        this.tfUserName = tfUserName;
    }

    public JPasswordField getTfPassword() {
        return tfPassword;
    }

    public void setTfPassword(JTextField tfPassword) {
        this.tfPassword = tfPassword;
    }

}

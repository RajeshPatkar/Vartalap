package rpian.vartalap.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

/*
 Features covered:
 Client:
 Login screen 
 Login functionality - 3 retries
 Error message notification for each case, empty fields, retry failed auth, auth acknowledge 
 notifying login frame to chat frame with status of chat window initialization

 chat screen
 - welcome note 
 - allowing user mesg delivery
 - allowing broadcast receipt
 - allowing termination of chat using "End"

 Server side: 
 - allowing client to auth 3 times 
 - allowing client to communicate with server independently and broadcast the mesg
 - allowing termination of client communication 

 Todo:
 - pending client specific name handle in broadcast 
 - pending client specific auth flag handle 
 - pending single client, single auth window control
 - pending single client, chat log feature 
 - pending client list population on server side
 - pending client list data population on client side (using user data / grouping approach to handle a messenger)

 - creating an admin console for administering clients and their login details and group details 
 - creating a console to hold chat information on client and server both 
 - option to save chat log
 - option to send files 
 - option to share profile image on thumbnail 

 
*/

public class RPIANVartalapClient {

    static int LOGIN_RETRY_COUNTER = 3;

    static PrintWriter nos = null;
    static BufferedReader nis = null;

    public static void main(String[] args) throws Exception {
        System.out.println("Client Signing on");
        
        try {
            //Socket soc = new Socket("192.168.1.213", 8096);
            Socket soc = new Socket("127.0.0.1", 8096); // local client + server testing
            Thread t = Thread.currentThread();
            t.setName(t.getName() + "-" + soc.getLocalPort());

            System.out.println(t.getName() + " Signing On");
            System.out.println(t.getName() + " says connection established");
            System.out.println(t.getName() + " Starting a Chat Window for this client");

            // Network Output Stream  
            // sending message to the server, by connecting this nos 
            nos = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(soc.getOutputStream())
                    ),
                    true
            );
            System.out.println(t.getName() + " Setting up 'nos' Network Output Stream to send message to Server");

            // Network Input Stream Reader
            nis = new BufferedReader(
                    new InputStreamReader(
                            soc.getInputStream()
                    )
            );
            System.out.println(t.getName() + " Setting up 'nis' Network Input Stream reader for accepting input from Server ");

            boolean flagAuth = false; // To track authentication module and retry in future
            String strReaderMessage = null;

            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setupLoginFrame();

            // switch break OUTER, getting out of the while loop once authenticated 
            OUTER:
            while (LOGIN_RETRY_COUNTER > loginFrame.getRetryCounter()) {

                loginFrame.showLoginFrame();
                strReaderMessage = nis.readLine();
                System.out.println(t.getName() + " message : \"" + strReaderMessage + "\"");

                if (null != strReaderMessage) {
                    switch (strReaderMessage) {
                        case "AuthFailed-Retry": // Allowing retry 

                            System.out.println(t.getName() + " AuthFailed-Retry response received from server");
                            loginFrame.setNotificationLabel("Login Failed, Kindly retry (#" + loginFrame.getRetryCounter() + ")");
                            break;

                        case "Authenticated": // 
                            flagAuth = true;
                            loginFrame.setNotificationLabel("Authenticated, Initializing Chat Window..");
                            System.out.println(t.getName() + " Authentication Received Response from Server: \"" + strReaderMessage + "\"");
                            break OUTER;
                    } // switch
                }// if 
            }// while ends here 

            if (flagAuth == true) {
                Thread.sleep(800);
                loginFrame.setNotificationLabel("Authenticated, Initializing Chat Window..");
                Thread.sleep(800);
                loginFrame.hideLoginFrame();

                // Continuing towards chat activity thought Chat frame. .
                ChatFrame chatFrame = new ChatFrame();

                chatFrame.setupFrame();
                Date dateLog = new Date();
                chatFrame.messageAreaAppend("[" + dateLog.toString() + "]: " + strReaderMessage + ", You are now logged in!");
                chatFrame.messageAreaAppend("[" + dateLog.toString() + "]: Hello, Sir! How can I help you?");

                while (!"End".equals(strReaderMessage)) {
                    strReaderMessage = nis.readLine();
                    chatFrame.messageAreaAppend(strReaderMessage);
                    System.out.println(t.getName() + " Server Responsed with: " + strReaderMessage);
                }// while ends here when "End"

                if ("End".equals(strReaderMessage)) {
                    // If the user has entered "End" = we should close this client application window
                    chatFrame.messageAreaAppend(strReaderMessage);
                    chatFrame.messageAreaAppend("Client: Signing Off");
                }// only if Client gets an "End" from server   

            }// if authenticated.. above block executed
            else { // When loop of retries has failed

                loginFrame.setNotificationLabel("All attempts failed! Shutting down..");
                System.out.println(t.getName() + " Failed Authentication(" + LOGIN_RETRY_COUNTER + " times), Server Msg:" + strReaderMessage);
                Thread.sleep(500);
                strReaderMessage = "End";
                //closing the user chat window - since failed to authenticate from server 
            }

            // If End, close the chat client windows
            if ("End".equals(strReaderMessage)) {

                System.out.println(t.getName() + " End message recd, hence client connection to be terminated using system.exit");
                System.out.println(t.getName() + " Signing Off");
                System.out.println(t.getName() + " Sleeping 3000");
                Thread.sleep(3000);
                System.exit(0);
            }// only if Client gets an "End" from server 

        } catch (IOException | InterruptedException e) {
                // TODO 
        }
    }
}

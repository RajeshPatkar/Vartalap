package rpian.vartalap.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Conversation extends Thread {

    Socket soc;
    static int LOGIN_RETRY_COUNTER = 3; // TODO: move this to configuration file or DB

    public Conversation(Socket soc) {
        this.soc = soc;
    }

    private boolean authenticateUserPassword(String strArgument) {

        Thread t = Thread.currentThread();

        boolean flagAuth = false;
        
        for (String a : RPIANVartalapServer.userArrayList) {
            System.out.println(t.getName() + " authenticateUserPassword(): Loop: String a :" + a);
            // TODO: Move authentication from array to DB
            if (strArgument.equals(a)) {
                flagAuth = true;
                break;
            }
        } // User authentication complete.. 

        return flagAuth;
    }

    @Override
    public void run() {
        Thread t = Thread.currentThread();
        System.out.println(t.getName() + " Signing On");

        try {
            BufferedReader nis = new BufferedReader(
                    new InputStreamReader(
                            soc.getInputStream()
                    )
            );
            PrintWriter nos = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    soc.getOutputStream()
                            )
                    ), true
            );
            RPIANVartalapServer.nosArrayList.add(nos);
            boolean flagAuth = false;
            int retryCounter = 0;
            String strInput = null;

            while (retryCounter < LOGIN_RETRY_COUNTER) {
                strInput = nis.readLine();
                flagAuth = authenticateUserPassword(strInput);
                if (flagAuth == true) {
                    break;
                } else {
                    nos.println("AuthFailed-Retry");
                }
                retryCounter++; // increment retry counter on user authentication failure
            }// while login retry block ends 

            if (flagAuth == true) {
                // perform chat conversation with Authorized user
                nos.println("Authenticated");
                strInput = nis.readLine();
                while (!strInput.equals("End")) {
                    RPIANVartalapServer.q.enqueue(strInput);
                    strInput = nis.readLine();
                } // until the user closes the conversation with an "End"
            } else {
                nos.println("Failed to Authenticate");
                strInput = "End"; // Forcefully terminating the chat with client connection 
            }// flag false ends here 

            if ("End".equals(strInput)) {
                nos.println(strInput);
                // if u dont send "End" to respective client, its chat window wont terminate 
                RPIANVartalapServer.nosArrayList.remove(nos);
                System.out.println(t.getName() + " Chat client terminated");
            }
        } catch (Exception e) {
            System.out.println(t.getName() + " Exception occured\nClient Seems to have abruptly closed the connection\n" + e);
        } finally {
            // TODO : release resources and connections 
            System.out.println(t.getName() + " Signing Off");
        }
    }// run ends 
}
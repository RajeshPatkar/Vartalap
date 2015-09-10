package rpian.vartalap.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

class Reader extends Thread {

    Socket soc;
    String strReaderMessage;
    Thread clientThread;
    JTextArea taMessageArea;
    boolean terminateClientWindow;

    Reader(Socket soc, JTextArea taMessageArea, Thread clientThread, boolean terminateClientWindow) {
        this.soc = soc;
        this.strReaderMessage = null;
        this.taMessageArea = taMessageArea;
        this.clientThread = clientThread;
        this.terminateClientWindow = terminateClientWindow;
    }

    @Override
    public void run() {
        try {
            BufferedReader nis = new BufferedReader(
                    new InputStreamReader(
                            soc.getInputStream()
                    )
            );
            
            this.terminateClientWindow  = false; 
            this.strReaderMessage = nis.readLine();
            
            while ( !this.strReaderMessage.equals("End")) {
        
                System.out.println("Received Server Response: " + this.strReaderMessage);
                taMessageArea.append("Server says: " + this.strReaderMessage + "\n");
                this.strReaderMessage = nis.readLine();

            }// while "End"
               
            if ("End".equals(this.strReaderMessage)) {
                // If the user has entered "End" = we should close this client application window
                System.out.println(clientThread.getName()+" End message recd, hence client connection to be terminated using system.exit");
            }
            
            this.terminateClientWindow = true; 
            
        } catch (IOException ex) {

            System.out.println(clientThread.getName()+" caught Exception" + ex);
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
}

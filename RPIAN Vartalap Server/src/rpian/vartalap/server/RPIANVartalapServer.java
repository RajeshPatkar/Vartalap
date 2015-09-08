package rpian.vartalap.server;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class RPIANVartalapServer {

    public static MessageQueue<String> q = new MessageQueue<>();
    public static ArrayList<PrintWriter> noslist = new ArrayList<>();
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(8096);
        MessageDispatcher md = new MessageDispatcher();
        md.setDaemon(true);
        md.start();
        for( int i = 0 ; i < 10 ; i++)
        {
            Socket soc = ss.accept();
            System.out.println("Connection established");
            Conversation c = new Conversation( soc );
            c.start();
        }
    }
    
}

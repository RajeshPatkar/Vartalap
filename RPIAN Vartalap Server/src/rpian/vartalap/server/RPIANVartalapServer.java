package rpian.vartalap.server;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class RPIANVartalapServer {

    public static MessageQueue<String> q = new MessageQueue<>();

    public static ArrayList<PrintWriter> nosArrayList = new ArrayList<>();
    public static ArrayList<String> userArrayList = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        ServerSocket ss = new ServerSocket(8096);
        MessageDispatcher md = new MessageDispatcher();
        md.setDaemon(true);
        md.start();
        
        // Todo: temp user / passwd array (needs to be mapped and authenticated from database) 
        userArrayList.add("client1,client1");
        userArrayList.add("client2,client2");
        userArrayList.add("client3,client3");
        
        for (int i = 0; i < 50; i++) {
            Socket soc = ss.accept();
            System.out.println("soc:" + soc.getPort() + " " + i + " : Socket in Accepting mode");
            
            System.out.println("Connection established");
            Conversation c = new Conversation(soc);
            String strConversationWindowName = "ChatWindow-" + soc.getPort();

            c.setName(strConversationWindowName);
            System.out.println("soc:" + soc.getPort() + " " + i + " : " + strConversationWindowName + " started");
            c.start();
        } // for ends here
        System.out.println("Server Signing Off");
    }
}
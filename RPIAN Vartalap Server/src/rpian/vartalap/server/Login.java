package rpian.vartalap.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Nikhil Smart
 */
public class Login extends Thread
{
    Socket soc;
    static int LOGIN_RETRY_COUNTER = 3; // TODO: move this to configuration file or DB

    Login(Socket soc)
    {
        this.soc = soc;
    }

    private boolean authenticateUserPassword(String strArgument)
    {
        boolean flagAuth = false;

        for (String a : RPIANVartalapServer.userArrayList)
        {
            System.out.println(this.getName() + " authenticateUserPassword(): Loop: String a :" + a);
            // TODO: Move authentication from array to DB
            if (strArgument.equals(a))
            {
                flagAuth = true;
                break;
            }
        } // User authentication complete.. 

        return flagAuth;
    }

    @Override
    public void run()
    {
        System.out.println(this.getName() + " Signing On");

        try
        {
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
            boolean flagAuth = false;
            int retryCounter = 0;
            String strInput = null;

            while (retryCounter < LOGIN_RETRY_COUNTER)
            {
                strInput = nis.readLine();
                System.out.println("Server recieved = "+strInput);
                flagAuth = authenticateUserPassword(strInput);
                if (flagAuth == true)
                {
                    Conversation c = new Conversation(soc);
                    String strConversationWindowName = "ChatWindow-" + soc.getPort();

                    c.setName(strConversationWindowName);
                    System.out.println("soc:" + soc.getPort() + " : " + strConversationWindowName + " started");
                    c.start();
                    System.out.println("Conversation thread started");
                    break;
                }
                else
                {
                    nos.println("AuthFailed-Retry");
                }
                retryCounter++; // increment retry counter on user authentication failure
            }// while login retry block ends 
        }
        catch (Exception e)
        {
            System.out.println(this.getName() + " Exception occured\nClient Seems to have abruptly closed the connection\n" + e);
        }
        finally
        {
            // TODO : release resources and connections 
            System.out.println(this.getName() + " Signing Off");
        }
    }
}

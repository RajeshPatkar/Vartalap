package rpian.vartalap.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Conversation extends Thread
{

    Socket soc;

    public Conversation(Socket soc)
    {
        this.soc = soc;
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
            RPIANVartalapServer.nosArrayList.add(nos);

            // perform chat conversation with Authorized user
            nos.println("Authenticated");
            String strInput = nis.readLine();
            while (!strInput.equals("End"))
            {
                System.out.println("Server recieved = "+strInput);
                RPIANVartalapServer.q.enqueue(strInput);
                strInput = nis.readLine();
            } // until the user closes the conversation with an "End"

            if ("End".equals(strInput))
            {
                nos.println(strInput);
                // if u dont send "End" to respective client, its chat window wont terminate 
                RPIANVartalapServer.nosArrayList.remove(nos);
                System.out.println(this.getName() + " Chat client terminated");
            }
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
    }// run ends 
}

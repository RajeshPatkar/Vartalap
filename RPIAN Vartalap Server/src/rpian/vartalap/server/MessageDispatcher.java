package rpian.vartalap.server;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;

public class MessageDispatcher extends Thread
{

    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                String str = RPIANVartalapServer.q.dequeue();
                System.out.println("Str = "+str);
                Collection c = RPIANVartalapServer.nosArrayList.values();//currently you can get all nos later u can get based on username
                Iterator<PrintWriter> i = c.iterator();
                while (i.hasNext())
                {
                    PrintWriter p = i.next();
                    p.println(str);
                }
            }
            catch (Exception e)
            {
                System.out.println("MessageDispatcher Caught Exception: " + e);
            }
        }
    }
}

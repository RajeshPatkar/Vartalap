package rpian.vartalap.server;

import java.io.PrintWriter;

public class MessageDispatcher extends Thread {

    @Override
    public void run() {
        while (true) {
            try {
                String str = RPIANVartalapServer.q.dequeue();
                for (PrintWriter o : RPIANVartalapServer.noslist) {
                    o.println(str);
                }
            } catch (Exception e) {
            }
        }
    }
}

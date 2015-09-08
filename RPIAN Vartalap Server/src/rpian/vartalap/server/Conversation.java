package rpian.vartalap.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Conversation extends Thread {

    Socket soc;

    public Conversation(Socket soc) {
        this.soc = soc;
    }

    @Override
    public void run() {
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
            RPIANVartalapServer.noslist.add(nos);
            String str = nis.readLine();
            while (!str.equals("End")) {
                RPIANVartalapServer.q.enqueue(str);
                str = nis.readLine();
            }
            nos.println("End");
            RPIANVartalapServer.noslist.remove(nos);
        } catch (Exception e) {
        }
    }
}


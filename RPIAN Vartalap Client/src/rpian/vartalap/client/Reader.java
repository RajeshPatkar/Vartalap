
package rpian.vartalap.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;


class Reader extends Thread {

    Socket soc;

    Reader(Socket soc) {
        this.soc = soc;
    }

    public void run() {
        try {
            BufferedReader nis = new BufferedReader(
                            new InputStreamReader(
                            soc.getInputStream()
                            )
             );
            String str = nis.readLine();
            while( !str.equals("End") )
            {
             System.out.println("server send back = " +str );
             str = nis.readLine();
            }
        } catch (Exception e) {
        }
    }
}


package rpian.vartalap.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


public class RPIANVartalapClient {

    
    public static void main(String[] args) throws Exception {
        System.out.println("Client Signing on");
          try {
            Socket soc = new Socket("192.168.1.213", 8096);
            Reader r = new Reader(soc);
            r.start();
            System.out.println("Client says connection establised");

            OutputStream out = soc.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(out);
            BufferedWriter br = new BufferedWriter(osw);
            PrintWriter nos = new PrintWriter(br, true);
            BufferedReader kin = new BufferedReader(
                    new InputStreamReader(
                            System.in
                    )
            );
            String n = kin.readLine();
            while (!n.equals("End")) {
                nos.println(n);
                n = kin.readLine();
            }
            nos.println("End");

            System.out.println("Client Signing off");

        } catch (Exception e) {

        }
    }

}

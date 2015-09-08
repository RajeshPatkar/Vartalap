
package rpian.vartalap.server;

import java.util.ArrayList;


public class MessageQueue<T> {

    ArrayList<T> al = new ArrayList<>();

    synchronized public void enqueue(T i) {
        al.add(i);
        notify();
    }

   synchronized public T dequeue() {
        if ( al.isEmpty()) {
            try {
                wait();
            } catch (Exception ex) {}
        }
        return al.remove(0);
      }
   
    synchronized public void print() {
        for (T i : al) {
            System.out.println("-->" + i);
        }

    }

    @Override
    synchronized public String toString() {
        String str = null;
        for (T s : al) {
            str += "::" + s;
        }
        return str;
    }
}
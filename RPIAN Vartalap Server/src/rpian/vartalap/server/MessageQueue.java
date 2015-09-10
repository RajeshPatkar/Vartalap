package rpian.vartalap.server;

import java.util.ArrayList;

public class MessageQueue<T> {

    ArrayList<T> messageQueue = new ArrayList<>();

    synchronized public void enqueue(T i) {
        messageQueue.add(i);
        notify();
    }

    synchronized public T dequeue() {
        if (messageQueue.isEmpty()) {
            try {
                wait();
            } catch (Exception ex) {
                // TODO 
            }
        }
        return messageQueue.remove(0);
    }

    synchronized public void print() {
        for (T value: messageQueue) {
            System.out.println("Value -->" + value);
        }

    }

    @Override
    synchronized public String toString() {
        String strMessage = null;
        for( T value: messageQueue )
        {
            strMessage += ":: "+ value;
        }
        return strMessage;
    }
}

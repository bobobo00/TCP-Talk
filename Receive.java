import java.io.*;
import java.net.Socket;

/**
 * @ClassName receive
 * @Description TODO
 * @Auther danni
 * @Date 2019/11/29 17:10]
 * @Version 1.0
 **/

public class Receive implements Runnable {
    private Socket client;
    private DataInputStream dis=null;
    private boolean isRunning;
    public Receive(Socket client) {
        this.client = client;
        isRunning=true;
        try {
            dis=new DataInputStream(client.getInputStream());
        } catch (IOException e) {
            System.err.println("接收端初始化异常");
            release();
        }
    }

    public void receive(){
        try {
            System.out.println(dis.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
            release();
        }
    }

    @Override
    public void run() {
        while(isRunning) {
            receive();
        }
        release();
    }
    private void release() {
       isRunning=false;
       Utils.close(dis);
    }
}

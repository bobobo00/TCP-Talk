import java.io.*;
import java.net.Socket;

/**
 * @ClassName Send
 * @Description TODO
 * @Auther danni
 * @Date 2019/11/29 17:10]
 * @Version 1.0
 **/

public class Send implements Runnable {
    private Socket client;
    private  BufferedReader br=null;
    private DataOutputStream dos=null;
    private boolean isRunning;
    public Send(Socket client) {
        this.client = client;
        isRunning=true;
        try {
            br=new BufferedReader(new InputStreamReader(System.in));
            dos=new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {
            System.err.println("发送端初始化异常");
           release();
        }
    }
    private String ReadFromConsle(){
        String datas=" ";
        try {
            datas=br.readLine();
        } catch (IOException e) {
            System.err.println("发送端异常");
        }
        return datas;
    }
    public void send(){
        String datas=ReadFromConsle();
        try {
           dos.writeUTF(datas);
           dos.flush();
        } catch (IOException e) {
            System.err.println("发送端异常");
     }
    }
    public void run() {
        while(isRunning) {
            send();
        }
        release();
    }
    private void release() {
        isRunning = false;
        Utils.close(dos,br);
    }

}

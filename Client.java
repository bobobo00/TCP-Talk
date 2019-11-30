import java.io.*;
import java.net.Socket;

/**
 * @ClassName Client
 * @Description TODO
 * @Auther danni
 * @Date 2019/11/29 19:45]
 * @Version 1.0
 **/

public class Client {
    public static void main(String[] args) throws IOException {
        System.out.println("--------Client-----------");
        Socket client=new Socket("localhost",9999);
        System.out.println("请输入用户名：");
        new Thread(new Send(client)).start();
        new Thread(new Receive(client)).start();
    }
}

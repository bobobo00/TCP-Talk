import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Server
 * @Description TODO
 * @Auther danni
 * @Date 2019/11/29 19:45]
 * @Version 1.0
 **/

public class Server {
    public static  List<Channel> channelList=new ArrayList<>();
    private static boolean flag;
    public static void main(String[] args) throws IOException {
        System.out.println("----------server----------");
        ServerSocket server = new ServerSocket(9999);
        flag=true;
        while(flag) {
            Socket client = server.accept();
            Channel channel = new Channel(client);
            channelList.add(channel);
            System.out.println("用户"+channel.getName()+"连接成功！");
            new Thread(channel).start();
        }
    }

}
 class Channel implements Runnable{
  private Socket client;
  private DataInputStream dis=null;
  private DataOutputStream dos=null;
  private String name="";
  private boolean isRunning=false;

     public String getName() {
         return name;
     }

     public Channel(Socket client) {
         this.client = client;
         isRunning=true;
         try {
             dis=new DataInputStream(client.getInputStream());
             dos=new DataOutputStream((client.getOutputStream()));
             name=receive();
             send("    "+name+"欢迎来到聊天室    ");
         } catch (IOException e) {
             realese();
         }
     }
     public String receive(){
         String data="";
         try {
             data=dis.readUTF();
         } catch (IOException e) {
             e.printStackTrace();
         }
         return data;
     }
     public void send(String info){
         try {
             dos.writeUTF(info);
             dos.flush();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
     public void sendOthers(String info){
          for(Channel target:Server.channelList){
              if(!target.equals(this)){
                  target.send(name+":"+info);
              }
          }
     }

     public void realese(){
      isRunning=false;
         try {
             if(null!=dis){
             dis.close();}
         } catch (IOException e) {
             e.printStackTrace();
         }
         try {
             if(null!=dos){
             dos.close();}
         } catch (IOException e) {
             e.printStackTrace();
         }
         try {
             if(null!=client){
             client.close();}
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
     public void run() {
        while(isRunning){
             String datas=this.receive();
             sendOthers(datas);
         }
         realese();
     }
 }
import java.io.Closeable;
import java.io.IOException;

/**
 * @ClassName Utils
 * @Description TODO
 * @Auther danni
 * @Date 2019/11/29 21:12]
 * @Version 1.0
 **/

public class Utils {
    public static void close(Closeable... targets){
        for(Closeable target:targets){
                try {
                    if(null!=target){
                    target.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}

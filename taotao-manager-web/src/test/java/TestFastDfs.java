import com.taotao.utils.FastDFSClient;
import org.csource.fastdfs.*;
import org.junit.Test;

/**
 * Created by 31364 on 2018/2/27.
 * description: 测试图片上传fastdfs
 */
public class TestFastDfs {

    @Test
    public void testFastDfs() throws Exception {
//        1.创建一个配置文件fastdfs.conf，指定trackerServer的地址
//        2.使用全局方法加载配置文件
        ClientGlobal.init("D:\\study\\ideaIU-2017.3.3.win\\workspace\\taotao\\taotao-manager-web\\src\\test\\resources\\fast_dfs.properties");
//        3.创建一个trackerClient对象
        TrackerClient trackerClient = new TrackerClient();
//        4.通过trackerClient对象获得trackerServer
        TrackerServer trackerServer = trackerClient.getConnection();
//        5.创建一个StorageServer的引用null就可以
        StorageServer storageServer = null;
//        6.创建一个StorageClient对象
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
//        7.上传图片
        String[] strings = storageClient.upload_file("C:\\Users\\31364\\Desktop\\WLOP\\16s_by_wlop-db0nplf.jpg", "jpg", null);
        for (String string : strings) {
            System.out.println(string);
        }
    }

    @Test
    public void testFastDfsClient() throws Exception {
        FastDFSClient fastDFSClient = new FastDFSClient("D:\\study\\ideaIU-2017.3.3.win\\workspace\\taotao\\taotao-manager-web\\src\\test\\resources\\fast_dfs.properties");
        String string = fastDFSClient.uploadFile("C:\\Users\\31364\\Desktop\\WLOP\\16s_by_wlop-db0nplf.jpg");
        System.out.println(string);
    }
}

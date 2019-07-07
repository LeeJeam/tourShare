package top.ibase4j.core.support.fastdfs;

import com.luhuiguo.fastdfs.conn.*;
import com.luhuiguo.fastdfs.service.DefaultGenerateStorageClient;
import com.luhuiguo.fastdfs.service.DefaultTrackerClient;
import com.luhuiguo.fastdfs.service.GenerateStorageClient;
import com.luhuiguo.fastdfs.service.TrackerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.ibase4j.core.util.InstanceUtil;
import top.ibase4j.core.util.PropertiesUtil;

import java.io.Serializable;

/**
 * @author ShenHuaJie
 * @version 2016年6月27日 上午9:51:06
 */
@SuppressWarnings("serial")
public class FileManager implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(FileManager.class);
    private static FileManager fileManager;
    private GenerateStorageClient fastFileStorageClient;

    public static FileManager getInstance() {
        if (fileManager == null) {
            synchronized (FileManager.class) {
                fileManager = new FileManager();
            }
        }
        return fileManager;
    }

    private FileManager() {
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
        pooledConnectionFactory.setSoTimeout(PropertiesUtil.getInt("fdfs.soTimeout", 1000));
        pooledConnectionFactory.setConnectTimeout(PropertiesUtil.getInt("fdfs.connectTimeout", 1000));
        ConnectionPoolConfig connectionPoolConfig = new ConnectionPoolConfig();
        FdfsConnectionPool pool = new FdfsConnectionPool(pooledConnectionFactory, connectionPoolConfig);
        TrackerConnectionManager trackerConnectionManager = new TrackerConnectionManager(pool,
                InstanceUtil.newArrayList(PropertiesUtil.getString("fdfs.trackerList").split(",")));
        TrackerClient trackerClient = new DefaultTrackerClient(trackerConnectionManager);
        ConnectionManager connectionManager = new ConnectionManager(pool);
        fastFileStorageClient = new DefaultGenerateStorageClient(trackerClient, connectionManager);
    }

    public void upload(final FileModel file) {
        String path = fastFileStorageClient.uploadFile(file.getGroupName(), file.getContent(), file.getExt())
                .getFullPath();
        logger.info("Upload to fastdfs success =>" + path);
        file.setRemotePath(PropertiesUtil.getString("fdfs.fileHost") + path);
    }

    public FileModel getFile(String groupName, String path) {
        FileModel file = new FileModel();
        file.setContent(fastFileStorageClient.downloadFile(groupName, path));
        return file;
    }

    public void deleteFile(String groupName, String path) throws Exception {
        fastFileStorageClient.deleteFile(groupName, path);
    }
}

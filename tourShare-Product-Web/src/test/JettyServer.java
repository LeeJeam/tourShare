import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;


/**
 * jetty启动服务
 *
 */
public class JettyServer {

    public static void main(String[] args) throws Exception {
        int port = getPort();
        startWeb(port);
        System.out.println("Jetty Server started.");
    }

    private static int getPort() throws Exception {
        String path =JettyServer.class.getClassLoader().getResource("").getPath();
        path = new File(path).getParentFile().getParentFile() + "/pom.xml";
        //创建一个DocumentBuilderFactory的对象
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        //创建一个DocumentBuilder的对象
        //创建DocumentBuilder对象
        DocumentBuilder db = dbf.newDocumentBuilder();
        //通过DocumentBuilder对象的parser方法加载pom.xml文件到当前项目下
        Document document = db.parse(path);
        NodeList project = document.getElementsByTagName("project");
        NodeList childNodes = project.item(0).getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            System.out.println(item.getNodeName());
            if ("properties".equals(item.getNodeName())) {
                NodeList childNodes1 = item.getChildNodes();
                for (int j = 0; j < childNodes1.getLength(); j++) {
                    Node item1 = childNodes1.item(j);
                    System.out.println(item1.getNodeName());
                    if ("server.port".equals(item1.getNodeName())) {
                        return Integer.parseInt(item1.getTextContent());
                    }
                }
            }
        }
        throw new RuntimeException("pom.xml未定义项目启动端口");
    }

    private static void startWeb(int port) throws Exception {
        String path =JettyServer.class.getClassLoader().getResource("").getPath();
        path = new File(path).getParentFile().getParentFile().getAbsolutePath() + "/src/main/webapp";
        // 服务器的监听端口
        Server server = new Server(port);
        // 关联一个已经存在的上下文
        WebAppContext context = new WebAppContext();
        context.setDescriptor(path + "/WEB-INF/web.xml");
        // 设置Web内容上下文路径
        context.setResourceBase(path);
        // 设置上下文路径
        context.setContextPath("/");
        context.setParentLoaderPriority(true);
        StringBuilder sb = new StringBuilder();
        sb.append(path);
        sb.append("/WEB-INF/classes");
        sb.append(";");
        File libDir = new File(path + "/WEB-INF/lib");
        if (libDir.exists() && libDir.isDirectory()) {
            File[] jars = libDir.listFiles((dir, name) -> {return (name.endsWith(".zip") || name.endsWith(".jar"));});
            for (File jar : jars) {
                sb.append(jar.getAbsolutePath());
                sb.append(";");
            }
        }
        System.out.println(sb.toString());
        context.setExtraClasspath(sb.toString());
        //开启HTML，CSS，JS热部署
        context.setInitParameter("org.eclipse.jetty.servlet.Default.useFileMappedBuffer", "false");
        server.setHandler(context);
        // 启动
        server.start();
        server.join();
    }

}

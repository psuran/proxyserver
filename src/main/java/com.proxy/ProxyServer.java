package src.main.java.com.proxy;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Logger;

public class ProxyServer {

    static final int HTTP_PORT = 8082;
    static final int BACKLOG = 0;
    static final int THREAD_COUNT = 100;
    static Logger logger = Logger.getLogger(ProxyServer.class.getName());

    public static void main(String[] args) throws IOException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        logger.info(inetAddress.getHostAddress());
        InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress, HTTP_PORT);
        logger.info(inetSocketAddress.toString());
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREAD_COUNT);

        HttpServer httpServer = HttpServer.create(inetSocketAddress, BACKLOG);
        httpServer.setExecutor(threadPoolExecutor);
        httpServer.createContext("/service", new RequestHandler());
        httpServer.start();
    }

}

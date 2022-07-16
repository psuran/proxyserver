package src.main.java.com.proxy;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.logging.Logger;

public class RequestHandler implements HttpHandler {

    Logger logger = Logger.getLogger(RequestHandler.class.getName());
    final String resp = "<?xml version=\"1.0\"?>\n" +
            "\n" +
            "<soap:Envelope\n" +
            "xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope/\"\n" +
            "soap:encodingStyle=\"http://www.w3.org/2003/05/soap-encoding\">\n" +
            "\n" +
            "<soap:Body>\n" +
            "  <m:GetPriceResponse xmlns:m=\"https://www.w3schools.com/prices\">\n" +
            "    <m:Price>1.90</m:Price>\n" +
            "  </m:GetPriceResponse>\n" +
            "</soap:Body>\n" +
            "\n" +
            "</soap:Envelope>";

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        InputStream inputStream = httpExchange.getRequestBody();
        byte[] soapReq = new byte[1024];
        StringBuffer stringBuffer = new StringBuffer();
        while(inputStream.read(soapReq) >= 0 ){
            stringBuffer.append(new String(soapReq));
        }
        String response = stringBuffer.toString().trim();
        logger.info(response);


        httpExchange.getResponseHeaders().add("Content-Type", "application/xml");
        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length());

        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.flush();
        outputStream.close();
        httpExchange.close();
    }
}

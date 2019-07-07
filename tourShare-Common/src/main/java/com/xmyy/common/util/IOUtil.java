/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xmyy.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zlp
 */
public class IOUtil {

    public static class Content {

        public String contentType;
        public byte[] data;
    }

    public static interface IOHandler {

        public void prepareConnection(URLConnection connection) throws IOException;

        public OutputStream getOutputStream(String contentType) throws IOException;
    }

    private static final int DEFAULT_CONNECT_TIMEOUT_MILLISECONDS = 6 * 1000;
    private static final int DEFAULT_READ_TIMEOUT_MILLISECONDS = 12 * 1000;
    private static final int DEFAULT_BUFFER_SIZE = 16 * 1024;
    private static final String os = System.getProperty("os.name").toLowerCase();
    protected static final Logger logger = LoggerFactory.getLogger(IOUtil.class);

    private static String typeCharset(String contentType, String defaultCharset) {

        if (contentType != null && !contentType.isEmpty()) {
            String[] frags = contentType.toLowerCase().split(";");
            if (frags != null && frags.length > 0) {
                for (String frag : frags) {
                    frag = frag.trim();
                    if (frag.startsWith("charset=")) {
                        defaultCharset = frag.substring(8).trim().replaceAll("\"", "").replaceAll("\'", "");
                        break;
                    }
                }
            }
        }
        return defaultCharset;
    }

    public static String readText(String url) {
        return readText(url, DEFAULT_CONNECT_TIMEOUT_MILLISECONDS, DEFAULT_READ_TIMEOUT_MILLISECONDS);
    }

    public static String readText(String url, int connectTimeout, int readTimeout) {
        Content content = read(url, connectTimeout, readTimeout);
        if (content != null) {
            try {
                return new String(content.data, typeCharset(content.contentType, "utf-8"));
            } catch (UnsupportedEncodingException ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
        return null;
    }

    public static Content read(String url) {
        return read(url, DEFAULT_CONNECT_TIMEOUT_MILLISECONDS, DEFAULT_READ_TIMEOUT_MILLISECONDS);
    }

    public static Content read(String url, int connectTimeout, int readTimeout) {
        final ByteArrayOutputStream out = new ByteArrayOutputStream(64 * 1024);
        final Content content = new Content();

        read(url, new IOHandler() {

            public void prepareConnection(URLConnection connection) throws IOException {
            }

            public OutputStream getOutputStream(String contentType) throws IOException {
                content.contentType = contentType;
                return out;
            }
        }, connectTimeout, readTimeout);
        content.data = out.toByteArray();
        return content;
    }

    public static boolean readToFile(String url, File file) {
        return readToFile(url, file, DEFAULT_CONNECT_TIMEOUT_MILLISECONDS, DEFAULT_READ_TIMEOUT_MILLISECONDS);
    }

    public static boolean readToFile(String url, final File file, int connectTimeout, int readTimeout) {
        return read(url, new IOHandler() {

            public void prepareConnection(URLConnection connection) throws IOException {
            }

            public OutputStream getOutputStream(String contentType) throws IOException {
                return new FileOutputStream(file);
            }
        }, connectTimeout, readTimeout);
    }

    public static boolean read(String url, IOHandler handler) {
        return read(url, handler, DEFAULT_CONNECT_TIMEOUT_MILLISECONDS, DEFAULT_READ_TIMEOUT_MILLISECONDS);
    }

    public static boolean read(String url, IOHandler handler, int connectTimeout, int readTimeout) {
        URLConnection conn = null;
        InputStream in = null;
        OutputStream out = null;

        try {
            URL u = new URL(url);
            conn = u.openConnection();
            if (conn instanceof HttpURLConnection) {
                HttpURLConnection connection = (HttpURLConnection) conn;
                if (connection instanceof HttpsURLConnection) {
                    TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            public X509Certificate[] getAcceptedIssuers() {
                                return null;
                            }

                            public void checkClientTrusted(X509Certificate[] certs, String authType) {
                            }

                            public void checkServerTrusted(X509Certificate[] certs, String authType) {
                            }
                        }
                    };
                    SSLContext ctx;
                    try {
                        ctx = SSLContext.getInstance("TLS");
                        ctx.init(null, trustAllCerts, new java.security.SecureRandom());
                        ((HttpsURLConnection) connection).setSSLSocketFactory(ctx.getSocketFactory());
                    } catch (KeyManagementException ex) {
                    } catch (NoSuchAlgorithmException ex) {
                    }
                    ((HttpsURLConnection) connection).setHostnameVerifier(new HostnameVerifier() {
                        public boolean verify(String paramString, SSLSession paramSSLSession) {
                            return true;
                        }
                    });
                }

                connection.setRequestMethod("GET");
            }
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);
            handler.prepareConnection(conn);
            conn.connect();

            int responseCode = 200;
            if (conn instanceof HttpURLConnection) {
                responseCode = ((HttpURLConnection) conn).getResponseCode();
            }

            if (responseCode == 200) {
                String contentType = conn.getContentType();
                out = handler.getOutputStream(contentType);
                if (out != null) {
                    in = conn.getInputStream();
                    int totalRead = copyStream(in, out);
                    logger.debug("download: url: " + url + " totalRead: " + totalRead + " contentType: " + contentType);
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            logger.error("download: url: " + url + " " + e.getMessage(), e);
            return false;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
            }
            if (conn != null && conn instanceof HttpURLConnection) {
                ((HttpURLConnection) conn).disconnect();
            }
        }
    }

    public static int copyStream(InputStream in, OutputStream out) throws IOException {
        return copyStream(in, out, DEFAULT_BUFFER_SIZE);
    }

    public static int copyStream(InputStream in, OutputStream out, int bufferSize) throws IOException {
        byte[] buff = new byte[bufferSize];
        int cbio, totalRead = 0;
        while ((cbio = in.read(buff)) != -1) {
            totalRead += cbio;
            out.write(buff, 0, cbio);
        }
        return totalRead;
    }

    public static String exec(String[] args) {
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        processBuilder.redirectErrorStream(true);
        Process process = null;
        InputStream stdout = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            process = processBuilder.start();
            stdout = process.getInputStream();
            int cbio;
            while ((cbio = stdout.read()) != -1) {
                byteArrayOutputStream.write(cbio);
            }
        } catch (IOException e) {
            logger.error("exec " + args[0] + ": " + e.getMessage(), e);
            return null;
        } finally {
            try {
                if (stdout != null) {
                    stdout.close();
                }
            } catch (IOException e) {
            }
            if (process != null) {
                process.destroy();
            }
        }
        return byteArrayOutputStream.toString();
    }

    public static File mkdir(String path) {
        List<String> list = new ArrayList<String>();
        String[] arr = path.split("/");
        String s = "";
        File d = null;

        for (int i = 0, length = arr.length; i < length; i++) {
            if (!arr[i].isEmpty() && !arr[i].startsWith(".")) {
                s = s + "/" + arr[i];
                logger.debug(s);
                d = new File(s);
                if (!d.exists()) {
                    list.add(s);
                    d.mkdir();
                }
            }
        }
        if (!os.contains("windows")) {
            if (!list.isEmpty()) {
                List<String> args = new ArrayList<String>();
                args.add("chmod");
                args.add("755");
                args.addAll(list);
                exec(args.toArray(new String[]{}));
            }
        }
        return d;
    }

    public static void close(Closeable ...closeables){
        if(closeables!=null){
            for (Closeable closeable : closeables) {
                if(closeable!=null){
                    try {
                        closeable.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
    }
}

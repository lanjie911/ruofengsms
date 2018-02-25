package com.sms.service.thirdpart.fenghuo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class KyongHttpUtils {


    public static enum HttpMethod  {
        POST("POST") , GET("GET") ;
        private String method ;

        HttpMethod(String method) {
            this.method = method;
        }

        public String getMethod() {
            return method;
        }


    }

    private static final String DEFAULT_CHARSET = "GBK";

    private KyongHttpUtils() {
    }


    /**
     *
     * @param url  请求url
     * @param params 请求参数
     * @param connectTimeout 连接超时
     * @param readTimeout 读取超时
     * @param headerMap 设置header
     * @return
     * @throws IOException
     */
    public static String doPost(String url, Map<String, String> params, int connectTimeout, int readTimeout,Map<String,String> headerMap) throws IOException {
        String charset = DEFAULT_CHARSET ;
        String ctype = "application/x-www-form-urlencoded;charset=" + charset;
        String query = buildQuery(params,charset);
        byte[] content = new byte[0];
        if(query != null) {
            content = query.getBytes(charset);
        }
        return _doPost(url, ctype, content, connectTimeout, readTimeout, headerMap);
    }

    private static String _doPost(String url, String ctype, byte[] content, int connectTimeout, int readTimeout, Map<String, String> headerMap) throws IOException {
        HttpURLConnection conn = null;
        OutputStream out = null;
        String rsp = null;
        try {
            conn = getConnection(new URL(url), "POST", ctype, headerMap);
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);
            out = conn.getOutputStream();
            out.write(content);
            rsp = getResponseAsString(conn);
        } finally {
            if(out != null) {
                out.close();
            }

            if(conn != null) {
                conn.disconnect();
            }
        }
        return rsp;
    }

    /**
     *
     * @param params  请求参数
     * @return  get请求的参数拼接
     * @throws IOException
     */
    public static String buildQuery(Map<String, String> params , String charset) throws IOException {
        if(params != null && !params.isEmpty()) {
            StringBuilder query = new StringBuilder();
            boolean hasParam = false;
            for (Map.Entry<String,String> entry : params.entrySet()){
                String name = entry.getKey();
                String value = entry.getValue();
                if(StringUtils.areNotEmpty(new String[]{name, value})) {
                    if(hasParam) {
                        query.append("&");
                    } else {
                        hasParam = true;
                    }
                    query.append(name).append("=").append(URLEncoder.encode(value,charset));
                }
            }
            return query.toString();
        } else {
            return null;
        }
    }

    private static HttpURLConnection getConnection(URL url, String method, String ctype, Map<String, String> headerMap) throws IOException {
        HttpURLConnection conn = null;
        conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html");
        conn.setRequestProperty("User-Agent", "kyong-open-api-sdk");
        conn.setRequestProperty("Content-Type", ctype);
        if(headerMap != null) {
            for (Map.Entry<String,String> entry : headerMap.entrySet()) {
                conn.setRequestProperty(entry.getKey(),entry.getValue());
            }
        }
        return conn;
    }


    protected static String getResponseAsString(HttpURLConnection conn) throws IOException {
        String charset = getResponseCharset(conn.getContentType());
        InputStream es = conn.getErrorStream();
        if(es == null) {
            return getStreamAsString(conn.getInputStream(), charset);
        } else {
            String msg = getStreamAsString(es, charset);
            if(StringUtils.isEmpty(msg)) {
                throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());
            } else {
                return msg;
            }
        }
    }

    private static String getResponseCharset(String ctype) {
        String charset = DEFAULT_CHARSET;
        if(StringUtils.isNotEmpty(ctype)) {
            String[] params = ctype.split(";");
            int length = params.length;
            for(int i = 0; i < length; i++) {
                String param = params[i];
                param = param.trim();
                if(param.startsWith("charset")) {
                    String[] pair = param.split("=", 2);
                    if(pair.length == 2 && !StringUtils.isEmpty(pair[1])) {
                        charset = pair[1].trim();
                    }
                    break;
                }
            }
        }

        return charset;
    }

    private static String getStreamAsString(InputStream stream, String charset) throws IOException {
        try {
            InputStreamReader reader = new InputStreamReader(stream, charset);
            StringBuilder response = new StringBuilder();
            char[] buff = new char[1024];
            boolean read = false;

            int read1;
            while((read1 = reader.read(buff)) > 0) {
                response.append(buff, 0, read1);
            }

            String content = response.toString();
            return content;
        } finally {
            if(stream != null) {
                stream.close();
            }

        }
    }

    public static String doGet(String url, Map<String, String> params) throws IOException {
        return doGet(url, params, DEFAULT_CHARSET);
    }

    public static String doGet(String url, Map<String, String> params, String charset) throws IOException {
        HttpURLConnection conn = null;
        String rsp = null;
        try {
            String ctype = "application/x-www-form-urlencoded;charset=" + charset;
            String query = buildQuery(params,charset);
            conn = getConnection(buildGetUrl(url, query), "GET", ctype, (Map)null);
            rsp = getResponseAsString(conn);
        } finally {
            if(conn != null) {
                conn.disconnect();
            }

        }

        return rsp;
    }

    private static URL buildGetUrl(String strUrl, String query) throws IOException {
        URL url = new URL(strUrl);
        if(StringUtils.isEmpty(query)) {
            return url;
        } else {
            if(StringUtils.isEmpty(url.getQuery())) {
                if(strUrl.endsWith("?")) {
                    strUrl = strUrl + query;
                } else {
                    strUrl = strUrl + "?" + query;
                }
            } else if(strUrl.endsWith("&")) {
                strUrl = strUrl + query;
            } else {
                strUrl = strUrl + "&" + query;
            }

            return new URL(strUrl);
        }
    }
}

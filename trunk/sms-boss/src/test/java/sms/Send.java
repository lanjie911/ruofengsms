package sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Administrator
 */
public class Send {

    public static String SMS(String postData, String postUrl) {
        try {
            //发送POST请求
            URL url = new URL(postUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Length", "" + postData.length());
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(postData);
            out.flush();
            out.close();

            //获取响应状态
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("connect failed!");
                return "";
            }
            //获取响应内容体
            String line, result = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
            in.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return "";
    }
    
    public static String dealSubString(String oldStr,String front,String behind){
    	
		return oldStr.substring(oldStr.indexOf(front)+front.length(),oldStr.indexOf(behind));
    	
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException {
    	//提交短信
    /*	String PostData = "sname=dl-lilun&spwd=lilun8848&scorpid=&sprdid=1012888&sdst=18511989847&smsg="+java.net.URLEncoder.encode("你的验证码是123456【若风科技】","utf-8");
        String ret = SMS(PostData, "http://cf.51welink.com/submitdata/Service.asmx/g_Submit");
    	String State =dealSubString(ret,"<State>","</State>");
    	String MsgID =dealSubString(ret,"<MsgID>","</MsgID>");  
    	String MsgState = dealSubString(ret,"<MsgState>","</MsgState>");
    	String Reserve =dealSubString(ret,"<Reserve>","</Reserve>"); */
    	
    	//
    	
    	
    }
    
    
    	
}

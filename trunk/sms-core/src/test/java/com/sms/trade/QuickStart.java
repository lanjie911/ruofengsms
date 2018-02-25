/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package com.sms.trade;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class QuickStart {

    public static void main(String[] args) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
        	StringBuffer str = new StringBuffer();
    		for(int i=0; i<800;i++){
    			str.append("18511293080").append(",");
    		}
    		
    		String mobiles = str.toString();
    		mobiles = mobiles.substring(0, mobiles.length());
    		
    		String username = "bjrfwy";
    		String password_md5 = "1655112897fefaf28c4e190563113b05";
        	
//        	String uri = "http://127.0.0.1:8119/sms-core/TestServlet";
    		String uri = "http://api.app2e.com/smsBigSend.api.php";

            HttpPost httpPost = new HttpPost(uri);
            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            nvps.add(new BasicNameValuePair("username", username));
            nvps.add(new BasicNameValuePair("pwd", password_md5));
            nvps.add(new BasicNameValuePair("extnum", "8688"));
            nvps.add(new BasicNameValuePair("p", mobiles));
            nvps.add(new BasicNameValuePair("isUrlEncode", "no"));
            nvps.add(new BasicNameValuePair("charSetStr", "utf"));
            nvps.add(new BasicNameValuePair("msg", "【测试短信】短信测试，请勿发，谢谢。11月17日17:29时"));
            System.out.println(nvps.toString());
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            CloseableHttpResponse response2 = httpclient.execute(httpPost);

            try {
                System.out.println(response2.getStatusLine());
                HttpEntity entity2 = response2.getEntity();
                
                if (entity2 != null){
                    InputStream instreams = entity2.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(instreams));  
                    StringBuilder reqStr = new StringBuilder();
                    String jsonStr = null;
                    while ((jsonStr = reader.readLine()) != null)
                    	reqStr.append(jsonStr);
        			reader.close();// 关闭输入流
        			String data = URLDecoder.decode(reqStr.toString(), "UTF-8");
        			System.out.println(data);
                }
                // do something useful with the response body
                // and ensure it is fully consumed
                EntityUtils.consume(entity2);
            } finally {
                response2.close();
            }
        } finally {
            httpclient.close();
        }
    }

}

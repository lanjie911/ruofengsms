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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class HttpClientQuickStart {

	public static void main(String[] args) throws Exception {
//		sendCMCC();
		callbackReport();
//		sendChuangRui();
	}

	public static void sendChuangRui() {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			int randNum = 1 + (int) (Math.random() * ((999999 - 1) + 1));
			HttpPost httpPost = new HttpPost("http://api.1cloudsp.com/api/v2/send");
			String content = "您的注册验证码为：" + randNum;
			String mobiles = "18511293080,18511989847";

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			// 联通的
			nvps.add(new BasicNameValuePair("accesskey", "oB0mUQumZBONFvHQ"));
			nvps.add(new BasicNameValuePair("secret", "eqUIIPa3U52RUXHakuMM8At3AIVWKksc"));
			nvps.add(new BasicNameValuePair("sign", "【闪电借款】"));
			nvps.add(new BasicNameValuePair("templateId", ""));
			nvps.add(new BasicNameValuePair("mobile", mobiles));
			nvps.add(new BasicNameValuePair("content", content));
			nvps.add(new BasicNameValuePair("data", ""));
			nvps.add(new BasicNameValuePair("scheduleSendTime", ""));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName("UTF-8")));
			System.out.println("请求信息：" + nvps.toString());
			response = httpclient.execute(httpPost);

			System.out.println("HTTP响应码：" + response.getStatusLine());

			HttpEntity entity = response.getEntity();
			InputStream instreams = entity.getContent();

			BufferedReader reader = new BufferedReader(new InputStreamReader(instreams));
			StringBuilder respStr = new StringBuilder();
			String jsonStr = null;
			while ((jsonStr = reader.readLine()) != null)
				respStr.append(jsonStr);
			reader.close();
			jsonStr = URLDecoder.decode(respStr.toString(), "UTF-8");
			System.out.println("响应信息：" + jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	public static void sendCMCC() {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			int randNum = 1 + (int) (Math.random() * ((999999 - 1) + 1));
//			HttpPost httpPost = new HttpPost("http://58.253.87.82:8718/smsgwhttp/sms/submit");
			HttpPost httpPost = new HttpPost("http://112.90.92.218:8718/smsgwhttp/sms/submit");
			
			String content = "【聚梦通信】短信验证码测试号为：" + randNum;
			String mobiles = "18511293080,";
			
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			// 移动的
//			nvps.add(new BasicNameValuePair("spid", "80011"));
//			nvps.add(new BasicNameValuePair("password", "DASfds"));
//			nvps.add(new BasicNameValuePair("ac", "489011"));
			// 联通的
			nvps.add(new BasicNameValuePair("spid", "100031"));
			nvps.add(new BasicNameValuePair("password", "da57eDRA87G"));
			nvps.add(new BasicNameValuePair("ac", "106905563347"));
			nvps.add(new BasicNameValuePair("content", content));
			nvps.add(new BasicNameValuePair("mobiles", mobiles));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName("UTF-8")));
			response = httpclient.execute(httpPost);
			
			System.out.println("HTTP响应码：" + response.getStatusLine());
			
			HttpEntity entity = response.getEntity();
			InputStream instreams = entity.getContent();
			/*
			 * String jsonStr = null;
			 * 
			 * ByteArrayOutputStream baos = new ByteArrayOutputStream(); int i=-1; while((i
			 * = instreams.read())!=-1){ baos.write(i); } jsonStr =
			 * URLDecoder.decode(baos.toString(), "UTF-8");
			 */
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(instreams));
			StringBuilder respStr = new StringBuilder();
			String jsonStr = null;
			while ((jsonStr = reader.readLine()) != null)
				respStr.append(jsonStr);
			reader.close(); // 关闭输入流
			jsonStr = URLDecoder.decode(respStr.toString(), "UTF-8");
			System.out.println("响应信息：" + jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	public static void callbackReport() {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			
			//HttpPost httpPost = new HttpPost("http://api.juhedx.com/JuMengReport");
			HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/ROOT/CallBackServlet");
			//String callBackStr = "mid=['d9fd3ee7db45bce3,13271230890,DELIVRD'"+",'b92de091a9020cc9, 13651056688 ,DELIVRD']";
			String callBackStr = "mid=[\"d9fd3ee7db45bce3,13271230890,DELIVRD\",\"b92de091a9020cc9,13651056688,DELIVRD\"]";
			
			httpPost.setEntity(new StringEntity(callBackStr, Charset.forName("UTF-8"))); 
			response = httpclient.execute(httpPost);
			
			System.out.println("HTTP响应码：" + response.getStatusLine());
			
			HttpEntity entity = response.getEntity();
			InputStream instreams = entity.getContent();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(instreams));
			StringBuilder respStr = new StringBuilder();
			String jsonStr = null;
			while ((jsonStr = reader.readLine()) != null)
				respStr.append(jsonStr);
			reader.close(); // 关闭输入流
			jsonStr = URLDecoder.decode(respStr.toString(), "UTF-8");
			System.out.println("响应信息：" + jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
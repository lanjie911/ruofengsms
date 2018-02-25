package com.sms.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.csource.service.DownloadFile;
import org.csource.service.DownloadFile.DownloadResponse;
import org.csource.service.UploadFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 类说明
 * 
 * @author toughPear
 * @date 2017年7月10日
 */
@Component
@Service
public class FastDfsUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(FastDfsUtil.class);
	
	
	@Value("${fastdfs_szTrackerServers}")
    private String szTrackerServersStr;

    @PostConstruct
    public void init(){
        try {
            String[] szTrackerServers = szTrackerServersStr.split("\\|");
            ClientGlobal.init(szTrackerServers);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    public  Map<String, Object> doUpload(MultipartFile file) {
    	Map<String, Object> result = new HashMap<>();
        String fastdfsId = null;
        try {
            byte[] bytes = file.getBytes();
            
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;
            StorageClient client = new StorageClient(trackerServer, storageServer);
            String originalFilename = file.getOriginalFilename();
            NameValuePair[] metaList = new NameValuePair[1];
            metaList[0] = new NameValuePair("fileName", originalFilename);
            String[] parts = client.upload_file(bytes, null, metaList);
            if (parts != null){
                fastdfsId = parts[0] + "/" + parts[1];
            }
            result.put("fastUrl", fastdfsId);
            result.put("fileName", originalFilename);
            result.put("success", true);
			result.put("message", "文件上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
			result.put("message", "文件上传失败");
			result.put("fastUrl", "");
            result.put("fileName", "");
        }
        return result;
    }
    
    public  void doDownloadImage(String fileId,HttpServletResponse response){
    	logger.info("====>>>>>fileId:" + fileId);
    	String[] szTrackerServers = szTrackerServersStr.split("\\|");
    	logger.info("==============>>>>>>szTrackerServers:" + Arrays.toString(szTrackerServers));
        DownloadFile downloadFile = DownloadFile.getInstance(szTrackerServers);
        DownloadResponse downloadResponse = downloadFile.doDownloadFile(fileId);
        
        try {
	        if(downloadResponse.getDownloadResult() == DownloadFile.FALSE){
				logger.info("FastDfsUtil.doDownloadImage: 图片下载失败");
				
			}else{
				byte[] fileContent = downloadResponse.getFileData();
				BufferedImage image = ImageIO.read(new ByteArrayInputStream(fileContent));;
				int widthInt = image.getWidth();        // 源图宽   
		        int heightInt = image.getHeight();        // 源图高  
		        
		        double widthSrc = Integer.valueOf(widthInt).doubleValue();
		        double heightSrc = Integer.valueOf(heightInt).doubleValue();
		        
		        double widthMax = 880.00;
		        double heightMax = 520.00;
		        
		        double widthRt = widthMax/widthSrc;
		        double heightRt = heightMax/heightSrc;
		        
		        double rt = 1.00;
		        
		        if(widthRt<1&&heightRt<1){
		        	if(widthRt>=heightRt){
		        		rt = heightRt;
		        	}else{
		        		rt = widthRt;
		        	}
		        }else{
		        	if(widthRt<1){
		        		rt = widthRt;
		        	}
		        	if(heightRt<1){
		        		rt = heightRt;
		        	}
		        }
				
		        Image scaleImage = image.getScaledInstance((int)(widthInt * rt), 
		        		(int)(heightInt * rt), Image.SCALE_DEFAULT);
		        
		        BufferedImage scaleImageBuf = 
		        		new BufferedImage((int)(widthInt * rt), 
		        				(int)(heightInt * rt), BufferedImage.TYPE_INT_RGB); 
		        
		        Graphics2D g = scaleImageBuf.createGraphics();   
		        
		        g.drawImage(scaleImage, 0, 0, null); // 绘制缩小后的图   
		        g.dispose();  
				//输出图片
				response.setContentType("image/jpeg");
				OutputStream out = response.getOutputStream();
				ImageIO.write(scaleImageBuf, "jpeg", out);
				out.close();
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
        
    }
    
    public static void main(String[] args) {
        String path ="E:/tmp/idcard/身份证正面.jpg";
        String szTrackerServersStr="192.16.1.252:22122";
        String[] szTrackerServers = szTrackerServersStr.split("\\|");
        UploadFile uploadFile = UploadFile.getInstance(szTrackerServers);
        UploadFile.UploadResponse response = uploadFile.new UploadResponse();
        response = uploadFile.doUploadFile(path);
        if (response.getUploadResult() == UploadFile.SUCCESS) {
            System.out.println(response.getUploadFileID());
        }
    }
}

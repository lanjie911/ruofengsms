package com.sms.dto.jumeng;

import java.io.Serializable;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Returnsms implements Serializable {
	
	private static final long serialVersionUID = 6458323164605167481L;
	private int result;
	private String desc;
	private String taskid;
	
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getTaskid() {
		return taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	@Override
	public String toString() {
		return "TogetherDream [result=" + result + ", desc=" + desc + ", taskid=" + taskid + "]";
	}
	public static void main(String[] args) {
		String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><returnsms><result>0</result><desc>提交成功</desc><taskid>2017112921520376057</taskid></returnsms>";
		
		try {  
            JAXBContext context = JAXBContext.newInstance(Returnsms.class);  
            Unmarshaller unmarshaller = context.createUnmarshaller();  
            Returnsms sc = (Returnsms)unmarshaller.unmarshal(new StringReader(xmlStr));  
            System.out.println(sc.getResult());  
            System.out.println(sc.getTaskid());  
            System.out.println(sc.getDesc());  
        } catch (JAXBException e) {  
            e.printStackTrace();  
        }  
	}
}
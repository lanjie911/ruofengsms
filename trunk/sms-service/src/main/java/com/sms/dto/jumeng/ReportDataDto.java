package com.sms.dto.jumeng;

import java.io.Serializable;
import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="returnsms")
public class ReportDataDto implements Serializable {
	
	private static final long serialVersionUID = -5543552148837688896L;
	private List<ReportDto> list;
	
	@XmlElement(name = "report")
	public List<ReportDto> getList() {
		return list;
	}
	public void setList(List<ReportDto> list) {
		this.list = list;
	}
	
	/*public static void main(String[] args) {
		String callBackXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><returnsms><report><mobile>18511293080</mobile><taskid>2017112921494976039</taskid><status>0</status><errcode>ok</errcode><receivetime>2017-11-29 21:49:53</receivetime></report><report><mobile>17710477787</mobile><taskid>2017113000235224981</taskid><status>2</status><errcode>UNDELIV</errcode><receivetime>2017-11-30 00:25:00</receivetime></report></returnsms>";
		try {  
            JAXBContext context = JAXBContext.newInstance(ReportDataDto.class);  
            Unmarshaller unmarshaller = context.createUnmarshaller();  
            ReportDataDto sc = (ReportDataDto)unmarshaller.unmarshal(new StringReader(callBackXML)); 
            List<ReportDto> list = sc.getList();
            for(ReportDto dto : list){
            	System.out.println(dto.toString());
            }
        	
        } catch (JAXBException e) {  
            e.printStackTrace();  
        }  
	}*/
}
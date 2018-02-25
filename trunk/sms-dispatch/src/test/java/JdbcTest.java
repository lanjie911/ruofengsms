

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTest {

	public static void main(String[] args) {
		getAll();
	}
	
	private static Integer getAll() {
		StringBuffer mobileStr = new StringBuffer("");
		StringBuffer mobiles_data = new StringBuffer("");
	    Connection conn = getConn();
	    String sql = "select mobiles_data from sms_applay_detail where batch_no = 2017122716212434";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	mobiles_data.append(rs.getString(1)).append(",");
	        }
	        conn.close();
	        
	        Connection tmpConn = getConn();
	        
	        String[] mobiles_dataStr = mobiles_data.toString().split(",");
	        System.out.println("size:" + mobiles_dataStr.length);
	        
	        String queryRecord =  "select mobile from s_plain_send_record where batch_no = 2017122716212434";
	        PreparedStatement pstmt2 = (PreparedStatement)tmpConn.prepareStatement(queryRecord);
	        ResultSet recordRs = pstmt2.executeQuery();
	        
	        StringBuffer plainStr = new StringBuffer();
	        while(recordRs.next()){
	        	plainStr.append(recordRs.getString(1)).append(",");
	        }
	        tmpConn.close();
	        String plainArray = plainStr.toString();
	        for(int i=0;i<mobiles_dataStr.length;i++){
	        	int indexodVal = plainArray.indexOf(mobiles_dataStr[i]);
	        	if(-1 == indexodVal){
	        		if(mobiles_dataStr[i].length() > 11){
	        			int loopNum = mobiles_dataStr[i].length()/11;
	        			for(int k = 0; k<loopNum; k++){
	        				mobileStr.append(mobiles_dataStr[i].substring(0*k, 0*k+11)).append(",");
	        			}
	        		}else{
	        			mobileStr.append(mobiles_dataStr[i]).append(",");
	        		}
	        	}
	        }
	        
	        ForFile sc = new ForFile();
	        sc.createFile("mobile", mobileStr.toString());
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	private static Connection getConn() {
	    String driver = "com.mysql.jdbc.Driver";
	    String url = "jdbc:mysql://172.31.53.33:3306/sms_trade?useSSL=false&allowMultiQueries=true";
	    String username = "smsTradeUser";
	    String password = "SsTU&451004&%";
	    Connection conn = null;
	    try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return conn;
	}
}
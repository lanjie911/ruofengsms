package com.sms.util.datasouce;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		// TODO Auto-generated method stub
		 String dataSouceKey = DynamicDataSourceHolder.getDataSouce();  
	     return dataSouceKey; 
	}

}

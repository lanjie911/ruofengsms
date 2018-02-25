package com.sms.util.datasouce;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		String dataSouceKey = DynamicDataSourceHolder.getDataSouce();
		return dataSouceKey;
	}
}
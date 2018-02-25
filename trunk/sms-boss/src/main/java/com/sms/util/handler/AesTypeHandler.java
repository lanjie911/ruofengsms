package com.sms.util.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sms.entity.AesString;
import com.sms.util.AesUtil;

public class AesTypeHandler extends TypeReference<AesString> implements TypeHandler<AesString> {

	private static final Logger logger = LoggerFactory.getLogger(AesTypeHandler.class);

	public void setNonNullParameter(PreparedStatement ps, int i, AesString parameter, JdbcType jdbcType) throws SQLException {

		// 处理已经有加密值的
		String value = parameter.getCryptoValue(); // 加密值
		if (StringUtils.isNotBlank(value)) { // 已经加过密了
			ps.setString(i, value);
			return;
		}
		// 处理数据未加密的
		String unValue = parameter.getOriginalValue(); // 未加密值
		if (StringUtils.isNotBlank(unValue)) {
			// 加密
			parameter.setCryptoValue(AesUtil.encrypt(unValue));
			ps.setString(i, parameter.getCryptoValue());
			return;
		} else {
			// 没有要加密的值设置null
			try {
				ps.setNull(i, jdbcType.TYPE_CODE);
			} catch (SQLException e) {
				throw new TypeException("Error setting null for parameter #" + i + " with JdbcType " + jdbcType + " . "
						+ "Try setting a different JdbcType for this parameter or a different jdbcTypeForNull configuration property. "
						+ "Cause: " + e, e);
			}
		}
	}

	public AesString getNullableResult(ResultSet rs, String columnName) throws SQLException {

		String value = rs.getString(columnName);
		AesString result = null;
		if (StringUtils.isBlank(value)) {
			result = new AesString("", "");
			return result;
		}
		String decodeValue = "";
		try {
			decodeValue = AesUtil.decrypt(value);
		} catch (Exception e) {
			logger.error("Aes decrypt error:[" + value + "]", e);
		}
		result = new AesString(value, decodeValue);
		return result;
	}

	public AesString getNullableResult(ResultSet rs, int columnIndex) throws SQLException {

		String value = rs.getString(columnIndex);
		AesString result = null;
		if (StringUtils.isBlank(value)) {
			result = new AesString("", "");
			return result;
		}
		String decodeValue = "";
		try {
			decodeValue = AesUtil.decrypt(value);
		} catch (Exception e) {
			logger.error("Aes decrypt error:[" + value + "]", e);
		}
		result = new AesString(value, decodeValue);
		return result;
	}

	public AesString getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {

		String value = cs.getString(columnIndex);
		AesString result = null;
		if (StringUtils.isBlank(value)) {
			result = new AesString("", "");
			return result;
		}
		String decodeValue = "";
		try {
			decodeValue = AesUtil.decrypt(value);
		} catch (Exception e) {
			logger.error("Aes decrypt error:[" + value + "]", e);
		}
		result = new AesString(value, decodeValue);
		return result;
	}

	@Override
	public void setParameter(PreparedStatement ps, int i, AesString parameter, JdbcType jdbcType) throws SQLException {
		if (parameter == null) {
			if (jdbcType == null) {
				throw new TypeException(
						"JDBC requires that the JdbcType must be specified for all nullable parameters.");
			}
			try {
				ps.setNull(i, jdbcType.TYPE_CODE);
			} catch (SQLException e) {
				throw new TypeException("Error setting null for parameter #" + i + " with JdbcType " + jdbcType + " . "
						+ "Try setting a different JdbcType for this parameter or a different jdbcTypeForNull configuration property. "
						+ "Cause: " + e, e);
			}
		} else {
			setNonNullParameter(ps, i, parameter, jdbcType);
		}
	}

	@Override
	public AesString getResult(ResultSet rs, String columnName) throws SQLException {
		AesString result = getNullableResult(rs, columnName);
		if (rs.wasNull()) {
			return new AesString("", "");
		} else {
			return result;
		}
	}

	@Override
	public AesString getResult(ResultSet rs, int columnIndex) throws SQLException {
		AesString result = getNullableResult(rs, columnIndex);
		if (rs.wasNull()) {
			return new AesString("", "");
		} else {
			return result;
		}
	}

	@Override
	public AesString getResult(CallableStatement cs, int columnIndex) throws SQLException {
		AesString result = getNullableResult(cs, columnIndex);
		if (cs.wasNull()) {
			return new AesString("", "");
		} else {
			return result;
		}
	}
}
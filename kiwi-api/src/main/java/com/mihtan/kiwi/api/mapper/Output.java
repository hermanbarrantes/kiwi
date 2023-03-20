package com.mihtan.kiwi.api.mapper;

import java.io.InputStream;
import java.sql.SQLException;

/**
 *
 * @author herman
 */
public interface Output extends Row {

    @Override
    default InputStream getAsciiStream(int parameterIndex) throws SQLException {
        var value = getClob(parameterIndex);
        return wasNull() ? null : value.getAsciiStream();
    }

    @Override
    default InputStream getAsciiStream(String parameterName) throws SQLException {
        var value = getClob(parameterName);
        return wasNull() ? null : value.getAsciiStream();
    }

    @Override
    default InputStream getBinaryStream(int parameterIndex) throws SQLException {
        var value = getBlob(parameterIndex);
        return wasNull() ? null : value.getBinaryStream();
    }

    @Override
    default InputStream getBinaryStream(String parameterName) throws SQLException {
        var value = getBlob(parameterName);
        return wasNull() ? null : value.getBinaryStream();
    }
}

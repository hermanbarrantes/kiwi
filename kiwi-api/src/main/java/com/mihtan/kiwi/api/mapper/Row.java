package com.mihtan.kiwi.api.mapper;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Wrapper;
import java.util.Calendar;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author herman
 */
public interface Row extends Wrapper {

    ResultSetMetaData getMetaData() throws SQLException;

    boolean wasNull() throws SQLException;

    String getString(int columnIndex) throws SQLException;

    String getString(String columnLabel) throws SQLException;

    boolean getBoolean(int columnIndex) throws SQLException;

    boolean getBoolean(String columnLabel) throws SQLException;

    default Optional<Boolean> getOptionalBoolean(int columnIndex) throws SQLException {
        var value = getBoolean(columnIndex);
        return wasNull() ? Optional.empty() : Optional.of(value);
    }

    default Optional<Boolean> getOptionalBoolean(String columnLabel) throws SQLException {
        var value = getBoolean(columnLabel);
        return wasNull() ? Optional.empty() : Optional.of(value);
    }

    byte getByte(int columnIndex) throws SQLException;

    byte getByte(String columnLabel) throws SQLException;

    default Optional<Byte> getOptionalByte(int columnIndex) throws SQLException {
        var value = getByte(columnIndex);
        return wasNull() ? Optional.empty() : Optional.of(value);
    }

    default Optional<Byte> getOptionalByte(String columnLabel) throws SQLException {
        var value = getByte(columnLabel);
        return wasNull() ? Optional.empty() : Optional.of(value);
    }

    short getShort(int columnIndex) throws SQLException;

    short getShort(String columnLabel) throws SQLException;

    default Optional<Short> getOptionalShort(int columnIndex) throws SQLException {
        var value = getShort(columnIndex);
        return wasNull() ? Optional.empty() : Optional.of(value);
    }

    default Optional<Short> getOptionalShort(String columnLabel) throws SQLException {
        var value = getShort(columnLabel);
        return wasNull() ? Optional.empty() : Optional.of(value);
    }

    int getInt(int columnIndex) throws SQLException;

    int getInt(String columnLabel) throws SQLException;

    default Optional<Integer> getOptionalInteger(int columnIndex) throws SQLException {
        var value = getInt(columnIndex);
        return wasNull() ? Optional.empty() : Optional.of(value);
    }

    default Optional<Integer> getOptionalInteger(String columnLabel) throws SQLException {
        var value = getInt(columnLabel);
        return wasNull() ? Optional.empty() : Optional.of(value);
    }

    long getLong(int columnIndex) throws SQLException;

    long getLong(String columnLabel) throws SQLException;

    default Optional<Long> getOptionalLong(int columnIndex) throws SQLException {
        var value = getLong(columnIndex);
        return wasNull() ? Optional.empty() : Optional.of(value);
    }

    default Optional<Long> getOptionalLong(String columnLabel) throws SQLException {
        var value = getLong(columnLabel);
        return wasNull() ? Optional.empty() : Optional.of(value);
    }

    float getFloat(int columnIndex) throws SQLException;

    float getFloat(String columnLabel) throws SQLException;

    default Optional<Float> getOptionalFloat(int columnIndex) throws SQLException {
        var value = getFloat(columnIndex);
        return wasNull() ? Optional.empty() : Optional.of(value);
    }

    default Optional<Float> getOptionalFloat(String columnLabel) throws SQLException {
        var value = getFloat(columnLabel);
        return wasNull() ? Optional.empty() : Optional.of(value);
    }

    double getDouble(int columnIndex) throws SQLException;

    double getDouble(String columnLabel) throws SQLException;

    default Optional<Double> getOptionalDouble(int columnIndex) throws SQLException {
        var value = getDouble(columnIndex);
        return wasNull() ? Optional.empty() : Optional.of(value);
    }

    default Optional<Double> getOptionalDouble(String columnLabel) throws SQLException {
        var value = getDouble(columnLabel);
        return wasNull() ? Optional.empty() : Optional.of(value);
    }

    byte[] getBytes(int columnIndex) throws SQLException;

    byte[] getBytes(String columnLabel) throws SQLException;

    Date getDate(int columnIndex) throws SQLException;

    Date getDate(String columnLabel) throws SQLException;

    Date getDate(int columnIndex, Calendar cal) throws SQLException;

    Date getDate(String columnLabel, Calendar cal) throws SQLException;

    Time getTime(int columnIndex) throws SQLException;

    Time getTime(String columnLabel) throws SQLException;

    Time getTime(int columnIndex, Calendar cal) throws SQLException;

    Time getTime(String columnLabel, Calendar cal) throws SQLException;

    Timestamp getTimestamp(int columnIndex) throws SQLException;

    Timestamp getTimestamp(String columnLabel) throws SQLException;

    Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException;

    Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException;

    InputStream getAsciiStream(int columnIndex) throws SQLException;

    InputStream getAsciiStream(String columnLabel) throws SQLException;

    InputStream getBinaryStream(int columnIndex) throws SQLException;

    InputStream getBinaryStream(String columnLabel) throws SQLException;

    Object getObject(int columnIndex) throws SQLException;

    Object getObject(String columnLabel) throws SQLException;

    Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException;

    Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException;

    <T> T getObject(int columnIndex, Class<T> type) throws SQLException;

    <T> T getObject(String columnLabel, Class<T> type) throws SQLException;

    Reader getCharacterStream(int columnIndex) throws SQLException;

    Reader getCharacterStream(String columnLabel) throws SQLException;

    BigDecimal getBigDecimal(int columnIndex) throws SQLException;

    BigDecimal getBigDecimal(String columnLabel) throws SQLException;

    Blob getBlob(int columnIndex) throws SQLException;

    Blob getBlob(String columnLabel) throws SQLException;

    Clob getClob(int columnIndex) throws SQLException;

    Clob getClob(String columnLabel) throws SQLException;

    URL getURL(int columnIndex) throws SQLException;

    URL getURL(String columnLabel) throws SQLException;
}

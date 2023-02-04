package com.mihtan.kiwi.api.statement;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;

/**
 *
 * @author herman
 */
@FunctionalInterface
public interface Statement<T extends Statement<T>> {

    T setParamenter(ParameterSetter parameterSetter);

    default T setBoolean(boolean value) {
        return setParamenter((s, i) -> s.setBoolean(i, value));
    }

    default T setBoolean(Boolean value, int type) {
        return setParamenter((s, i) -> {
            if (null == value) {
                s.setNull(i, type);
            } else {
                s.setBoolean(i, value);
            }
        });
    }

    default T setBoolean(Boolean value) {
        return setBoolean(value, Types.BOOLEAN);
    }

    default T setByte(byte value) {
        return setParamenter((s, i) -> s.setByte(i, value));
    }

    default T setByte(Byte value, int type) {
        return setParamenter((s, i) -> {
            if (null == value) {
                s.setNull(i, type);
            } else {
                s.setByte(i, value);
            }
        });
    }

    default T setByte(Byte value) {
        return setByte(value, Types.TINYINT);
    }

    default T setShort(short value) {
        return setParamenter((s, i) -> s.setShort(i, value));
    }

    default T setShort(Short value, int type) {
        return setParamenter((s, i) -> {
            if (null == value) {
                s.setNull(i, type);
            } else {
                s.setShort(i, value);
            }
        });
    }

    default T setShort(Short value) {
        return setShort(value, Types.SMALLINT);
    }

    default T setInt(int value) {
        return setParamenter((s, i) -> s.setInt(i, value));
    }

    default T setInt(Integer value, int type) {
        return setParamenter((s, i) -> {
            if (null == value) {
                s.setNull(i, type);
            } else {
                s.setInt(i, value);
            }
        });
    }

    default T setInt(Integer value) {
        return setInt(value, Types.INTEGER);
    }

    default T setLong(long value) {
        return setParamenter((s, i) -> s.setLong(i, value));
    }

    default T setLong(Long value, int type) {
        return setParamenter((s, i) -> {
            if (null == value) {
                s.setNull(i, type);
            } else {
                s.setLong(i, value);
            }
        });
    }

    default T setLong(Long value) {
        return setLong(value, Types.BIGINT);
    }

    default T setFloat(float value) {
        return setParamenter((s, i) -> s.setFloat(i, value));
    }

    default T setFloat(Float value, int type) {
        return setParamenter((s, i) -> {
            if (null == value) {
                s.setNull(i, type);
            } else {
                s.setFloat(i, value);
            }
        });
    }

    default T setFloat(Float value) {
        return setFloat(value, Types.REAL);
    }

    default T setDouble(double value) {
        return setParamenter((s, i) -> s.setDouble(i, value));
    }

    default T setDouble(Double value, int type) {
        return setParamenter((s, i) -> {
            if (null == value) {
                s.setNull(i, type);
            } else {
                s.setDouble(i, value);
            }
        });
    }

    default T setDouble(Double value) {
        return setDouble(value, Types.DOUBLE);
    }

    default T setBigDecimal(BigDecimal value, int type) {
        return setParamenter((s, i) -> {
            if (null == value) {
                s.setNull(i, type);
            } else {
                s.setBigDecimal(i, value);
            }
        });
    }

    default T setBigDecimal(BigDecimal value) {
        return setBigDecimal(value, Types.NUMERIC);
    }

    default T setString(String value, int type) {
        return setParamenter((s, i) -> {
            if (null == value) {
                s.setNull(i, type);
            } else {
                s.setString(i, value);
            }
        });
    }

    default T setString(String value) {
        return setString(value, Types.VARCHAR);
    }

    default T setDate(Date value, int type) {
        return setParamenter((s, i) -> {
            if (null == value) {
                s.setNull(i, type);
            } else {
                s.setDate(i, value);
            }
        });
    }

    default T setDate(Date value) {
        return setDate(value, Types.DATE);
    }

    default T setTime(Time value, int type) {
        return setParamenter((s, i) -> {
            if (null == value) {
                s.setNull(i, type);
            } else {
                s.setTime(i, value);
            }
        });
    }

    default T setTime(Time value) {
        return setTime(value, Types.TIME);
    }

    default T setTimestamp(Timestamp value, int type) {
        return setParamenter((s, i) -> {
            if (null == value) {
                s.setNull(i, type);
            } else {
                s.setTimestamp(i, value);
            }
        });
    }

    default T setTimestamp(Timestamp value) {
        return setTimestamp(value, Types.TIMESTAMP);
    }

    default T setObject(Object value, int type) {
        return setParamenter((s, i) -> {
            if (null == value) {
                s.setNull(i, type);
            } else {
                s.setObject(i, value, type);
            }
        });
    }

    default T setObject(Object value) {
        return setObject(value, Types.OTHER);
    }

    default T setClob(Clob value, int type) {
        return setParamenter((s, i) -> {
            if (null == value) {
                s.setNull(i, type);
            } else {
                s.setClob(i, value);
            }
        });
    }

    default T setClob(Clob value) {
        return setClob(value, Types.CLOB);
    }

    default T setClob(Reader value, int type) {
        return setParamenter((s, i) -> {
            if (null == value) {
                s.setNull(i, type);
            } else {
                s.setClob(i, value);
            }
        });
    }

    default T setClob(Reader value) {
        return setClob(value, Types.CLOB);
    }

    default T setBlob(Blob value, int type) {
        return setParamenter((s, i) -> {
            if (null == value) {
                s.setNull(i, type);
            } else {
                s.setBlob(i, value);
            }
        });
    }

    default T setBlob(Blob value) {
        return setBlob(value, Types.BLOB);
    }

    default T setBlob(InputStream value, int type) {
        return setParamenter((s, i) -> {
            if (null == value) {
                s.setNull(i, type);
            } else {
                s.setBlob(i, value);
            }
        });
    }

    default T setBlob(InputStream value) {
        return setBlob(value, Types.BLOB);
    }

    default T setAsciiStream(InputStream value, int type) {
        return setParamenter((s, i) -> {
            if (null == value) {
                s.setNull(i, type);
            } else {
                s.setAsciiStream(i, value);
            }
        });
    }

    default T setAsciiStream(InputStream value) {
        return setAsciiStream(value, Types.LONGVARCHAR);
    }

    default T setBinaryStream(InputStream value, int type) {
        return setParamenter((s, i) -> {
            if (null == value) {
                s.setNull(i, type);
            } else {
                s.setBinaryStream(i, value);
            }
        });
    }

    default T setBinaryStream(InputStream value) {
        return setBinaryStream(value, Types.LONGVARBINARY);
    }

    default T setCharacterStream(Reader value, int type) {
        return setParamenter((s, i) -> {
            if (null == value) {
                s.setNull(i, type);
            } else {
                s.setCharacterStream(i, value);
            }
        });
    }

    default T setCharacterStream(Reader value) {
        return setCharacterStream(value, Types.LONGVARCHAR);
    }

}

package com.mihtan.kiwi.api.statement;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;

/**
 *
 * @author herman
 */
public interface Call extends Statement<Call> {

    void execute();

    Call setParamenter(CallParameterSetter parameterSetter);

    default Call setBoolean(String name, boolean value) {
        return setParamenter(s -> s.setBoolean(name, value));
    }

    default Call setBoolean(String name, Boolean value, int type) {
        return setParamenter(s -> {
            if (null == value) {
                s.setNull(name, type);
            } else {
                s.setBoolean(name, value);
            }
        });
    }

    default Call setBoolean(String name, Boolean value) {
        return setBoolean(name, value, Types.BOOLEAN);
    }

    default Call setByte(String name, byte value) {
        return setParamenter(s -> s.setByte(name, value));
    }

    default Call setByte(String name, Byte value, int type) {
        return setParamenter(s -> {
            if (null == value) {
                s.setNull(name, type);
            } else {
                s.setByte(name, value);
            }
        });
    }

    default Call setByte(String name, Byte value) {
        return setByte(name, value, Types.TINYINT);
    }

    default Call setShort(String name, short value) {
        return setParamenter(s -> s.setShort(name, value));
    }

    default Call setShort(String name, Short value, int type) {
        return setParamenter(s -> {
            if (null == value) {
                s.setNull(name, type);
            } else {
                s.setShort(name, value);
            }
        });
    }

    default Call setShort(String name, Short value) {
        return setShort(name, value, Types.SMALLINT);
    }

    default Call setInt(String name, int value) {
        return setParamenter(s -> s.setInt(name, value));
    }

    default Call setInt(String name, Integer value, int type) {
        return setParamenter(s -> {
            if (null == value) {
                s.setNull(name, type);
            } else {
                s.setInt(name, value);
            }
        });
    }

    default Call setInt(String name, Integer value) {
        return setInt(name, value, Types.INTEGER);
    }

    default Call setLong(String name, long value) {
        return setParamenter(s -> s.setLong(name, value));
    }

    default Call setLong(String name, Long value, int type) {
        return setParamenter(s -> {
            if (null == value) {
                s.setNull(name, type);
            } else {
                s.setLong(name, value);
            }
        });
    }

    default Call setLong(String name, Long value) {
        return setLong(name, value, Types.BIGINT);
    }

    default Call setFloat(String name, float value) {
        return setParamenter(s -> s.setFloat(name, value));
    }

    default Call setFloat(String name, Float value, int type) {
        return setParamenter(s -> {
            if (null == value) {
                s.setNull(name, type);
            } else {
                s.setFloat(name, value);
            }
        });
    }

    default Call setFloat(String name, Float value) {
        return setFloat(name, value, Types.REAL);
    }

    default Call setDouble(String name, double value) {
        return setParamenter(s -> s.setDouble(name, value));
    }

    default Call setDouble(String name, Double value, int type) {
        return setParamenter(s -> {
            if (null == value) {
                s.setNull(name, type);
            } else {
                s.setDouble(name, value);
            }
        });
    }

    default Call setDouble(String name, Double value) {
        return setDouble(name, value, Types.DOUBLE);
    }

    default Call setBigDecimal(String name, BigDecimal value, int type) {
        return setParamenter(s -> {
            if (null == value) {
                s.setNull(name, type);
            } else {
                s.setBigDecimal(name, value);
            }
        });
    }

    default Call setBigDecimal(String name, BigDecimal value) {
        return setBigDecimal(name, value, Types.NUMERIC);
    }

    default Call setString(String name, String value, int type) {
        return setParamenter(s -> {
            if (null == value) {
                s.setNull(name, type);
            } else {
                s.setString(name, value);
            }
        });
    }

    default Call setString(String name, String value) {
        return setString(name, value, Types.VARCHAR);
    }

    default Call setDate(String name, Date value, int type) {
        return setParamenter(s -> {
            if (null == value) {
                s.setNull(name, type);
            } else {
                s.setDate(name, value);
            }
        });
    }

    default Call setDate(String name, Date value) {
        return setDate(name, value, Types.DATE);
    }

    default Call setTime(String name, Time value, int type) {
        return setParamenter(s -> {
            if (null == value) {
                s.setNull(name, type);
            } else {
                s.setTime(name, value);
            }
        });
    }

    default Call setTime(String name, Time value) {
        return setTime(name, value, Types.TIME);
    }

    default Call setTimestamp(String name, Timestamp value, int type) {
        return setParamenter(s -> {
            if (null == value) {
                s.setNull(name, type);
            } else {
                s.setTimestamp(name, value);
            }
        });
    }

    default Call setTimestamp(String name, Timestamp value) {
        return setTimestamp(name, value, Types.TIMESTAMP);
    }

    default Call setObject(String name, Object value, int type) {
        return setParamenter(s -> {
            if (null == value) {
                s.setNull(name, type);
            } else {
                s.setObject(name, value, type);
            }
        });
    }

    default Call setObject(String name, Object value) {
        return setObject(name, value, Types.OTHER);
    }

    default Call setOutParamenter(String name, int type) {
        return setParamenter(s -> s.registerOutParameter(name, type));
    }

    default Call setOutParamenter(int type) {
        return setParamenter((s, i) -> {
            if (s instanceof CallableStatement) {
                CallableStatement call = (CallableStatement) s;
                call.registerOutParameter(i, type);
            } else {
                throw new StatementException("Not a CallableStatement");
            }
        });
    }
}

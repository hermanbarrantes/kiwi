package com.mihtan.kiwi.api.handler;

import com.mihtan.kiwi.api.statement.Call;
import com.mihtan.kiwi.api.statement.Query;
import com.mihtan.kiwi.api.statement.Update;
import java.sql.Wrapper;

/**
 *
 * @author herman
 */
public interface Handler extends Wrapper {

    Query query(String sql);

    Update update(String sql);

    Call call(String sql);
}

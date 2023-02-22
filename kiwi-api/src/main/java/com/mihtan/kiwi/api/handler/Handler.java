package com.mihtan.kiwi.api.handler;

import com.mihtan.kiwi.api.statement.Call;
import com.mihtan.kiwi.api.statement.Query;
import com.mihtan.kiwi.api.statement.Update;

/**
 *
 * @author herman
 */
public interface Handler {

    Query query(String sql);

    Update update(String sql);

    Call call(String sql);
}

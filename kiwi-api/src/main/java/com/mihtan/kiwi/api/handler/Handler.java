package com.mihtan.kiwi.api.handler;

import com.mihtan.kiwi.api.statement.Call;
import com.mihtan.kiwi.api.statement.Query;
import com.mihtan.kiwi.api.statement.Update;

/**
 *
 * @author herman
 */
public interface Handler {

    Query createQuery(String sql);

    Update createUpdate(String sql);

    Call createCall(String sql);
}

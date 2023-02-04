package com.mihtan.kiwi.api.statement;

import com.mihtan.kiwi.api.mapper.RowMapper;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author herman
 */
public interface Query extends Statement<Query> {

    public <T> Optional<T> one(RowMapper<T> rowMapper);

    public <T> Optional<T> first(RowMapper<T> rowMapper);

    public <T> List<T> list(RowMapper<T> rowMapper);
}

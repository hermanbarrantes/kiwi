package com.mihtan.kiwi.api.statement;

/**
 *
 * @author herman
 */
public interface Update extends Statement<Update> {

    int execute();

    <T> T executeWithKey(String name, Class<T> clazz);

    <T> T executeWithKey(int index, Class<T> clazz);
}

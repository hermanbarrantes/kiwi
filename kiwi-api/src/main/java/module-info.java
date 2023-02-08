module com.mihtan.kiwi.api {
    requires transitive java.sql;
    exports com.mihtan.kiwi.api;
    exports com.mihtan.kiwi.api.handler;
    exports com.mihtan.kiwi.api.mapper;
    exports com.mihtan.kiwi.api.result;
    exports com.mihtan.kiwi.api.statement;
    exports com.mihtan.kiwi.api.transaction;
}

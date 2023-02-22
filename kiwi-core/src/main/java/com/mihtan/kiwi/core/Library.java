package com.mihtan.kiwi.core;

import java.sql.Types;
import java.time.LocalDate;
import java.util.List;

public class Library {

    public boolean someLibraryMethod() {
        return true;
    }

    public static void main(String[] args) {
        Kiwi
                .withConnectionFactory(null)
                .withHandlerFactory(null)
                .withTransactionManagerFactory(null)
                .build();
        Kiwi kiwi = Kiwi.create(() -> null);
        List<String> list = kiwi.call(handle -> {
            Long key = handle
                    .update("INSERT INTO TEST(NAME) VALUES (?)")
                    .setString("Hola")
                    .executeAndGetKey();
            handle
                    .call("{call SP(?)}")
                    .setString("Hola")
                    .setBoolean("ACTIVE", true)
                    .setOutParamenter("OUT", Types.VARCHAR)
                    .execute();
            return handle
                    .query("SELECT NAME FROM TEST WHERE NAME LIKE ?")
                    .setInt(1)
                    .setString("Hola", Types.VARCHAR)
                    .setObject(LocalDate.now(), Types.TIMESTAMP)
                    .list((rs) -> rs.getString("NAME"));
        });
    }
}

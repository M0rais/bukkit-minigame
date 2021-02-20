package pt.diogo.github.database

import com.zaxxer.hikari.HikariDataSource

class Hikari(host: String, port: String, database: String, user: String, password: String) {

    val source = HikariDataSource()

    init {
        source.jdbcUrl = "jdbc:mysql://$host:$port/$database";
        source.username = user;
        source.password = password;
        source.addDataSourceProperty("autoReconnect", "true");
    }

}
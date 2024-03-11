package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class Main {
    public static void main(String[] args) throws SQLException {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Денис","Петров", (byte) 15);
        userService.saveUser("Илья","Иванов", (byte) 22);
        userService.saveUser("Константин","Архипов", (byte) 19);
        userService.saveUser("Елена","Васечкина", (byte) 18);

        userService.getAllUsers().forEach(System.out::print);

        //userService.cleanUsersTable();
        //userService.dropUsersTable();
    }

}

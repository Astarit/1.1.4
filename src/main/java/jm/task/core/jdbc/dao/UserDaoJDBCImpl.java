package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import net.bytebuddy.implementation.Implementation;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {

//        String sql = """
//                CREATE TABLE IF NOT EXISTS users (id BIGSERIAL PRIMARY KEY,
//                name VARCHAR(55) NOT NULL ,
//                lastname VARCHAR(55) NOT NULL ,
//                age INT NOT NULL );
//                """;
//        try (Connection connection = Util.open(); Statement statement = connection.createStatement()) {
//            if (statement.execute(sql)) {
//                System.out.println("Таблица успешно создана");
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public void dropUsersTable() {
        String sql = """
                DROP TABLE IF EXISTS users;
                """;
        try (Connection connection = Util.open(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица успешно удалена");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {


//        String SQL = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
//
//        try (Connection connection = Util.open(); PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
//
//            preparedStatement.setString(1, name);
//            preparedStatement.setString(2, lastName);
//            preparedStatement.setByte(3, age);
//            preparedStatement.executeUpdate();
//
//            System.out.println("Пользователь с именем " + name + " добавлен в базу");
//        } catch (SQLException e) {
//            e.printStackTrace(); // Обработка исключений
//            System.out.println("Ошибка при добавлении пользователя");
//        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id=?";
        try (Connection connection = Util.open(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Пользователь с id = " + id + " успешно удален из таблицы");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось удалить пользователя с id = " + id);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String SQL = "SELECT id, name, lastname, age FROM users";
        try (Connection connection = Util.open(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge((byte) resultSet.getInt("age"));
                userList.add(user);
            }
            System.out.println("\n Список пользователей успешно получен \n");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось получить список пользователей");
        }
        return userList;
    }

    public void cleanUsersTable() {
        String sql = """
                TRUNCATE TABLE users;

                ALTER SEQUENCE users_id_seq
                RESTART WITH 1
                """;
        try (Connection connection = Util.open(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("\nТаблица успешно очищена\n");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось очистить таблицу");
        }
    }
}


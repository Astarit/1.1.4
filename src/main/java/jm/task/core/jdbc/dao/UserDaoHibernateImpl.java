package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;

import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS users (" +
                                   "id SERIAL PRIMARY KEY NOT NULL ," +
                                   "name VARCHAR(255) NOT NULL , " +
                                   "lastName VARCHAR(255) NOT NULL , " +
                                   "age SMALLINT NOT NULL)"
            ).executeUpdate();
            transaction.commit();
            System.out.println("Таблица добавлена");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();}
            throw new RuntimeException("Ошибка при создания таблицы");
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
           transaction= session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
            transaction.commit();
            System.out.println("Таблица удалена");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();}
            throw new RuntimeException("Ошибка при удалении таблицы");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.persist(user);
            transaction.commit();
            System.out.println("Пользователь " + name + " успешно добавлен");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();}
            throw new RuntimeException("Ошибка при добавлении пользователя");
        }

    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
            }
            transaction.commit();
            System.out.println("Пользователь успешно удален");
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();}
            throw new RuntimeException("Возникла ошибка при удаления пользователя");
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User", User.class);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Возникла ошибка при попытке получения списка пользоветелей");
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
             session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();}
            throw new RuntimeException("Не удалось очистить таблицу");
        }
    }
}

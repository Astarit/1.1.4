package jm.task.core.jdbc.dao;

import jakarta.persistence.Table;
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
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.createNativeQuery("CREATE TABLE IF NOT EXISTS users (" +
                                   "id SERIAL PRIMARY KEY NOT NULL ," +
                                   "name VARCHAR(255) NOT NULL , " +
                                   "lastName VARCHAR(255) NOT NULL , " +
                                   "age SMALLINT NOT NULL)"
            ).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица добавлена");

        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создания таблицы");
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession();) {
            Transaction transaction = session.beginTransaction();

            session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();

            transaction.commit();
            session.close();
            System.out.println("Таблица удалена");
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при удалении таблицы");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()){

            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.persist(user);
            session.getTransaction().commit();
            System.out.println("Пользователь " + name + " успешно добавлен");
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при добавлении пользователя");
        }

    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
            }
            session.getTransaction().commit();
            System.out.println("Пользователь успешно удален");
        } catch (Exception e) {
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
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Не удалось очистить таблицу");
        }
    }
}

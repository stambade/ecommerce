package com.tomtom.ecommerce.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tomtom.ecommerce.entities.User;
import com.tomtom.ecommerce.exceptions.EcommerceException;

@Component
public class UserDaoImpl implements IUserDao {

	Logger logger = LogManager.getLogger(UserDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public User getUserById(Serializable id) throws EcommerceException {
		Session session = null;
		User s = null;
		try {
			session = sessionFactory.openSession();
			session.getTransaction().begin();
			s = (User) session.get(User.class, id);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			throw new EcommerceException(he.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return s;
	}

	@Override
	public Long save(User user) throws EcommerceException {
		Session session = null;
		Long id = null;
		try {
			session = sessionFactory.openSession();
			session.getTransaction().begin();
			id = (Long) session.save(user);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			throw new EcommerceException(he.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsers() throws EcommerceException {
		Session session = null;
		List<User> users = null;
		try {
			session = sessionFactory.openSession();
			session.getTransaction().begin();
			users = (List<User>) session.createQuery("From User").getResultList();
			for (User user : users) {
				user.setProducts(null);
			}
			session.getTransaction().commit();
		} catch (HibernateException he) {
			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			throw new EcommerceException(he.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return users;
	}

}

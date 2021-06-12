package com.tomtom.ecommerce.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tomtom.ecommerce.entities.IGenericVo;
import com.tomtom.ecommerce.entities.Order;
import com.tomtom.ecommerce.exceptions.EcommerceException;

@Component
@SuppressWarnings({ "rawtypes", "unchecked" })
public class EcommerceDaoImpl implements IEcommerceDao {

	Logger logger = LogManager.getLogger(EcommerceDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public IGenericVo getById(Class cls, Serializable id) throws EcommerceException {
		Session session = null;
		IGenericVo obj = null;
		try {
			session = sessionFactory.openSession();
			session.getTransaction().begin();
			obj = (IGenericVo) session.get(cls, id);
			session.getTransaction().commit();
		} catch (HibernateException he) {
			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			throw new EcommerceException(he.getMessage());
		}

		finally {
			if (session != null) {
				session.close();
			}
		}
		return obj;
	}

	@Override
	public List<IGenericVo> getAll(Class cls) throws EcommerceException {
		Session session = null;
		List<IGenericVo> objs = null;
		try {
			session = sessionFactory.openSession();
			session.getTransaction().begin();
			objs = (List<IGenericVo>) session.createQuery("From " + cls.getName()).getResultList();
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
		return objs;
	}

	@Override
	public List<IGenericVo> getAllByKeyValue(Class cls, String key, String value) throws EcommerceException {

		Session session = null;
		List<IGenericVo> products = null;
		try {
			session = sessionFactory.openSession();
			session.getTransaction().begin();
			products = (List<IGenericVo>) session
					.createQuery("From " + cls.getName() + " where " + " " + key + "=:" + key).setParameter(key, value)
					.list();
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
		return products;
	}

	@Override
	public Long save(IGenericVo s) throws EcommerceException {
		Session session = null;
		Long id = null;
		try {
			session = sessionFactory.openSession();
			session.getTransaction().begin();
			id = (Long) session.save(s);
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

	@Override
	public List<Order> getAllOrdersOfUser(long userId) {
		// TODO Auto-generated method stub
		Session session = null;
		List<Object> objects = null;
		List<Order> orders = null;
		try {
			session = sessionFactory.openSession();
			session.getTransaction().begin();

			String hql = "From Order where o.user.id=:userId";
			objects = session.createQuery(hql).setParameter("userId", userId).list();

			// if list not null
			orders = new ArrayList<Order>();
			for (Object obj : objects) {
				Order o = (Order) obj;
				o.getProducts();
				o.setUser(null);// if needed only
			}

			session.getTransaction().commit();
		} catch (HibernateException he) {
			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}
			throw new EcommerceException(he.getMessage());
		}

		finally {
			if (session != null) {
				session.close();
			}
		}
		return orders;
	}
}

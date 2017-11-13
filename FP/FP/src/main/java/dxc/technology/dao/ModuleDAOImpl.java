package dxc.technology.dao;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dxc.technology.entity.Module;

@Repository
@Transactional
public class ModuleDAOImpl implements ModuleDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void insert(Module module) {
		getSessionFactory().getCurrentSession().saveOrUpdate(module);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<String> getListKey() {
		return (ArrayList<String>) getSessionFactory().getCurrentSession()
				.createQuery("select key from " + Module.class.getName()).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Integer> getIdModule() {
		Query q = getSessionFactory().getCurrentSession().createQuery("select id from " + Module.class.getName());
		ArrayList<Integer> arrList = (ArrayList<Integer>) q.list();
		return arrList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Integer> getIdModule(String key) {
		Query q = getSessionFactory().getCurrentSession()
				.createQuery("select id from " + Module.class.getName() + " where key = :api_key");
		q.setParameter("api_key", key);
		ArrayList<Integer> arrList = (ArrayList<Integer>) q.list();
		return arrList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int findKey(String key) {
		int check = 0;
		Query q = getSessionFactory().getCurrentSession()
				.createQuery("from " + Module.class.getName() + " where key =:key");
		q.setParameter("key", key);
		ArrayList<String> listKey = (ArrayList<String>) q.list();
		if (listKey.size() == 0) {
			check = 1;
		}
		return check;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}

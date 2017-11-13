package dxc.technology.dao;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dxc.technology.entity.Project;

@Repository
@Transactional
public class ProjectDAOImpl implements ProjectDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void insert(Project project) {
		getSessionFactory().getCurrentSession().saveOrUpdate(project);
	}

	@Override
	public void update(Project project) {
		Query q = getSessionFactory().getCurrentSession()
				.createQuery("update " + Project.class.getName() + " set name = :name where id = :id");
		q.setParameter("id", project.getId());
		q.setParameter("name", project.getName());
		q.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Project> getAll() {
		return (ArrayList<Project>) getSessionFactory().getCurrentSession().createQuery("from project").list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Integer> getIdProject() {
		return (ArrayList<Integer>) getSessionFactory().getCurrentSession().createQuery("select id from project")
				.list();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}

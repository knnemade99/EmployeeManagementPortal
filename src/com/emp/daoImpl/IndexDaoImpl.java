package com.emp.daoImpl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.emp.dao.IndexDao;
import com.emp.entity.User;
import com.emp.entity.UserCredential;
import com.emp.util.EmailAPI;

@Component("indexDao")
public class IndexDaoImpl implements IndexDao {
	

	public SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();

	@Autowired
	EmailAPI email;

	@Override
	public ResponseEntity<String> unlockEmployee(String username) {
		ResponseEntity<String> responseEntity=new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		
		Session session=sessionFactory.openSession();
		session.beginTransaction();
		
		if(username!=null){
			
			/* get User Object */
			User user=null;
			String hql="from user where username = '"+username+"'";
			Query q=session.createQuery(hql);
			if(!q.list().isEmpty()){
				user=(User)q.list().get(0);
						System.out.println(user);
				/* Lock user */
				user.setLockStatus("locked");
				session.update(user);

				responseEntity=new ResponseEntity<String>(HttpStatus.OK);
			}
			else{
				responseEntity=new ResponseEntity<String>(HttpStatus.NOT_FOUND);
			}
		}
		else{
			responseEntity=new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}
		session.getTransaction().commit();
		session.close();
		return responseEntity;
	}
}

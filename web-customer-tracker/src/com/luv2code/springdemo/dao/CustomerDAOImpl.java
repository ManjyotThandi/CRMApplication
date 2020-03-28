package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	// inject session factory (defined in xml) so we can use it here
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	// No need for transactional here. It is in service layer now.. in case we want to use multiple dao methods in one transaction (in order to rollback)
	public List<Customer> getCustomers() {

		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// create a query using HQL...sort by last name
		Query<Customer> theQuery = currentSession.createQuery("from Customer order by lastName", Customer.class);
		
		// get the list of customers from the query by executing it
		List<Customer> customers = theQuery.getResultList();
		
		// return the list of customers
		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {
		
		// transaction is in service layer now.
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// save it in db.... use saveOrupdate.. if the object
		// (theCustomer) has an id or null, save, else update. Built into hibernate to
		// check the primary key(id) of object
		currentSession.saveOrUpdate(theCustomer);
		
	}

	@Override
	public Customer getCustomer(int theId) {
		
		// get the current hibernate session (created in xml only once)
		Session currentSession = sessionFactory.getCurrentSession();
		
		// retrieve/read from db using that primary key. No need to create query, as hibernate as built in if you have primary key (? im pretty sure).
		// When we created query we wanted list of ALL
		Customer theCustomer = currentSession.get(Customer.class, theId);
		
		return theCustomer;
	}

	@Override
	public void deleteCustomer(int theId) {
		// get current session
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query theQuery = currentSession.createQuery("delete from Customer where id=:customerId");
		
		theQuery.setParameter("customerId",theId);
		
		theQuery.executeUpdate();
		
	}

}

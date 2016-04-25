package br.com.concrete.identity.user.dao;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.concrete.identity.user.domain.User;

@Repository
public class UserDAO implements Users {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	@Override
	public void save(User user) {
		em.persist(user);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		try {
			User user = em.createQuery("from User where email = :email", User.class)
					.setParameter("email", email.toLowerCase()).getSingleResult();
			return Optional.of(user);
		} catch (NoResultException e) {
			return Optional.empty();			
		}
	}


}

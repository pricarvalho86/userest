package br.com.concrete.identity.user;

import javax.persistence.EntityManager;
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

}

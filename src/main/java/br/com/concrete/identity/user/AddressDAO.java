package br.com.concrete.identity.user;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.concrete.identity.user.domain.Address;

@Repository
public class AddressDAO implements Addresses {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	@Override
	public void save(Address address) {
		em.persist(address);
	}

}

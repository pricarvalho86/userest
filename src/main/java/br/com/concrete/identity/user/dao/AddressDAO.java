package br.com.concrete.identity.user.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.concrete.identity.user.domain.Address;
import br.com.concrete.identity.user.domain.User;

@Repository
public class AddressDAO implements Addresses {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	@Override
	public void save(Address address) {
		em.persist(address);
	}

	@Override
	public List<Address> listAllBy(User user) {
		List<Address> adresses = em.createQuery("from Address where user = :user", Address.class)
				.setParameter("user", user).getResultList();
		return adresses;
	}

}

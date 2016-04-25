package br.com.concrete.identity.user.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.concrete.identity.user.domain.Token;

@Repository
public class TokenDAO implements Tokens {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	@Override
	public void save(Token token) {
		em.persist(token);
	}

	@Override
	public Optional<Token> findByCode(String tokenCode) {
		try {
			Token token = em.createQuery("from Token where code = :token order by id desc", Token.class)
<<<<<<< HEAD
					.setParameter("token", tokenCode).getSingleResult();
=======
					.setParameter("token", tokenCode).setFirstResult(0).getSingleResult();
>>>>>>> b08a34698bc5bc88c0ddaf8a488a9649d10570f2
			return Optional.of(token);
		} catch (NoResultException e) {
			return Optional.empty();			
		}
	}

}

package br.com.concrete.identity.user;

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
			Token token = em.createQuery("from Token where code = :token", Token.class)
					.setParameter("token", tokenCode).getSingleResult();
			return Optional.of(token);
		} catch (NoResultException e) {
			return Optional.empty();			
		}
	}

}

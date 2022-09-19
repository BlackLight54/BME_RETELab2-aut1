package hu.bme.aut.retelab2.repository;

import hu.bme.aut.retelab2.domain.Ad;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AdRepository {

	@PersistenceContext
	EntityManager em;

	@Transactional
	public Ad save(Ad ad) {
		return em.merge(ad);
	}

	public List<Ad> findAll() {
		return em.createQuery(
				"SELECT a FROM Ad a",
				Ad.class
		).getResultList();

	}
}

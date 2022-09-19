package hu.bme.aut.retelab2.repository;

import hu.bme.aut.retelab2.domain.Ad;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Slf4j
@EnableScheduling
public class AdRepository {

	@PersistenceContext
	EntityManager em;

	@Transactional
	public Ad save(Ad ad) {
		return em.merge(ad);
	}

	@Transactional
	public Ad modify(Ad ad) throws Exception {
		if (ad.getId() == null) {
			throw new NotFoundException("Id is null");
		}
		if (em.find(
				Ad.class,
				ad.getId()
		) == null) {
			throw new NotFoundException("No such ad");
		}
		if (ad.getSecret() == null) {
			throw new NotFoundException("Secret is null");
		}
		if (!ad.getSecret().equals(em.find(
				Ad.class,
				ad.getId()
		).getSecret())) {
			throw new Exception("Secret is wrong");
		}
		return em.merge(ad);
	}

	public List<Ad> findAll() {
		return em.createQuery(
				"SELECT a FROM Ad a",
				Ad.class
		).getResultList();

	}

	public List<Ad> findBetweenPrice(int min, int max) {

		return em.createQuery(
				"SELECT a FROM Ad a WHERE a.price BETWEEN :min AND :max",
				Ad.class
		).setParameter(
				"min",
				min
		).setParameter(
				"max",
				max
		).getResultList();
	}


	public List<Ad> findAllWithTag(String tag) {
		return em.createQuery(
						"SELECT a FROM Ad a JOIN a.tags t WHERE LOWER(t) = LOWER(?1)",
						Ad.class
				)
				.setParameter(
						1,
						tag
				)
				.getResultList();
	}

	@Transactional
	@Scheduled(fixedDelay = 6000)
	public void deleteExpired() {
		System.out.println("Deleting expired ads");
		List<Ad> expiredAds = em.createQuery(
				"SELECT a From Ad a where a.expiryDate < ?1",
				Ad.class
		).setParameter(
				1,
				LocalDateTime.now()
		).getResultList();
		for (Ad expiredAd : expiredAds) {
			em.remove(expiredAd);
		}
	}


}

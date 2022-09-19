package hu.bme.aut.retelab2.controller;

import hu.bme.aut.retelab2.SecretGenerator;
import hu.bme.aut.retelab2.domain.Ad;
import hu.bme.aut.retelab2.repository.AdRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/ads")
@Slf4j
public class AdController {

	@Autowired
	AdRepository adRepository;


	@PostMapping
	public Ad create(@RequestBody Ad ad) {
		ad.setId(null);
		ad.setSecret(SecretGenerator.generate());
		return adRepository.save(ad);
	}

	@PutMapping
	public Ad update(@RequestBody Ad ad) {
		try {
			return adRepository.modify(ad);
		} catch (NotFoundException e) {
			log.error(
					"Ad not found",
					e
			);
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND,
					"Ad not found",
					e
			);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,
			                                  e.getMessage(),
			                                  e);
		}
	}

	@GetMapping
	public List<Ad> getBetweenPrice(@RequestParam(defaultValue = "0") int min, @RequestParam(defaultValue = "10000000") int max) {
		List<Ad> ret = adRepository.findBetweenPrice(
				min,
				max
		);
		ret.forEach(ad -> ad.setSecret(null));
		return ret;
	}

	@GetMapping("{tag}")
	public List<Ad> getByTag(@PathVariable String tag) {
		log.trace(
				"getByTag: {}",
				tag
		);
		List<Ad> ret = adRepository.findAllWithTag(tag);
		ret.forEach(ad -> ad.setSecret(null));
		return ret;
	}

}

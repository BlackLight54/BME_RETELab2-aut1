package hu.bme.aut.retelab2.controller;

import hu.bme.aut.retelab2.domain.Ad;
import hu.bme.aut.retelab2.repository.AdRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ads")
@Slf4j
public class AdController {

	@Autowired
	AdRepository adRepository;

	@GetMapping
	public List<Ad> getAll() {

		return adRepository.findAll();
	}

	@PostMapping
	public Ad create(@RequestBody Ad ad) {
		log.trace("create ad");
		log.trace(ad.getAddress());
		ad.setId(null);
		return adRepository.save(ad);
	}

}

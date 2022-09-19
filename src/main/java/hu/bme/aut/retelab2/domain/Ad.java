package hu.bme.aut.retelab2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Ad {
	@Id
	@GeneratedValue
	private Long id;

	private String address;

	private String description;

	private int price;

	private LocalDateTime createdOn;

	@PrePersist
	private void fillCreationDate() {
		createdOn = LocalDateTime.now();
	}


	private String secret;
}

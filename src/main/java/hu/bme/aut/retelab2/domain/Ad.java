package hu.bme.aut.retelab2.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
	private void fillCreationAndExpiryDate() {
		if (expiryDate == null) {
			expiryDate = LocalDateTime.now().plusSeconds(10);
		}
		createdOn = LocalDateTime.now();
	}


	private String secret;

	@ElementCollection
	private List<String> tags;

	@Nullable
	private LocalDateTime expiryDate;


}

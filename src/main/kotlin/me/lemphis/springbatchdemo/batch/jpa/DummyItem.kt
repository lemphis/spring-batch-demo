package me.lemphis.springbatchdemo.batch.jpa

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class DummyItem(
	@Column(length = 20)
	var firstName: String? = null,
	@Column(length = 20)
	var lastName: String? = null,
	@Column(length = 200)
	var email: String? = null,
) {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null

	var createdAt: LocalDateTime = LocalDateTime.now()

}


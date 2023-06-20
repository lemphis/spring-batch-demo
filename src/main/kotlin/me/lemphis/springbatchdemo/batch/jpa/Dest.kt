package me.lemphis.springbatchdemo.batch.jpa

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Dest(
	@Column(length = 40, nullable = false)
	var fullName: String,
	@Column(length = 200, nullable = false)
	var email: String,
) {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null

}


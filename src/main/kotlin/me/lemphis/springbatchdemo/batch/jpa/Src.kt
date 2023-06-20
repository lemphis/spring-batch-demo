package me.lemphis.springbatchdemo.batch.jpa

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import me.lemphis.springbatchdemo.batch.DummyItemDto

@Entity
class Src(
	@Column(length = 20, nullable = false)
	var firstName: String,
	@Column(length = 20, nullable = false)
	var lastName: String,
	@Column(length = 200, nullable = false)
	var email: String,
) {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null

	fun toDest() = Dest(
		fullName = "${this.firstName} ${this.lastName}".uppercase(),
		email = this.email,
	)

	fun toDto() = DummyItemDto(
		firstName, lastName, email,
	)

}

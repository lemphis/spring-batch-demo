package me.lemphis.springbatchdemo.batch

import me.lemphis.springbatchdemo.batch.jpa.Src

data class DummyItemDto(
	var firstName: String = "",
	var lastName: String = "",
	var email: String = "",
) {
	fun toSrc() = Src(
		firstName = this.firstName,
		lastName = this.lastName,
		email = this.email,
	)
}

package me.lemphis.springbatchdemo.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table
class DummyItem(
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null,
	var firstName: String? = null,
	var email: String? = null,
	var createdAt: LocalDateTime = LocalDateTime.now(),
)

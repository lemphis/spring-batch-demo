package me.lemphis.springbatchdemo.batch.jpa

import org.springframework.data.jpa.repository.JpaRepository

interface DestRepository : JpaRepository<Dest, Long>

package me.lemphis.springbatchdemo.batch.mybatis

import me.lemphis.springbatchdemo.batch.jpa.Dest
import me.lemphis.springbatchdemo.batch.jpa.Src
import org.apache.ibatis.annotations.Mapper

@Mapper
interface Mapper {
	fun insertIntoDest(dest: Dest): Int
	fun selectFromSrc(): List<Src>
}

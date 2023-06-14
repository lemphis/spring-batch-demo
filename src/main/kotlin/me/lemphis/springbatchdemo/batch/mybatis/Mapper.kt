package me.lemphis.springbatchdemo.batch.mybatis

import me.lemphis.springbatchdemo.batch.jpa.DummyItem
import org.apache.ibatis.annotations.Mapper

@Mapper
interface Mapper {
	fun test(dummyItem: DummyItem): Int
	fun selectTwoColumns(): List<Map<String, Any>>
}

package me.lemphis.springbatchdemo.batch.mybatis

import me.lemphis.springbatchdemo.batch.jpa.Dest
import me.lemphis.springbatchdemo.batch.jpa.Src
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface Mapper {
	@Select(
		"""
		<script>
			SELECT first_name AS firstName, last_name as lastName, email
			FROM src
			<if test="skiprows != null and pagesize != null">
				LIMIT #{skiprows}, #{pagesize}
			</if>
		</script>
		""",
	)
	fun selectFromSrc(): List<Src>

	@Insert("INSERT INTO dest(full_name, email) VALUES (#{fullName}, #{email})")
	fun insertIntoDest(dest: Dest): Int
}

package dao

import com.bs.model.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

object UserTable :Table("user"){
    val id=integer("id").primaryKey(0).autoIncrement()
    val account= varchar("account", length = 255)
    val password= varchar("password", length = 255)
    val name= varchar("name", length = 255).nullable()
    val age= integer("age").default(0)
    val logo= varchar("logo", length = 255).nullable()
    val type=integer("type").default(0)
    val job= varchar("job", length = 255).nullable()
    val status= integer("status").default(0)
    val sex=varchar("sex", length = 255).nullable()
}
fun toUser(row: ResultRow): User = User(
    account = row[UserTable.account],
    password = row[UserTable.password],
    age = row[UserTable.age],
    name = row[UserTable.name],
    logo =row[UserTable.logo],
    id=row[UserTable.id],
    type = row[UserTable.type],
    job =row[UserTable.job],
    status = row[UserTable.status],
    sex = row[UserTable.sex]
)
object KcTable:Table("kc"){
    val id= integer("id").primaryKey(0).autoIncrement()
    val stime= varchar("stime", length = 255).nullable()
    val addr= varchar("addr", length = 255).nullable()
    val lsId= varchar("lsId", length = 255).nullable()
    val week= integer("week").default(0)
    val ytime=integer("ytime").default(0)
    val openKq= bool("openKq")
    val name=varchar("name", length = 255).nullable()
}
fun toKc(row:ResultRow):KcBean = KcBean(
    id=row[KcTable.id],
    stime = row[KcTable.stime],
    addr = row[KcTable.addr],
    lsId = row[KcTable.lsId],
    week = row[KcTable.week],
    ytime = row[KcTable.ytime],
    openKq = row[KcTable.openKq],
    name = row[KcTable.name]
)
object KqTable:Table("kq"){
    val id= integer("id").primaryKey(0).autoIncrement()
    val userName= varchar("userName", length = 255).nullable()
    val userId= varchar("userId", length = 255).nullable()
    val time= varchar("time", length = 255).nullable()
    val status= varchar("status", length = 255).nullable()
    val kcname= varchar("kcname", length = 255).nullable()
    val kcId= varchar("kcId", length = 255).nullable()
    val addr= varchar("addr", length = 255).nullable()
    val cj= varchar("cj", length = 255).nullable()
}
fun toKq(row: ResultRow):KqBean=KqBean(
    id = row[KqTable.id],
    kcId = row[KqTable.kcId],
    kcname = row[KqTable.kcname],
    userId = row[KqTable.userId],
    userName = row[KqTable.userName],
    status = row[KqTable.status],
    addr = row[KqTable.addr],
    time = row[KqTable.time],
    cj=row[KqTable.cj]
)
object QjTable:Table("qj"){
    val id= integer("id").primaryKey(0).autoIncrement()
    val cotent=varchar("cotent", length = 255).nullable()
    val kcid=varchar("kcid", length = 255).nullable()
    val time=varchar("time", length = 255).nullable()
    val userId=varchar("userId", length = 255).nullable()
    val userName=varchar("userName", length = 255).nullable()
    val kcName=varchar("kcName", length = 255).nullable()
    val status=varchar("status", length = 255).nullable()
}
fun toQj(row: ResultRow):QjBean= QjBean(
    id = row[QjTable.id],
    cotent = row[QjTable.cotent],
    kcid = row[QjTable.kcid],
    kcName = row[QjTable.kcName],
    userName = row[QjTable.userName],
    userId = row[QjTable.userId],
    time = row[QjTable.time],
    status = row[QjTable.status]

)
object BjTable:Table("bj"){
    val id= integer("id").primaryKey(0).autoIncrement()
    val content= varchar("content", length = 255).nullable()
    val date= varchar("date", length = 255).nullable()
    val title= varchar("title", length = 255).nullable()
    val userId= varchar("userId", length = 255).nullable()
}
fun toBj(row: ResultRow):BjBean= BjBean(
    id=row[BjTable.id],
    content = row[BjTable.content],
    date = row[BjTable.date],
    title = row[BjTable.title],
    userId = row[BjTable.userId]
)

object courseMesTable:Table("coursemes"){
    val stu_id = integer("stu_id").primaryKey(0).autoIncrement()
    val stu_name = varchar("stu_name",length = 255).nullable()
    val course_name = varchar("course_name",length = 255).nullable()
    val stu_class = varchar("stu_class",length = 255).nullable()
    val courseId = varchar("courseId",length = 255).nullable()
}
fun Tocoursemes(row:ResultRow):CourseBean = CourseBean(
    stu_id = row[courseMesTable.stu_id],
    stu_name = row[courseMesTable.stu_name],
    course_name = row[courseMesTable.course_name],
    stu_class = row[courseMesTable.stu_class],
    courseId =  row[courseMesTable.courseId]

)
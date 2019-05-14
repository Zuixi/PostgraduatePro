package com.bs

import com.bs.model.*
import com.google.gson.Gson
import common.Constants
import dao.*
import dao.UserTable.password
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.features.ContentNegotiation
import io.ktor.gson.GsonConverter
import io.ktor.http.ContentType
import io.ktor.http.content.PartData
import io.ktor.http.content.readAllParts
import io.ktor.request.receive
import io.ktor.request.receiveMultipart
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import utils.DateUtil
import java.util.Date

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        register(ContentType.Application.Json, GsonConverter())
    }
    //连接数据库
    Database.connect(
        "jdbc:mysql://localhost:3306/data?characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true"
        , "com.mysql.jdbc.Driver"
        , "root"
        , "123456"
    )
    transaction {
        //设置每个Bean需要获取的信息
        SchemaUtils.create(KcTable,QjTable,KqTable,BjTable,courseMesTable)
    }
    //设置路由
    routing {
        post("/login") {
            val params = call.receiveParameters()
            val acc = params["account"] ?: ""
            val psd = params["password"] ?: ""
            var re: Any? = null
            transaction {
                val query = UserTable.select { UserTable.account.eq(acc) }
                val pwd = query.joinToString { it[password] }
                re = if (pwd.isEmpty() || pwd != psd) {
                    Constants.ERROR.toString()
                } else {
                    toUser(query.single())
                }
                commit()
            }
            call.respond(re!!)

        }

        post("/register") {
            val params = call.receiveParameters()
            val acc = params["account"] ?: ""
            val pwd = params["password"] ?: ""
            var re: Any? = null
            transaction {
                val query = UserTable.select { UserTable.account.eq(acc) }
                re = if (query.none()) {
                    UserTable.insert {
                        it[account] = acc
                        it[password] = pwd
                    }
                    Constants.SUCCESS.toString()
                } else {
                    Constants.ERROR.toString()
                }
            }
            call.respond(re!!)
        }

        post("/resetPwd") {
            val params = call.receiveParameters()
            val id = params["id"] ?: ""
            val pwd = params["password"] ?: ""
            var re: Any? = null
            transaction {
                re = UserTable.update({ UserTable.id eq id.toInt() }) {
                    it[password] = pwd
                }
            }
            call.respond(re!!)
        }
        post("/getAllUser") {
            val list = mutableListOf<User>()
            transaction {
                UserTable.selectAll().filterNot { it[UserTable.job] == "管理员" }.forEach {
                    list.add(toUser(it))
                }
            }
            call.respond(list)
        }
        post("/getUser") {
            val params = call.receiveParameters()
            val id = params["id"] ?: ""
            var re: Any? = null
            transaction {
                re = toUser(UserTable.selectAll().filter { it[UserTable.id] == id.toInt() }.single())
            }
            call.respond(re!!)
        }
        post("/resetLogo") {
            val params = call.receiveParameters()
            val id = params["id"] ?: ""
            val logo = params["logo"] ?: ""
            var re: Any? = null
            transaction {
                re = UserTable.update({ UserTable.id eq id.toInt() }) {
                    it[UserTable.logo] = logo
                }
            }
            call.respond(re!!)
        }
        post("/resetInfo") {
            val params = call.receiveParameters()
            val info = params["info"] ?: ""
            val bean = Gson().fromJson<User>(info, User::class.java)
            var re: Any? = Constants.SUCCESS
            transaction {
                re = UserTable.update({ UserTable.id eq bean.id!!.toInt() }) {
                    it[UserTable.sex] = bean.sex
                    it[UserTable.name] = bean.name
                    it[UserTable.job] = bean.job

                }
            }
            call.respond(re!!)
        }
        post("/getKcList") {
            var re: Any? = Constants.SUCCESS
            transaction {
                val list = mutableListOf<KcBean>()
                KcTable.selectAll().forEach { list.add(toKc(it)) }
                re = list
            }
            call.respond(re!!)
        }
        post("/getkckqList") {
            val params = call.receiveParameters()
            val id = params["kcid"] ?: ""
            var re: Any? = Constants.SUCCESS
            transaction {
                val list = mutableListOf<KqBean>()
                KqTable.selectAll().filter { it[KqTable.kcId]==id }.forEach { list.add(toKq(it)) }
                re = list
            }
            call.respond(re!!)
        }
        post("/getUserkqList") {
            val params = call.receiveParameters()
            val id = params["id"] ?: ""
            var re: Any? = Constants.SUCCESS
            transaction {
                val list = mutableListOf<KqBean>()
                KqTable.selectAll().filter { it[KqTable.userId]==id }.forEach { list.add(toKq(it)) }
                re = list
            }
            call.respond(re!!)
        }
        post("/resetKqOpen") {
            val params = call.receiveParameters()
            val id = params["kcid"] ?: ""
            val isOpen=params["isopen"] ?:""
            var re: Any? = Constants.SUCCESS
            transaction {
                KcTable.update({ KcTable.id eq id.toInt() }){
                    it[KcTable.openKq]=isOpen.toBoolean()
                }
            }
            call.respond(re!!)
        }

        post("/addkq") {
            val params = call.receiveParameters()
            val info = params["kcinfo"] ?: ""
            val userId = params["userId"] ?: ""
            val userName = params["userName"] ?: ""
            val time = params["time"] ?: ""
            val addr = params["addr"] ?: ""
            val bean = Gson().fromJson<KcBean>(info, KcBean::class.java)
            var re: Any? = Constants.SUCCESS
            transaction {
                KqTable.selectAll().forEach {
                   val curDate=DateUtil.getCurDateStr(DateUtil.FORMAT_YMDHM)
                    val tarDate=DateUtil.date2Str(Date(it[KqTable.time]!!.toLong()),DateUtil.FORMAT_YMDHM)
                    val sub=DateUtil.getDaySub(curDate,tarDate,DateUtil.FORMAT_YMDHM)
                    if (sub==0&&it[KqTable.kcId]==bean.id.toString()
                        &&it[KqTable.userId]==userId){
                        KqTable.deleteWhere {
                            KqTable.id.eq(it[KqTable.id]) }
                    }
                }
                val isOpen = toKc(KcTable.select { KcTable.id.eq(bean.id!!.toInt()) }.single()).openKq!!

                var status=""
                status = if (isOpen) {
                    "正常"
                } else {
                    "旷课"
                }
                KqTable.insert {
                    it[KqTable.addr] = addr
                    it[KqTable.time] = time
                    it[KqTable.kcname] = bean.name
                    it[KqTable.kcId] = bean.id!!.toString()
                    it[KqTable.status] = status
                    it[KqTable.userId] = userId
                    it[KqTable.userName] = userName

                }
            }
            call.respond(re!!)
        }

        post("/addqj") {
            val params = call.receiveParameters()
            val info = params["info"] ?: ""
            val bean = Gson().fromJson<QjBean>(info, QjBean::class.java)
            var re: Any? = Constants.SUCCESS
            transaction {
                QjTable.insert {
                    it[QjTable.cotent] = bean.cotent
                    it[QjTable.kcName]=bean.kcName
                    it[QjTable.kcid]=bean.kcid
                    it[QjTable.time]=bean.time
                    it[QjTable.userId]=bean.userId
                    it[QjTable.userName]=bean.userName
                    it[QjTable.status]="待审核"
                }
            }
            call.respond(re!!)
        }
        post("/getkcQjList") {
            val params = call.receiveParameters()
            val id = params["kcid"] ?: ""
            var re: Any? = Constants.SUCCESS
            transaction {
                val list = mutableListOf<QjBean>()
                QjTable.selectAll().filter { it[QjTable.kcid]==id }.forEach { list.add(toQj(it)) }
                re = list
            }
            call.respond(re!!)
        }
        post("/getCourseMes"){
            val params = call.receiveParameters();
            val id = params["courseId"]?: ""
            var re: Any? = Constants.SUCCESS
            transaction {
                val listmes = mutableListOf<CourseBean>()
                courseMesTable.selectAll().filter { it[courseMesTable.courseId] == id}.forEach(){listmes.add(Tocoursemes(it)) }
                re = listmes
            }
            call.respond(re!!)
        }
        post("/getUserQjList") {
            val params = call.receiveParameters()
            val id = params["id"] ?: ""
            var re: Any? = Constants.SUCCESS
            transaction {
                val list = mutableListOf<QjBean>()
                QjTable.selectAll().filter { it[QjTable.userId]==id }.forEach { list.add(toQj(it)) }
                re = list
            }
            call.respond(re!!)
        }
        post("/resetQjStatus") {
            val params = call.receiveParameters()
            val id = params["qjid"] ?: ""
            val status=params["status"] ?:""
            var re: Any? = Constants.SUCCESS
            transaction {
                QjTable.update({ QjTable.id eq id.toInt() }){
                    it[QjTable.status]=status
                }
            }
            call.respond(re!!)
        }
        post("/resetKqStatus") {
            val params = call.receiveParameters()
            val id = params["id"] ?: ""
            val status=params["status"] ?:""
            var re: Any? = Constants.SUCCESS
            transaction {
                KqTable.update({ KqTable.id eq id.toInt() }){
                    it[KqTable.status]=status
                }
            }
            call.respond(re!!)
        }
        post("/resetKqCj") {
            val params = call.receiveParameters()
            val id = params["id"] ?: ""
            val cj=params["cj"] ?:""
            var re: Any? = Constants.SUCCESS
            transaction {
                KqTable.update({ KqTable.id eq id.toInt() }){
                    it[KqTable.cj]=cj
                }
            }
            call.respond(re!!)
        }
        post("/getUserBjList") {
            val params = call.receiveParameters()
            val id = params["id"] ?: ""
            var re: Any? = Constants.SUCCESS
            transaction {
                val list = mutableListOf<BjBean>()
                BjTable.selectAll().filter { it[BjTable.userId]==id }.forEach { list.add(toBj(it)) }
                re = list
            }
            call.respond(re!!)
        }
        post("/delBj") {
            val params = call.receiveParameters()
            val id = params["id"] ?: ""
            var re: Any? = Constants.SUCCESS
            transaction {
                BjTable.deleteWhere { BjTable.id.eq(id.toInt()) }
            }
            call.respond(re!!)
        }
        post("/addBj") {
            val params = call.receiveParameters()
            val info = params["info"] ?: ""
            val bean = Gson().fromJson<BjBean>(info, BjBean::class.java)
            var re: Any? = Constants.SUCCESS
            transaction {
                BjTable.insert {
                   it[BjTable.content]=bean.content
                    it[BjTable.title]=bean.title
                    it[BjTable.userId]=bean.userId
                    it[BjTable.date]=bean.date
                }
            }
            call.respond(re!!)
        }
    }
}






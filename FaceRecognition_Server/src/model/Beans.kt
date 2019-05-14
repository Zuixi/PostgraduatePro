package com.bs.model

data class User(
    val id: Int?,
    val account:String?,
    val password:String?,
    val name:String?,
    val age:Int?,
    val logo:String?,
    val type:Int?,
    val status:Int?,
    val job:String?,
    val sex:String?
)

data class KcBean (
    val id:Int?,
    val name:String?,
    val stime:String?,
    val addr:String?,
    val lsId:String?,
    val week:Int?,
    val openKq:Boolean?,
    val ytime:Int?
)

data class KqBean (
    var id: Int?,
    var userName: String?,
    var userId: String?,
    var time: String?,
    var status: String?,
    var kcname: String?,
    var kcId: String?,
    var addr: String?,
    var cj:String?
)
data class QjBean (
    var id: Int?,
    var cotent: String?,
    var kcid: String?,
    val kcName:String?,
    var time: String?,
    var userId: String?,
    var userName: String?,
    var status:String?
)
class BjBean (
    var id: Int?,
    var content: String?,
    var date: String?,
    var title: String?,
    var userId: String?
)
// 这个courseBean为什么没用，不能用来传送数据
data class CourseBean(
    var stu_id: Int?,
    var stu_name: String?,
    var course_name: String?,
    var stu_class: String?,
    var courseId: String?
)



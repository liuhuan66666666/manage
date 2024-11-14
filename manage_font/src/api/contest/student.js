import {http} from "@/utils/http.js"



//增
export const add=(data)=>{
    return http.post("/student/add",data)
}


//删除
export const drop=(data)=>{
    return  http.post("/student/delete",data)
}


//改
export const edit=(data)=>{
    return http.post("/student/edit",data)
}


//分页获取学生表列表（封装类）
export const listStudentVOByPage=(data)=>{
    return http.post("/student/list/page/vo",data)
}



//获取全部学生列表
export const getStudentNames=()=>{
    return http.get("/student/getStudentNames")
}

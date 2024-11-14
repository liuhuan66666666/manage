import {http} from "@/utils/http.js";


//增
export const  add=(data)=>{
    return http.post("/teacher/add",data)
}


//删除
export const  drop=(data)=>{
    return http.post("/teacher/delete",data)
}


//改
export const edit=(data)=>{
    return http.post("teacher/edit",data)
}

//分页获取教师表列表（封装类）
export const listTeacherVOByPage=(data)=>{
    return http.post("/teacher/list/page/vo",data)
}



//获取全部教师列表
export const getTeacherNames=()=>{
    return http.get("/teacher/getTeacherNames")
}

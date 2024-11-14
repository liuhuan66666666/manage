import {http} from "@/utils/http.js";


//增
export const add=(data)=>{
    return http.post("/project/add",data)
}



//删
export const drop=(data)=>{
    return http.post("/project/delete",data)
}



//改
export const edit=(data)=>{
    return http.post("/project/edit",data)
}



//查
export const listProjectVOByPage=(data)=>{
    return http.post("/project/list/page/vo",data)
}

//获取全部项目名

export const getProjectNames=()=>{
    return http.get("/project/getProjectNames")
}
import {http} from "@/utils/http.js";

/*
 *部门相关
 */
export const getDeparments=()=>{
    return http.get("/department/getList")
}

//新增部门信息

export const add=(data)=>{
    return http.post("/department/add",data)
}

//删除部门
export const drop=(data)=>{
    return http.post("/department/delete",data)
}
//编辑部门
export  const edit=(data)=>{
    return http.post("/department/edit",data)
}

//根据名称查找部门
export const getDepartmentByName=(data)=>{
    return http.post("/department/list/page",data)
}

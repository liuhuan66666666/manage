import {http} from "@/utils/http.js";
//添加成员
export const add=(data)=>{
    return http.post("/member/add",data)
}

//删除成员
export const drop=(data)=>{
    return http.post("/member/delete",data)
}

//编辑成员
export const edit=(data)=>{
    return http.post("/member/edit",data)
}

//分页获取成员信息
export const listMemberVOByPage=(data)=>{
    return http.post("/member/list/page/vo",data)
}

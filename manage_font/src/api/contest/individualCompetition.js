import {http} from "@/utils/http.js";

//获取个人赛列表
export const listIndividualCompetitionVOByPage=(data)=>{
    return http.post("/individualCompetition/list/page/vo",data)
}

//增加个人赛列表
export const add=(data)=>{
    return http.post("/individualCompetition/add",data)
}


//删除

export const drop=(data)=>{
    return  http.post("/individualCompetition/delete",data)
}

//编辑
export const edit=(data)=>{
    return http.post("/individualCompetition/edit",data)
}
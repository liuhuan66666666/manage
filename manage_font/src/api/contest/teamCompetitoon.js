import {http} from "@/utils/http.js";

//增
export const add=(data)=>{
    return http.post("/teamCompetition/add",data)
}


//删除
export const drop=(data)=>{
    return http.post("/teamCompetition/delete",data)
}


//改
export const edit=(data)=>{
    return http.post("/teamCompetition/edit",data)
}



export const listTeamCompetitionVOByPage=(data)=>{
    return http.post("/teamCompetition/list/page/vo",data)
}
import {http} from "@/utils/http.js";

//活动通知
export const getActivityListVO=()=>{
    return http.get("/activity/list")
}


export const add=(data)=>{
    return http.post("/activity/add",data)
}


export const drop=(data)=>{
    return http.post("/activity/delete",data)
}


export const edit=(data)=>{
    return http.post("/activity/edit",data)
}
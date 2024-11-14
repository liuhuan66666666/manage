import {http} from "@/utils/http.js";


//公告通知
export const getAnnouncement=()=>{
    return http.get("/notification/getList")
}

export const add=(data)=>{
    return http.post("/notification/add",data)
}

export const drop=(data)=>{
    return http.post("/notification/delete",data)
}

export const edit=(data)=>{
    return http.post("/notification/edit",data)
}
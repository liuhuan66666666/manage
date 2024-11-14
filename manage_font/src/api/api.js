import {http} from "@/utils/http.js";


//登录
export const LoginK=(data)=>{
    return http.post("/user/login",data)
}

//获取当前登录成员
export const getLoginUser=()=>{
    return http.get("/user/get/login")
}























import {http} from "@/utils/http.js";


//根据id获取成员
export const getMember=(id)=>{
    return http.get("/member/get/vo",{id:id})
}


//echarts
export const getMajorDistribution=()=>{
    return http.get("/member/getMajorDistribution")
}


export const getDepartmentDistribution=()=>{
    return http.get("/member/getDepartmentDistribution")
}



import {http} from "@/utils/http.js";



//积分相关
export const getListIntegralVO=(data)=>{
    return http.post("/integral/list/page/vo",data)
}
export const edit=(data)=>{
    return http.post("/integral/edit",data)
}

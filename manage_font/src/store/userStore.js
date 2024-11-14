import {ref} from "vue";
import {defineStore} from "pinia";
import {getLoginUser} from "@/api/api.js";
import Access_Enum from "@/access/access_Enum.js";

export const useLoginUserStore = defineStore('LoginUser', () => {
    const loginUser = ref({
        userName: "未登录",
    })

    function setLoginUser(newLoginUser) {
        loginUser.value = newLoginUser;
    }

    async function fetchLoginUser() {
        const res = await getLoginUser();
        if (res.code === 0 && res.data) {
            loginUser.value = res.data;
        } else {
            loginUser.value = {userRole: Access_Enum.NOT_LOGIN};
        }
    }

    return {loginUser, setLoginUser, fetchLoginUser}
})
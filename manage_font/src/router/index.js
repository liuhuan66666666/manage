import { createWebHashHistory, createRouter } from 'vue-router'
import {routes} from "@/router/routes.js";
import {useLoginUserStore} from "@/store/userStore.js";
import Access_Enum from "@/access/access_Enum.js";
import {ElMessage} from "element-plus";

const router = createRouter({
  history: createWebHashHistory(),
  routes,
})
// 路由守卫
router.beforeEach(async (to, from, next) => {
  // 获取当前登录用户
  const loginUserStore = useLoginUserStore();
  let loginUser = loginUserStore.loginUser;
  // 如果之前没有尝试获取过登录用户信息，才自动登录
  if (!loginUser || !loginUser.userRole) {
    // 加 await 是为了等待用户登录成功并获取到值后，再执行后续操作
    await loginUserStore.fetchLoginUser();
    loginUser = loginUserStore.loginUser;
  }
  console.log(to.path);
  if (to.path !== "/login" && to.path !== "/") {
    if (loginUser.userRole === Access_Enum.NOT_LOGIN) {
      ElMessage.error("还未登录,请先登录");
      next("/login");
    } else {
      next();
    }
  } else {
    next();
  }
});


export default router
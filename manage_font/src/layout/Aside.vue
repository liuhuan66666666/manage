<template>
  <!--region-->
  <!--静态菜单-->
  <!--  <el-sub-menu index="/information">-->
  <!--                <template #title>-->
  <!--                    <el-icon>-->
  <!--                        <info-filled/>-->
  <!--                    </el-icon>-->
  <!--                    <span>信息管理</span>-->
  <!--                </template>-->
  <!--                <el-menu-item index="/information/activity">-->
  <!--                    <el-icon>-->
  <!--                        <Tickets/>-->
  <!--                    </el-icon>-->
  <!--                    <span>-->
  <!--            活动管理-->
  <!--          </span>-->
  <!--                </el-menu-item>-->
  <!--                <el-menu-item index="/information/announcement">-->
  <!--                    <el-icon>-->
  <!--                        <notification/>-->
  <!--                    </el-icon>-->
  <!--                    <span>-->
  <!--            公告通知-->
  <!--          </span></el-menu-item>-->
  <!--                <el-menu-item index="/information/integral">-->
  <!--                    <el-icon>-->
  <!--                        <Star/>-->
  <!--                    </el-icon>-->
  <!--                    <span>-->
  <!--            积分管理-->
  <!--          </span></el-menu-item>-->
  <!--            </el-sub-menu>-->
  <!--            <el-menu-item index="/department">-->
  <!--                <el-icon>-->
  <!--                    <office-building/>-->
  <!--                </el-icon>-->
  <!--                <span>部门管理</span>-->
  <!--            </el-menu-item>-->
  <!--            <el-menu-item index="/member">-->
  <!--                <el-icon>-->
  <!--                    <user/>-->
  <!--                </el-icon>-->
  <!--                <span>成员管理</span>-->
  <!--            </el-menu-item>-->
  <!--            <el-menu-item index="/project">-->
  <!--                <el-icon>-->
  <!--                    <folder/>-->
  <!--                </el-icon>-->
  <!--                <span>项目管理</span>-->
  <!--            </el-menu-item>-->
  <!--            endregion-->
  <!--动态菜单-->
    <el-menu :default-active="$route.path" class="el-menu-vertical-demo" :collapse="isCollapse"
             :unique-opened="false" :router="true">
        <el-menu-item index="/home">
            <el-icon>
                <House/>
            </el-icon>
            <span>首页</span>
        </el-menu-item>
        <template v-for="(item) in  filteredMenuItems" :key="item.path">
            <el-sub-menu v-if="item.children && item.children.length" :index="item.path">
                <template #title>
                    <el-icon>
                        <component :is="item.icon"/>
                    </el-icon>
                    <span>{{ item.name }}</span>
                </template>
                <el-menu-item v-for="(demo) in item.children" :key="demo.path"
                              :index="`${item.path}/${demo.path}`">
                    <el-icon>
                        <component :is="demo.icon"/>
                    </el-icon>
                    <span>{{ demo.name }}</span>
                </el-menu-item>
            </el-sub-menu>
            <el-menu-item v-else :index="item.path">
                <el-icon>
                    <component :is="item.icon"/>
                </el-icon>
                <span>{{ item.name }}</span>
            </el-menu-item>
        </template>
    </el-menu>
</template>

<script setup>
import {computed, ref} from "vue";

defineProps(['isCollapse'])
import {routes} from "@/router/routes.js";
import checkAccess from "@/access/checkAccess.js";
import {useLoginUserStore} from "@/store/userStore.js";

let LoginUser = useLoginUserStore();
let loginUser = LoginUser.loginUser;
const filteredMenuItems = computed(() => {
    return (routes.filter(route => route.path !== '/' && route.path !== '/login')[0].children
        .filter(item => checkAccess(loginUser, item.meta?.access)))
})


</script>

<style scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    min-height: 100vh;
}
</style>
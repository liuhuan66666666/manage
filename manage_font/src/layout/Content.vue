<template>
    <div>
        <div class="header">
            <div v-if="!isCollapse" style="margin-left: 10px;margin-top: 5px;">
                <el-button round :icon="ArrowLeft" @click="changeMenu" />
            </div>
            <div v-if="isCollapse" style="margin-left: 10px;margin-top: 5px;">
                <el-button round :icon="ArrowRight" @click="changeMenu" />
            </div>
            <div v-if="$route.fullPath === '/home'" class="container">
                <echarts></echarts>
            </div>
        </div>
        <div>
            <router-view></router-view>
        </div>
    </div>

</template>

<script setup>
import { ArrowLeft, ArrowRight } from '@element-plus/icons-vue'
import Echarts from '@/view/Echarts.vue';
defineProps(['isCollapse'])
const emit = defineEmits(['changeCollapse'])
const changeMenu = (() => {
    emit('changeCollapse')
})

</script>

<style scoped></style>
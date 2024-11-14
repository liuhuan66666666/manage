<template>
    <div>
        <!-- 顶部搜索框区域 -->
        <div style="margin-top: 20px; display: flex; flex-wrap: wrap;" class="header">
            <el-button type="primary" style="margin-left: 10px;" plain :icon="Plus" @click="addActivity">新增活动
            </el-button>
            <el-input v-model="searchTerm" style="width: 240px; margin-left: 10px;margin-right: 20px"
                      placeholder="请输入活动名称"/>
        </div>

        <!-- 表格内容区域 -->
        <main>
            <el-table :data="filteredData" style="width: 100%; margin-top: 10px;" :cell-style="rowClass"
                      :header-cell-style="headClass">
                <el-table-column prop="name" label="活动名称"/>
                <el-table-column prop="date" label="活动日期"/>
                <el-table-column prop="location" label="活动地点"/>
                <el-table-column label="操作">
                    <template #default="scope">
                        <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
                        <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </main>

        <!-- 添加活动对话框 -->
        <el-dialog v-model="addVisible" title="添加活动" width="400" center>
            <el-form :model="addInput" label-width="auto" style="width: 100%;">
                <el-form-item label="活动名称">
                    <el-input v-model="addInput.name"/>
                </el-form-item>
                <el-form-item label="活动日期">
                    <el-input type="date" v-model="addInput.date"/>
                </el-form-item>
                <el-form-item label="活动地点">
                    <el-input v-model="addInput.location"/>
                </el-form-item>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button type="danger" plain @click="addVisible = false">取消</el-button>
                    <el-button type="primary" @click="confirmAddActivity">确认</el-button>
                </div>
            </template>
        </el-dialog>

        <!-- 编辑活动对话框 -->
        <el-dialog v-model="editVisible" title="编辑活动" width="400" center>
            <el-form :model="editInput" label-width="auto" style="width: 100%;">
                <el-form-item label="活动名称">
                    <el-input v-model="editInput.name"/>
                </el-form-item>
                <el-form-item label="活动日期">
                    <el-input type="date" v-model="editInput.date"/>
                </el-form-item>
                <el-form-item label="活动地点">
                    <el-input v-model="editInput.location"/>
                </el-form-item>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button type="danger" plain @click="editVisible = false">取消</el-button>
                    <el-button type="primary" @click="confirmEdit">确认</el-button>
                </div>
            </template>
        </el-dialog>

        <!-- 删除确认对话框 -->
        <el-dialog v-model="deleteVisible" title="确认删除?" width="200" center>
            <template #footer>
                <div class="dialog-footer">
                    <el-button type="danger" plain @click="deleteVisible = false">取消</el-button>
                    <el-button type="primary" @click="confirmDelete">确认</el-button>
                </div>
            </template>
        </el-dialog>
    </div>
</template>

<script setup>
import {ref, reactive, onMounted, computed} from 'vue';
import {Plus} from '@element-plus/icons-vue';
import {getActivityListVO, add, drop,edit} from "@/api/information/activity.js";
import {ElMessage} from "element-plus";

//表单样式
const rowClass = () => ({'text-align': 'center'})
const headClass = () => ({'text-align': 'center', background: '#f8f8f9'})

//搜索条件
const searchTerm = ref('');
const activities = ref([])

//增加
const addInput = reactive({name: '', date: '', location: ''});
const addVisible = ref(false);
const addActivity = () => {
    addVisible.value = true;
};
const confirmAddActivity = () => {
    add(addInput).then(res => {
        if (res.data) {
            ElMessage.success("添加成功")
        } else {
            ElMessage.warning("操作失败,请稍后再试")
        }
    }).catch(() => {
        ElMessage.error("操作失败")
    }).finally(() => {
        getActivityListVO().then(res => {
            activities.value = res.data
        })
        // 将addInput对象属性清空
        Object.assign(addInput, {name: '', date: '', location: ''});
        addVisible.value = false
    })


};

//删除
const deleteVisible = ref(false);
//删除ID
const deleteId = ref(null)
const handleDelete = (row) => {
    deleteId.value = row.id
    deleteVisible.value = true;
};
const confirmDelete = () => {
    drop({id: deleteId.value}).then(res => {
        if (res.data) {
            ElMessage.success("删除成功")
        } else {
            ElMessage.warning("操作失败,请稍后再试")
        }
    }).catch(() => {
        ElMessage.error("操作失败")
    }).finally(() => {
        getActivityListVO().then(res => {
            activities.value = res.data
        })
        deleteId.value = ''
        deleteVisible.value = false;
    })

};

//编辑
const editVisible = ref(false);
const editInput = reactive({name: '', date: '',id:'' ,location: ''});
const handleEdit = (activity) => {
    editVisible.value = true;
    Object.assign(editInput, activity);
};
const confirmEdit = () => {
    edit(editInput).then(res=>{
        if(res.data){
            ElMessage.success("编辑成功")
        }else{
            ElMessage.warning("操作失败,请稍后再试")
        }
    }).catch(()=>{
        ElMessage.error("操作失败")
    }).finally(()=>{
        getActivityListVO().then(res => {
            activities.value = res.data
        })
        Object.keys(editInput).forEach(key=>{
            editInput.key=''
        })
        editVisible.value = false;
    })

};

//使用计算属性优化前端性能
const filteredData = computed(() => {
    if (!searchTerm.value) return activities.value;
    return activities.value.filter(item => item.name.includes(searchTerm.value));
})

onMounted(() => {
    getActivityListVO().then(res => {
        activities.value = res.data
    })
})
</script>

<style scoped>
.header > div {
    margin-left: 10px;
}
</style>

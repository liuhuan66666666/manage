<template>
    <div>
        <!-- 顶部操作区域 -->
        <div style="display: flex; margin-top: 20px; justify-content: space-between">
            <div style="display: flex; flex-wrap: wrap;" class="header">
                <el-button type="primary" plain @click="addNotification">新增公告</el-button>
            </div>
        </div>

        <!-- 公告列表 -->
        <el-table :data="notifications" style="width: 100%; margin-top: 10px;" :cell-style="rowClass"
                  :header-cell-style="headClass" tooltip-effect="dark">
            <el-table-column prop="title" label="公告标题" />
            <el-table-column prop="content" label="公告内容" />
            <el-table-column prop="date" label="发布日期" />
            <el-table-column prop="status" label="状态">
                <template #default="scope">
                    <el-tag v-if="scope.row.status === 1" type="warning">待审核</el-tag>
                    <el-tag v-if="scope.row.status === 0" type="success">通过</el-tag>
                    <el-tag v-if="scope.row.status === -1" type="danger">拒绝</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作">
                <template #default="scope">
                    <el-button size="small" @click="handleReview(scope.row)">审核</el-button>
                    <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 新增公告对话框 -->
        <el-dialog v-model="addVisible" title="新增公告" width="400" center>
            <el-form :model="AddNotification" label-width="auto" style="width: 100%;">
                <el-form-item label="标题">
                    <el-input v-model="AddNotification.title" />
                </el-form-item>
                <el-form-item label="内容">
                    <el-input v-model="AddNotification.content" />
                </el-form-item>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button type="danger" plain @click="addVisible = false">取消</el-button>
                    <el-button type="primary" @click="confirmAdd">确认</el-button>
                </div>
            </template>
        </el-dialog>

        <!-- 编辑公告对话框 -->
        <el-dialog v-model="editVisible" width="400" center>
            <el-form :model="editNotification" label-width="auto" style="width: 100%;">
                <el-form-item label="状态">
                    <el-select v-model="editNotification.status">
                        <el-option label="待审核" :value="1" />
                        <el-option label="通过" :value="0" />
                        <el-option label="拒绝" :value="-1" />
                    </el-select>
                </el-form-item>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button type="danger" plain @click="editVisible = false">取消</el-button>
                    <el-button type="primary" @click="confirmReview">确认</el-button>
                </div>
            </template>
        </el-dialog>

        <!-- 删除确认对话框 -->
        <el-dialog v-model="deleteVisible" title="确认删除公告？" width="200" center>
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
import { ref, reactive, onMounted } from 'vue';
import { getAnnouncement, add, drop, edit } from "@/api/information/Announcement.js";
import { ElMessage } from "element-plus";

// 表单样式
const rowClass = () => ({ 'text-align': 'center' });
const headClass = () => ({ 'text-align': 'center', background: '#f8f8f9' });

// 公告列表数据
const notifications = ref([]);

// 新增
const addVisible = ref(false);
const AddNotification = reactive({ title: '', content: '' });
const addNotification = () => {
    addVisible.value = true;
};

const confirmAdd = async () => {
    try {
        const res = await add(AddNotification);
        if (res.data) {
            ElMessage.success("添加成功");
        } else {
            ElMessage.warning("操作失败,请稍后再试");
        }
    } catch {
        ElMessage.error("操作失败");
    } finally {
        Object.assign(AddNotification, { title: '', content: '' }); // 重置表单
        await refreshNotifications(); // 刷新公告列表
        addVisible.value = false; // 关闭对话框
    }
};

// 删除
const deleteVisible = ref(false);
const deleteNotficationId = ref('');
const handleDelete = (row) => {
    deleteNotficationId.value = row.id;
    deleteVisible.value = true;
};

const confirmDelete = async () => {
    try {
        const res = await drop({ id: deleteNotficationId.value });
        if (res.data) {
            ElMessage.success("删除成功");
        } else {
            ElMessage.warning("操作失败,请稍后再试");
        }
    } catch {
        ElMessage.error("操作失败");
    } finally {
        deleteNotficationId.value = '';
        deleteVisible.value = false; // 关闭对话框
        await refreshNotifications(); // 刷新公告列表
    }
};

// 获取公告数据
const refreshNotifications = async () => {
    try {
        const res = await getAnnouncement();
        notifications.value = res.data;
    } catch (error) {
        ElMessage.error("获取公告失败");
    }
};

// 组件挂载时获取公告数据
onMounted(() => {
    refreshNotifications();
});

// 编辑
const editVisible = ref(false);
const editNotification = reactive({
    id: "",
    title: "",
    content: "",
    status: ""
});

const handleReview = (row) => {
    editVisible.value = true;
    Object.assign(editNotification, row);
};

const confirmReview = async () => {
    try {
        const res = await edit(editNotification);
        if (res.data) {
            ElMessage.success("审核成功");
        } else {
            ElMessage.warning("审核失败，请稍后再试");
        }
    } catch {
        ElMessage.error("操作失败");
    } finally {
        await refreshNotifications(); // 刷新公告列表
        editVisible.value = false; // 关闭对话框
    }
};
</script>

<style lang="scss" scoped>
.header > div {
    margin-left: 10px;
}

.dialog-footer {
    text-align: right;
}
</style>

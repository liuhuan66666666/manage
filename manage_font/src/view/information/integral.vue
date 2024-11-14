<template>
    <div>
        <div style="display: flex;margin-top: 20px;justify-content: space-between">
            <!--顶部搜索框区域-->
            <div style=" display: flex; flex-wrap: wrap;" class="header">
                <div>
                    <el-input v-model="searchTerm" style="width: 240px; margin-left: 10px;" placeholder="请输入姓名"/>
                </div>
            </div>
            <div>
                <el-button @click="sortscore('asc')" type="success" plain>积分升序</el-button>
                <el-button @click="sortscore('desc')" type="info" plain>积分降序</el-button>
            </div>
        </div>
        <!--表单内容区域-->
        <main>
            <el-table :data="filteredData" style="width: 100%; margin-top: 10px;" :cell-style="rowClass"
                      :header-cell-style="headClass" tooltip-effect="dark">
                <el-table-column prop="name" label="姓名"/>
                <el-table-column prop="score" label="积分"/>
                <el-table-column label="操作">
                    <template #default="scope">
                        <el-button size="small" type="warning" @click="handleEdit(scope.row)">编辑</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </main>
        <div style="margin-top: 20px;margin-left: 10px;">
            <el-pagination :page-sizes="[8, 10]"
                           background layout="total, sizes, prev, pager, next, jumper"
                           :total="total" :current-page="currentPage" :page-size="pageSize"
                           @size-change="handleSizeChange"
                           @current-change="handleCurrentChange"
            />
        </div>
        <!--对话框-->
        <el-dialog v-model="addVisible" title="添加人员" width="400" center>
            <el-form :model="addInput" label-width="auto" style="width: 100%;">
                <el-form-item label="姓名">
                    <el-input v-model="addInput.name"/>
                </el-form-item>
                <el-form-item label="积分">
                    <el-input v-model="addInput.score" type="number"/>
                </el-form-item>
            </el-form>
        </el-dialog>

        <!--编辑对话框-->
        <el-dialog v-model="editVisible" title="编辑" width="400" center>
            <el-form :model="editInput" label-width="auto" style="width: 100%;">
                <el-form-item label="姓名">
                    <el-input v-model="editInput.name"/>
                </el-form-item>
                <el-form-item label="积分">
                    <el-input v-model="editInput.score" type="number"/>
                </el-form-item>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button type="danger" plain @click="editVisible = false">取消</el-button>
                    <el-button type="primary" @click="confirmEdit">确认</el-button>
                </div>
            </template>
        </el-dialog>

        <!--删除对话框-->
        <el-dialog v-model="deleteVisible" title="确认删除?" width="200" center>
            <template #footer>
                <div class="dialog-footer">
                    <el-button type="danger" plain @click="deleteVisible = false, deleteName=''">取消</el-button>
                    <el-button type="primary" @click="deletePeple">确认</el-button>
                </div>
            </template>
        </el-dialog>
    </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue';
import { getListIntegralVO, edit } from "@/api/information/integral.js";
import { ElMessage } from "element-plus";

// 表格样式
const rowClass = () => ({ 'text-align': 'center' });
const headClass = () => ({ 'text-align': 'center', background: '#f8f8f9' });

// 搜索相关
const searchTerm = ref('')
const filteredData = computed(() => {
    if (!searchTerm.value) return tableData.value;
    return tableData.value.filter(item => item.name.includes(searchTerm.value));
})

// 表格数据
const tableData = ref([]);

// 编辑
const editInput = reactive({ name: '', score: '' });
const editVisible = ref(false);
const handleEdit = (row) => {
    Object.assign(editInput, row)
    editVisible.value = true;
};

const confirmEdit = async () => {
    try {
        const res = await edit(editInput);
        if (res.data) {
            ElMessage.success("编辑成功");
        } else {
            ElMessage.warning("操作失败,请稍后再试");
        }
    } catch {
        ElMessage.error("操作失败");
    } finally {
        initData(currentPage.value, pageSize.value);
        editVisible.value = false;
    }
};

// 初始化数据
const initData = async (currentPage, pageSize) => {
    try {
        const res = await getListIntegralVO({ currentPage, pageSize });
        total.value = parseInt(res.data.total);
        tableData.value = res.data.records;
    } catch (err) {
        console.error('Error fetching data:', err);
    }
}

onMounted(() => {
    initData(currentPage.value, pageSize.value);
});

// 分页
const currentPage = ref(1);
const pageSize = ref(8);
const total = ref(50);

const handleSizeChange = (val) => {
    pageSize.value = val;
    initData(currentPage.value, pageSize.value);
};

const handleCurrentChange = (val) => {
    currentPage.value = val;
    initData(currentPage.value, pageSize.value);
};

// 积分排序
const sortscore = (order) => {
    if (order === 'asc') {
        tableData.value.sort((a, b) => a.score - b.score);
    } else {
        tableData.value.sort((a, b) => b.score - a.score);
    }
};
</script>

<style scoped>
.header > div {
    margin-left: 10px;
}
</style>

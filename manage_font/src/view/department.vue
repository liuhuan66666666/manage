<template>
    <div>
        <!--顶部搜索框区域-->
        <div style="margin-top: 20px;display: flex;flex-wrap: wrap;" class="header">
            <div>
                <el-button type="primary" style="margin-left: 10px;  " plain :icon="Plus" @click="handleAdd">新增
                </el-button>
            </div>
            <div>
                <el-form-item style="width: 240px;">
                    <el-input v-model="departmentName" placeholder="请输入部门名称"/>
                </el-form-item>
            </div>

            <div>
                <el-button type="primary" @click="search">搜索</el-button>
            </div>
            <div>
                <el-button
                    style="float: left; margin-left: 10px; margin-bottom: 12px"
                    :icon="Refresh"
                    @click="clearSearch"
                    type="warning"
                    plain
                >重置</el-button>
            </div>
        </div>
        <!--表单内容区域-->
        <main>
            <el-table :data="tableData" style="width: 100%; margin-top: 10px;" :cell-style="rowClass"
                      :header-cell-style="headClass" tooltip-effect="dark">
                <el-table-column prop="name" label="部门名称"/>
                <el-table-column prop="responsibilities" label="部门职责"/>
                <el-table-column prop="manager" label="部门负责人"/>
                <el-table-column prop="manager_phone" label="负责人电话"/>
                <el-table-column label="操作">
                    <template #default="scope">
                        <el-button size="small" @click="handleEdit(scope.row)">
                            编辑
                        </el-button>
                        <el-button size="small" type="danger" @click="handleDelete(scope.row)">
                            删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </main>

        <!--dialong组件-->
        <!--添加-->
        <el-dialog
                v-model="addVisible"
                title="添加人员"
                width="400"
                center
        >
            <el-form :model="addInput" label-width="auto" style="width: 100%;">
                <el-form-item label="部门名称">
                    <el-input v-model="addInput.name"/>
                </el-form-item>
                <el-form-item label="部门职责">
                    <el-input v-model="addInput.responsibilities" type="textarea" :rows="3"></el-input>
                </el-form-item>

                <el-form-item label="部门负责人">
                    <el-input v-model="addInput.manager"/>
                </el-form-item>

                <el-form-item label="负责人联系方式 ">
                    <el-input v-model="addInput.manager_phone"/>
                </el-form-item>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button type="danger" plain @click="addVisible = false,addInput=''">取消</el-button>
                    <el-button type="primary" @click="addDepartK">
                        确认
                    </el-button>
                </div>
            </template>
        </el-dialog>
        <!--编辑-->
        <el-dialog
                v-model="editVisible"
                title="编辑"
                width="400"
                center
        >
            <el-form :model="editInput" label-width="auto" style="width: 100%;" :label-position="'left'">
                <el-form-item label="部门名称">
                    <el-input v-model="editInput.name"/>
                </el-form-item>
                <el-form-item label="部门职责">
                    <el-input v-model="editInput.responsibilities" type="textarea" :rows="3"></el-input>
                </el-form-item>

                <el-form-item label="部门负责人">
                    <el-input v-model="editInput.manager"/>
                </el-form-item>

                <el-form-item label="负责人联系方式 ">
                    <el-input v-model="editInput.manager_phone"/>
                </el-form-item>


            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button type="danger" plain @click="editVisible = false">取消</el-button>
                    <el-button type="primary" @click="editDepartK">
                        确认
                    </el-button>
                </div>
            </template>
        </el-dialog>
        <!--删除-->
        <el-dialog
                v-model="deleteVisible"
                title="确认删除?"
                width="200"
                center
        >
            <template #footer>
                <div class="dialog-footer">
                    <el-button type="danger" plain @click="deleteVisible = false,deleteId=''">取消</el-button>
                    <el-button type="primary" @click="deleteDepartV">
                        确认
                    </el-button>
                </div>
            </template>
        </el-dialog>
    </div>


</template>

<script setup>
import {ref, reactive, onMounted} from 'vue'
import {Plus, Refresh} from '@element-plus/icons-vue'
import {getDeparments, drop, add, edit,getDepartmentByName} from "@/api/department.js";
import {ElMessage} from "element-plus";
//表格数据
const tableData = ref([])
//搜索相关
const departmentName = ref(null)

const search =()=>{
     getDepartmentByName({name:departmentName.value}).then((res)=>{
         tableData.value = [res.data];
     })
}

const clearSearch=()=>{
    departmentName.value=''
    initData()
}

// 表格样式调整
const rowClass = () => ({'text-align': 'center'})
const headClass = () => ({'text-align': 'center', background: '#f8f8f9'})


const initData = () => {
    getDeparments().then(res => {
        tableData.value = res.data
    })
}
onMounted(() => {
    initData()
})

//增加
const addVisible = ref(false)
let addInput = reactive({
    "name": "",
    "responsibilities": "",
    "manager": "",
    "manager_phone": ""
})
const handleAdd = () => {
    addVisible.value = true;
}

const addDepartK = () => {
    add(addInput).then((res) => {
        if (res.data) {
            ElMessage.success("添加成功")
        } else {
            ElMessage.warning("操作失败,请稍后再试")
        }
    }).catch((error) => {
        ElMessage.warning("操作失败,请稍后再试")
    }).finally(() => {
        initData()
        Object.keys(addInput).forEach(key => {
            addInput[key] = "";
        });
        addVisible.value = false
    })
}


//删除
const deleteVisible = ref(false)
//要删除的Id
const deleteId = ref('')
const handleDelete = (scope) => {
    deleteId.value = scope.id
    deleteVisible.value = true
}
const deleteDepartV = () => {
    drop({id: deleteId.value}).then((res) => {
        if (res.data) {
            ElMessage.warning("删除成功")
        } else {
            ElMessage.error("操作失败，请稍后再试")
        }
    }).catch((error) => {
        ElMessage.error("操作失败，请稍后再试")
    })
        .finally((res) => {
            deleteVisible.value = ''
            deleteVisible.value = false
            initData()
        })

}


//编辑
const editVisible = ref(false)
let editInput = reactive({
    "id": "",
    "name": "",
    "responsibilities": "",
    "manager": "",
    "manager_phone": ""
});
const handleEdit = (scope) => {
    editVisible.value=true
    Object.assign(editInput,scope)
}
const editDepartK = () => {
   edit(editInput).then((res)=>{
       if(res.data){
           ElMessage.success("编辑成功")
       }else{
           ElMessage.warning("操作失败,请稍后再试")
       }
   }).catch((error)=>{
       ElMessage.error("操作失败")
   }).finally(()=>{
       for (let key in editInput) {
           if (editInput.hasOwnProperty(key)) {
               editInput[key] = " ";
           }
       }
       initData()
       editVisible.value=false
   })
}


</script>

<style scoped>
.header > div {
    margin-left: 10px;
}
</style>





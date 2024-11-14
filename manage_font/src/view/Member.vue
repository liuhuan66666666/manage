<template>
    <div>
        <!--顶部搜索框区域-->
        <div style="margin-top: 20px;display: flex;flex-wrap: wrap;" class="header">
            <div>
                <el-button type="primary" style="margin-left: 10px;  " plain :icon="Plus" @click="addVisible=true">新增
                </el-button>
            </div>
            <div>
                <el-input v-model="searchContent.name" style="width: 240px;margin-left: 10px;"
                          placeholder="请输入姓名"/>
            </div>
            <div>
                <el-form-item style="width: 240px;">
                    <el-select v-model="searchContent.department" placeholder="部门">
                        <el-option label="开发部" :value="'开发部'"/>
                        <el-option label="媒体部" :value="'媒体部'"/>
                        <el-option label="组织部" :value="'组织部'"/>
                    </el-select>
                </el-form-item>
            </div>
            <div>
                <el-form-item style="width: 240px;">
                    <el-select v-model="searchContent.grade" placeholder="年级">
                        <el-option label="2022级" :value="2022"/>
                        <el-option label="2023级" :value="2023"/>
                        <el-option label="2024级" :value="2024"/>
                    </el-select>
                </el-form-item>
            </div>
            <div>
                <el-form-item style="width: 240px;">
                    <el-select v-model="searchContent.major" placeholder="专业">
                        <el-option label="数字媒体技术" :value="'数字媒体技术'"/>
                        <el-option label="计算机科学与技术" :value="'计算机科学与技术'"/>
                        <el-option label="数据科学与大数据技术" :value="'数据科学与大数据技术'"/>
                        <el-option label="信息计算与科学" :value="'信息计算与科学'"/>
                    </el-select>
                </el-form-item>
            </div>
            <div>
                <el-button type="primary" @click="search">搜索</el-button>
            </div>
            <div>
                <el-button type="warning" plain icon="UploadFilled" @click="exportMember">导出</el-button>
            </div>
            <div>
                <el-button
                    style="float: left; margin-left: 10px; margin-bottom: 12px"
                    :icon="Refresh"
                    @click="reset"
                    type="warning"
                    plain
                >重置</el-button>
            </div>
        </div>
        <!--表单内容区域-->
        <main>
            <el-table :data="tableData" style="width: 100%; margin-top: 10px;" :cell-style="rowClass"
                      :header-cell-style="headClass" tooltip-effect="dark">
                <el-table-column prop="name" label="姓名"/>
                <el-table-column prop="gender" label="性别"
                                 :formatter="(row) => row.gender === 1 ? '男' : row.gender === 2 ? '女' : '未填写'"/>
                <el-table-column prop="studentId" label="学号"/>
                <el-table-column prop="contact" label="联系方式"/>
                <el-table-column prop="joinDate" label="加入时间"/>
                <el-table-column prop="department" label="所属部门"/>
                <el-table-column prop="major" label="专业"/>
                <el-table-column prop="grade" label="年级"/>
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
        <!--分页组件-->
        <div style="margin-top: 20px;margin-left: 10px;">
            <el-pagination :page-sizes="[8, 10]"
                           background layout="total, sizes, prev, pager, next, jumper"
                           :total="total" :current-page="page.currentPage" :page-size="page.pageSize"
                           @size-change="handleSizeChange"
                           @current-change="handleCurrentChange"
            />
        </div>
        <!--dialong组件-->
        <!--添加-->
        <el-dialog
            v-model="addVisible"
            title="添加人员"
            width="400"
            center
        >
            <el-form :model="addInput" label-width="auto" style="width: 100%;" :rules="rules"
                     ref="ruleFormRef">
                <el-form-item label="姓名" prop="name">
                    <el-input v-model="addInput.name"/>
                </el-form-item>

                <el-form-item label="性别" prop="gender">
                    <el-select v-model="addInput.gender" prop="gender">
                        <el-option label="男" :value="1"/>
                        <el-option label="女" :value="2"/>
                    </el-select>
                </el-form-item>

                <el-form-item label="学号" prop="studentId">
                    <el-input v-model="addInput.studentId"/>
                </el-form-item>

                <el-form-item label="联系方式" prop="contact">
                    <el-input v-model="addInput.contact"/>
                </el-form-item>
                <el-form-item label="部门" prop="department">
                    <el-select v-model="addInput.department" placeholder="请选择部门">
                        <el-option label="开发部" :value="'开发部'"/>
                        <el-option label="组织部" :value="'组织部'"/>
                        <el-option label="媒体部" :value="'媒体部'"/>
                    </el-select>
                </el-form-item>
                <el-form-item label="专业" prop="major">
                    <el-select v-model="addInput.major" placeholder="请选择专业">
                        <el-option label="数字媒体技术" :value="'数字媒体技术'"/>
                        <el-option label="计算机科学与技术" :value="'计算机科学与技术'"/>
                        <el-option label="通信工程" :value="'通信工程'"/>
                        <el-option label="信息计算与科学" :value="'信息计算与科学'"/>
                        <el-option label="数据科学与大数据技术" :value="'数据科学与大数据技术'"/>
                    </el-select>
                </el-form-item>
                <el-form-item label="年级" prop="grade">
                    <el-input v-model="addInput.grade"/>
                </el-form-item>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button type="danger" plain @click="addVisible = false">取消</el-button>
                    <el-button type="primary" @click="confirmAdd">
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
            <el-form :model="editInput" label-width="auto" style="width: 100%;">
                <el-form-item label="姓名">
                    <el-input v-model="editInput.name"/>
                </el-form-item>

                <el-form-item label="性别 ">
                    <el-select v-model="editInput.gender">
                        <el-option label="男" :value="1"/>
                        <el-option label="女" :value="2"/>
                    </el-select>
                </el-form-item>

                <el-form-item label="学号 ">
                    <el-input v-model="editInput.studentId"/>
                </el-form-item>

                <el-form-item label="联系方式 ">
                    <el-input v-model="editInput.contact"/>
                </el-form-item>
                <el-form-item label="部门 ">
                    <el-select v-model="editInput.department">
                        <el-option label="开发部" :value="'开发部'"/>
                        <el-option label="组织部" :value="'组织部'"/>
                        <el-option label="媒体部" :value="'媒体部'"/>
                    </el-select>
                </el-form-item>
                <el-form-item label="专业">
                    <el-input v-model="editInput.major"/>
                </el-form-item>
                <el-form-item label="年级">
                    <el-input v-model="editInput.grade"/>
                </el-form-item>

            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button type="danger" plain @click="editVisible = false">取消</el-button>
                    <el-button type="primary" @click="confirmEdit">
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
                    <el-button type="danger" plain @click="deleteVisible = false">取消</el-button>
                    <el-button type="primary" @click="confirmDelete">
                        确认
                    </el-button>
                </div>
            </template>
        </el-dialog>
    </div>


</template>

<script setup>
import {ref, reactive, onMounted} from 'vue'
import {Plus,Refresh} from '@element-plus/icons-vue'
import {add,drop,edit,listMemberVOByPage} from "@/api/member.js";
import {exportExcel} from "@/utils/excel.js";
import {ElMessage} from "element-plus";


// region表格样式
const rowClass = () => ({'text-align': 'center'})
const headClass = () => ({'text-align': 'center', background: '#f8f8f9'})

//表单验证规则
const rules = {
    name: [
        { required: true, message: '姓名不能为空', trigger: 'blur' },
        { min: 2, max: 10, message: '姓名长度应在2到10个字符之间', trigger: 'blur' }
    ],
    gender: [
        { required: true, message: '请选择性别', trigger: 'change' }
    ],
    studentId: [
        { required: true, message: '学号不能为空', trigger: 'blur' },
        { pattern: /^[0-9]{8,12}$/, message: '学号格式不正确', trigger: 'blur' }
    ],
    contact: [
        { required: true, message: '联系方式不能为空', trigger: 'blur' },
        { pattern: /^[1][3,4,5,7,8][0-9]{9}$/, message: '联系方式格式不正确', trigger: 'blur' }
    ],
    department: [
        { required: true, message: '请选择部门', trigger: 'change' }
    ],
    major: [
        { required: true, message: '请选择专业', trigger: 'change' }
    ],
    grade: [
        { required: true, message: '请选择年级', trigger: 'change' }
    ]
};
const ruleFormRef = ref()

//搜索
const searchContent = reactive({
    name: '',
    department: '',
    grade: '',
    major: ''
})
const search = async () => {
    const isSearchContentEmpty = Object.values(searchContent).every(value => value === '');
    if (isSearchContentEmpty) {
        ElMessage.warning("搜索条件为空")
    } else {
        // searchContent.currentPage=page.currentPage
        // searchContent.pageSize=page.pageSize
        //上面是一个坑 如果直接修改 那么searchContent就会有currentPage和pageSize属性 ...searchContent便会覆盖
        const params = {
            ...searchContent,  // 保留现有的搜索条件
            currentPage: page.currentPage,  // 从page中获取最新的分页信息
            pageSize: page.pageSize
        }
        let response =await listMemberVOByPage(params)
        tableData.value=response.data.records
        total.value=parseInt(response.data.total)
    }
}


//导出
const exportMember=()=>{
    exportExcel('/member/exportMemberExcel',searchContent)
}


//表格数据
const tableData = ref([])

//增加
const addVisible = ref(false)
const addInput = reactive({
    name: '',
    gender:'',
    studentId: '',
    contact: '',
    department: '',
    major: '',
    grade: ''
})
const confirmAdd=async ()=>{
    ruleFormRef.value.validate(async (valid) => {
        if (valid) {
            let response=await add(addInput)
            if(response.code===0){
                ElMessage.success("操作成功")
                initData(page.currentPage,page.pageSize)
                Object.keys(addInput).forEach(key=>{
                    addInput[key]=" "
                })
                addVisible.value=false
            }
            else {
                ElMessage.error(response.message)
            }
        } else {
            ElMessage.error('表单验证失败');
        }
    });
}

//删除
const deleteVisible = ref(false)
const deleteId=ref(null)
const handleDelete = (row) => {
    deleteVisible.value = true
    deleteId.value = row.id
}
const confirmDelete=async  ()=>{
    let response =  await drop({id:deleteId.value})
    if(response.code===0){
        ElMessage.success("操作成功")
        initData(page.currentPage,page.pageSize)
        deleteVisible.value=false
    }
    else {
        ElMessage.error(response.message)
    }
}

//编辑
const editVisible = ref(false)
const editInput = reactive({
    id: '',
    name: '',
    gender: '',
    studentId: '',
    contact: '',
    department: '',
    major: '',
    grade: ''
});
const handleEdit = (row) => {
    Object.assign(editInput, row)
    editVisible.value = true
}
const confirmEdit=async ()=>{
    let response=await edit(editInput)
    if(response.code===0){
        ElMessage.success("操作成功")
        initData(page.currentPage,page.pageSize)
        editVisible.value=false
    }
    else {
        ElMessage.error(response.message)
    }
}

//  分页
const page = reactive({
    currentPage: 1,
    pageSize: 8,
})
//总条数
const total = ref(10)
//每页展示条数发生改变
const handleSizeChange = (val) => {
    console.log(val)
    page.pageSize = val
    initData(page.currentPage,page.pageSize,searchContent)
}
//当前页发生改变
const handleCurrentChange = (val) => {
    console.log(val)
    page.currentPage = val
    initData(page.currentPage,page.pageSize,searchContent)
}

//重置
const reset=()=>{
    Object.keys(searchContent).forEach((key)=>{
        searchContent[key]=""
    })
    page.currentPage=1
    page.pageSize=8
    initData(page.currentPage,page.pageSize)
}

//初始化数据
const initData=async (currentPage,pageSize,searchContent=null)=>{
    let response= await listMemberVOByPage({
        currentPage,
        pageSize,
        ...searchContent
    })
    tableData.value=response.data.records
    total.value=parseInt(response.data.total)
}

onMounted(()=>{
    initData(page.currentPage,page.pageSize)
})



</script>

<style scoped>
.header > div {
    margin-left: 10px;
}
</style>
<template>
    <div style="margin-top: 20px">
        <div class="search">
            <div>
                <el-input v-model="searchContent.name" style="width: 240px;" placeholder="请输入教师姓名"/>
            </div>
            <div>
                <el-input v-model="searchContent.teacherId" style="width: 240px;" placeholder="请输入教师工号"/>
            </div>
            <div>
                <el-select v-model="searchContent.college" placeholder="请选择学院" style="width: 240px;">
                    <el-option v-for="item in academies" :key="item.value" :label="item.label" :value="item.value"/>
                </el-select>
            </div>
            <div>
                <el-select v-model="searchContent.department" placeholder="请选择系" style="width: 240px;">
                    <el-option v-for="item in specializeds" :key="item.value" :label="item.label" :value="item.value"/>
                </el-select>
            </div>
            <div>
                <el-button type="primary" :icon="Search" @click="search">搜索</el-button>
            </div>
            <div>
                <el-button
                        :icon="Refresh"
                        plain
                        @click="reset"
                >重置
                </el-button>
            </div>


        </div>
        <el-button type="primary" style="margin-left: 10px;  " plain :icon="Plus" @click="addVisible=true"
                   size="default">
            新增
        </el-button>
        <el-table :data="tableData" :cell-style="rowClass" :header-cell-style="headClass">
            <el-table-column prop="name" label="教师姓名"/>
            <el-table-column prop="teacherId" label="教师工号"/>
            <el-table-column prop="teacherType" label="教师类型" :formatter="(row) => row.teacherType === 1 ? '企业老师' : row.teacherType === 0 ? '校内老师' : '未知'"/>
            <el-table-column prop="phone" label="联系方式"/>
            <el-table-column prop="email" label="邮箱" show-overflow-tooltip/>
            <el-table-column prop="college" label="二级学院" show-overflow-tooltip/>
            <el-table-column prop="department" label="所在系"/>
            <el-table-column prop="createTime" label="创建时间"/>
            <el-table-column prop="status" label="状态" :formatter="(row) => row.status === 1 ? '在职' : row.status === 0 ? '离职' : '未知'"/>
            <el-table-column label="操作">
                <template #default="scope">
                    <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
                    <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!--分页组件-->
        <div style="float: right;margin-top: 20px;margin-right: 10px">
            <el-pagination :page-sizes="[8, 10]" background layout="total, sizes, prev, pager, next, jumper"
                           :total="total" :current-page="page.currentPage" :page-size="page.pageSize"
                           @size-change="handleSizeChange" @current-change="handleCurrentChange"/>
        </div>

    </div>

    <!--添加-->
    <el-dialog v-model="addVisible" width="400" center>
        <el-form :model="addTeacher" label-width="auto" style="width: 100%;" :rules="rules" ref="ruleFormRef">
            <el-form-item label="教师姓名" prop="name">
                <el-input v-model="addTeacher.name"/>
            </el-form-item>
            <el-form-item label="教师工号" prop="teacherId">
                <el-input v-model="addTeacher.teacherId"/>
            </el-form-item>
            <el-form-item label="教师类型" prop="teacherType">
                <el-select v-model="addTeacher.teacherType">
                    <el-option label="企业老师" :value="1"/>
                    <el-option label="校内老师" :value="0"/>
                </el-select>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
                <el-input v-model="addTeacher.email"/>
            </el-form-item>
            <el-form-item label="二级学院" prop="college">
                <el-select v-model="addTeacher.college">
                    <el-option label="人工智能产业学院" :value="'人工智能产业学院'"/>
                    <el-option label="数学与统计学院" :value="'数学与统计学院'"/>
                </el-select>
            </el-form-item>
            <el-form-item label="所在系" prop="department">
                <el-select v-model="addTeacher.department">
                    <el-option label="数字媒体技术系" :value="'数字媒体技术系'"/>
                    <el-option label="计算机科学与技术系" :value="'计算机科学与技术系'"/>
                    <el-option label="通信工程系" :value="'通信工程系'"/>
                    <el-option label="大数据与科学技术系" :value="'大数据与科学技术系'"/>
                    <el-option label="人工智能系" :value="'人工智能系'"/>
                    <el-option label="信息与计算科学系" :value="'信息与计算科学系'"/>
                </el-select>
            </el-form-item>
            <el-form-item label="联系方式" prop="phone">
                <el-input v-model="addTeacher.phone"/>
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button type="danger" plain @click="addVisible=false">取消</el-button>
                <el-button type="primary" @click="confirmAdd">确认</el-button>
            </div>
        </template>
    </el-dialog>
    <el-dialog v-model="editVisible" width="400" center>
        <el-form :model="editTeacher" label-width="auto" style="width: 100%;">
            <el-form-item label="教师姓名">
                <el-input v-model="editTeacher.name"/>
            </el-form-item>
            <el-form-item label="教师工号">
                <el-input v-model="editTeacher.teacherId"/>
            </el-form-item>
            <el-form-item label="教师类型">
                <el-select v-model="editTeacher.teacherType">
                    <el-option label="企业老师" :value="1"/>
                    <el-option label="校内老师" :value="0"/>
                </el-select>
            </el-form-item>
            <el-form-item label="邮箱">
                <el-input v-model="editTeacher.email"></el-input>
            </el-form-item>
            <el-form-item label="二级学院">
                <el-select v-model="editTeacher.college">
                    <el-option label="人工智能产业学院" :value="'人工智能产业学院'"/>
                    <el-option label="数学与统计学院" :value="'数学与统计学院'"/>
                </el-select>
            </el-form-item>
            <el-form-item label="所在系">
                <el-select v-model="editTeacher.department">
                    <el-option label="数字媒体技术系" :value="'数字媒体技术系系'"/>
                    <el-option label="计算机科学与技术系" :value="'计算机科学与技术系'"/>
                    <el-option label="通信工程系" :value="'通信工程系'"/>
                    <el-option label="大数据与科学技术系" :value="'大数据与科学技术系'"/>
                    <el-option label="人工智能系" :value="'人工智能系'"/>
                    <el-option label="信息与计算科学系" :value="'信息与计算科学系'"/>
                </el-select>
            </el-form-item>
            <el-form-item label="联系方式">
                <el-input v-model="editTeacher.phone"/>
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button type="danger" plain @click="editVisible=false">取消</el-button>
                <el-button type="primary" @click="confirmEdit">确认</el-button>
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
</template>

<script setup>
import {onMounted, reactive, ref} from 'vue'
import {add,drop,edit,listTeacherVOByPage} from "@/api/contest/teacher.js";
import {Plus, Refresh, Search} from '@element-plus/icons-vue'
import {ElMessage} from "element-plus";

//表单验证规则
const rules = {
    name: [
        { required: true, message: '请输入教师姓名', trigger: 'blur' },
        { min: 2, max: 20, message: '姓名长度在 2 到 20 个字符之间', trigger: 'blur' }
    ],
    teacherId: [
        { required: true, message: '请输入教师工号', trigger: 'blur' },
        { pattern: /^[0-9]{6,12}$/, message: '工号必须是6到12位数字', trigger: 'blur' }
    ],
    teacherType: [
        { required: true, message: '请选择教师类型', trigger: 'change' }
    ],
    phone: [
        { required: true, message: '请输入联系方式', trigger: 'blur' },
        { pattern: /^1[3-9]\d{9}$/, message: '请输入有效的手机号', trigger: 'blur' }
    ],
    email: [
        { required: true, message: '请输入邮箱', trigger: 'blur' },
        { pattern: /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/, message: '请输入有效的邮箱地址', trigger: 'blur' }
    ],
    college: [
        { required: true, message: '请选择二级学院', trigger: 'change' }
    ],
    department: [
        { required: true, message: '请选择所在系', trigger: 'change' }
    ]
};

const ruleFormRef = ref()


//表格样式调整
const rowClass = () => ({'text-align': 'center'})
const headClass = () => ({'text-align': 'center', background: '#f8f8f9'})



//搜索条件
const searchContent = reactive({
    name: '',
    teacherId: '',
    college: '',
    department: ''
})
const search = async () => {
    const isSearchContentEmpty = Object.values(searchContent).every(value => value === '');
    if (isSearchContentEmpty) {
        ElMessage.warning("搜索条件为空")
    } else {
        const params = {
            ...searchContent,  // 保留现有的搜索条件
            currentPage: page.currentPage,  // 从page中获取最新的分页信息
            pageSize: page.pageSize
        }
        let response =await listTeacherVOByPage(params)
        tableData.value=response.data.records
        total.value=parseInt(response.data.total)
    }
}

//二级学院
const academies = [
    {value: '人工智能产业学院', label: '人工智能产业学院'},
    {value: '数统学院', label: '数统学院'}
]

//专业系
const specializeds = [
    {value: '数字媒体技术系系', label: '数字媒体技术系系'},
    {value: '计算机科学与技术系', label: '计算机科学与技术系'},
    {value: '人工智能系', label: '人工智能系'},
    {value: '通信工程系', label: '通信工程系'},
    {value: '大数据与科学技术系', label: '大数据与科学技术系'},
    {value: '信息与计算科学系', label: '信息与计算科学系'}
]

//表格数据
const tableData = ref([])

//增加
const addVisible = ref(false)
const addTeacher = reactive({
    name: '',
    teacherId: '',
    teacherType:'',
    phone: '',
    college: '',
    email:'',
    department: ''
})
const confirmAdd=async ()=>{
    ruleFormRef.value.validate(async (valid)=>{
        if(valid){
            let response=await add(addTeacher)
            if(response.code===0){
                ElMessage.success("操作成功")
                initData(page.currentPage,page.pageSize)
                Object.keys(addTeacher).forEach(key=>{
                    addTeacher[key]=" "
                })
                addVisible.value=false
            }
            else {
                ElMessage.error(response.message)
            }
        }
        else {
            ElMessage.error("表单验证失败，请检查输入项")
        }
    })

}

//删除
const deleteVisible = ref(false)
//要删除的id
const deleteId=ref()
const handleDelete=(row)=>{
    deleteId.value=row.id
    deleteVisible.value=true
}
const confirmDelete=async  ()=>{
    let response =  await drop({id:deleteId.value})
    if(response.code===0){
        ElMessage.success("操作成功")
        initData(page.currentPage,page.pageSize,searchContent)
        deleteVisible.value=false
    }
    else {
        ElMessage.error(response.message)
    }
}


//编辑
const editVisible = ref(false)
const editTeacher = reactive({
    name: '',
    teacherId: '',
    teacherType:'',
    phone: '',
    college: '',
    email:'',
    department: '数字媒体系'
})
const handleEdit = (row) => {
    console.log(row)
    Object.assign(editTeacher, row)
    editVisible.value = true
}
const confirmEdit=async ()=>{
    let response=await edit(editTeacher)
    if(response.code===0){
        ElMessage.success("操作成功")
        initData(page.currentPage,page.pageSize,searchContent)
        editVisible.value=false
    }
    else {
        ElMessage.error(response.message)
    }
}



//分页
const page = reactive({currentPage: 1, pageSize: 8})
//每页展示条数发生改变
const handleSizeChange = (val) => {
    page.pageSize = val
    initData(page.currentPage,page.pageSize,searchContent)
}
//当前页发生改变
const handleCurrentChange = (val) => {
    page.currentPage = val
    initData(page.currentPage,page.pageSize,searchContent)

}

const total = ref(20)


//初始化数据
const initData=async (currentPage,pageSize,searchContent=null)=>{
    let response=await listTeacherVOByPage({
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


//重置

const reset=()=>{
    Object.keys(searchContent).forEach((key)=>{
        searchContent[key]=""
    })
    page.currentPage=1
    page.pageSize=8
    initData(page.currentPage,page.pageSize)
}
</script>

<style scoped >
el-form-item {
  margin: 10px
}

.el-table {
  margin-top: 10px;
}

.search {
  display: flex;
}

.search > div {
  margin: 10px;
}
</style>

<template>
    <div style="margin-top: 20px">
        <el-form :model="searchContent" class="form-inline">
            <div style="display: flex">
                <el-form-item label="学生姓名" style="margin-left: 10px">
                    <el-input v-model="searchContent.name" style="width: 240px;" placeholder="请输入学生姓名" />
                </el-form-item>
                <el-form-item label="学生学号" style="margin-left: 10px">
                    <el-input v-model="searchContent.studentId" style="width: 240px;" placeholder="请输入学生学号" />
                </el-form-item>
            </div>
            <div style="display: flex;justify-content: space-between">
                <div style="display: flex">
                    <el-form-item label="二级学院" style="margin-left: 10px">
                        <el-select v-model="searchContent.college" placeholder="请选择学院" style="width: 240px;">
                            <el-option v-for="item in academies" :key="item.value" :label="item.label" :value="item.value" />
                        </el-select>
                    </el-form-item>

                    <el-form-item label="专业名称" style="margin-left: 10px">
                        <el-select v-model="searchContent.major" placeholder="请选择专业" style="width: 240px;">
                            <el-option v-for="item in specializeds" :key="item.value" :label="item.label" :value="item.value" />
                        </el-select>
                    </el-form-item>
                    <el-form-item style="margin-left: 10px">
                        <el-button type="primary" :icon="Search" @click="search">搜索</el-button>
                    </el-form-item>
                    <el-form-item style="margin-left: 10px">
                        <el-button
                            :icon="Refresh"
                            plain
                            @click="reset"
                        >重置</el-button>
                    </el-form-item>
                </div>
                <div style="display: flex">

                    <el-form-item>
                        <el-button  type="primary" style="margin-left: 10px;  " plain :icon="Plus" @click="addVisible=true">
                            新增
                        </el-button>
                    </el-form-item>
                </div>
            </div>
        </el-form>
        <el-table :data="tableData" :cell-style="rowClass" :header-cell-style="headClass">
            <el-table-column prop="name" label="学生姓名" />
            <el-table-column prop="studentId" label="学生学号" />
            <el-table-column prop="phone" label="联系方式" />
            <el-table-column prop="email" label="电子邮箱"   show-overflow-tooltip />
            <el-table-column prop="college" label="二级学院" />
            <el-table-column prop="major" label="专业名称" />
            <el-table-column prop="createTime" label="创建时间" />
            <el-table-column prop="status" label="状态"   :formatter="(row) => row.status === 1 ? '在校' : row.status === 0 ? '离校' : '未知'"/>
            <el-table-column label="操作">
                <template #default="scope">
                    <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
                    <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!--分页组件-->
        <div style="float: right;margin-top: 20px;margin-right: 10px">
            <el-pagination :page-sizes="[8, 10]"
                           background layout="total, sizes, prev, pager, next, jumper"
                           :total="total" :current-page="page.currentPage" :page-size="page.pageSize"
                           @size-change="handleSizeChange"
                           @current-change="handleCurrentChange"
            />
        </div>
    </div>
    <!--新增-->
    <el-dialog
        v-model="addVisible"
        width="400"
        center
    >
        <el-form :model="addStudent" label-width="auto" style="width: 100%;" :rules="rules" ref="ruleFormRef">
            <el-form-item label="学生姓名" prop="name">
                <el-input v-model="addStudent.name"/>
            </el-form-item>
            <el-form-item label="学生学号" prop="studentId">
                <el-input v-model="addStudent.studentId"/>
            </el-form-item>

            <el-form-item label="二级学院" prop="college">
                <el-select v-model="addStudent.college" prop="college">
                    <el-option label="人工智能产业学院" :value="'人工智能产业学院'"/>
                    <el-option label="数学与统计学院" :value="'数学与统计学院'"/>
                </el-select>
            </el-form-item>
            <el-form-item label="专业名称"  prop="major" >
                <el-select v-model="addStudent.major" prop="major">
                    <el-option label="数字媒体技术" :value="'数字媒体技术'"/>
                    <el-option label="计算机与科学技术" :value="'计算机与科学技术'"/>
                    <el-option label="通信工程" :value="'通信工程'"/>
                    <el-option label="大数据与科学技术" :value="'大数据与科学技术'"/>
                    <el-option label="人工智能" :value="'人工智能'"/>
                    <el-option label="信息与计算科学" :value="'信息与计算科学'"/>
                </el-select>
            </el-form-item>
            <el-form-item label="联系方式" prop="phone">
                <el-input v-model="addStudent.phone"/>
            </el-form-item>
            <el-form-item label="电子邮箱" prop="email">
                <el-input v-model="addStudent.email"/>
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button type="danger" plain @click="addVisible=false">取消</el-button>
                <el-button type="primary" @click="confirmAdd">
                    确认
                </el-button>
            </div>
        </template>
    </el-dialog>
    <el-dialog
        v-model="editVisible"
        width="400"
        center
    >
        <el-form :model="editStudent" label-width="auto" style="width: 100%;">
            <el-form-item label="学生姓名">
                <el-input v-model="editStudent.name"/>
            </el-form-item>


            <el-form-item label="学生学号">
                <el-input v-model="editStudent.studentId"/>
            </el-form-item>

            <el-form-item label="二级学院">
                <el-select v-model="editStudent.college">
                    <el-option label="人工智能产业学院" :value="'人工智能产业学院'"/>
                    <el-option label="数学与统计学院" :value="'数学与统计学院'"/>
                </el-select>
            </el-form-item>
            <el-form-item label="专业名称">
                <el-select v-model="editStudent.major">
                    <el-option label="数字媒体技术" :value="'数字媒体技术'"/>
                    <el-option label="计算机与科学技术" :value="'计算机与科学技术'"/>
                    <el-option label="通信工程" :value="'通信工程'"/>
                    <el-option label="大数据与科学技术" :value="'大数据与科学技术'"/>
                    <el-option label="人工智能" :value="'人工智能'"/>
                    <el-option label="信息与计算科学" :value="'信息与计算科学'"/>
                </el-select>
            </el-form-item>
            <el-form-item label="联系方式">
                <el-input v-model="editStudent.phone"/>
            </el-form-item>
            <el-form-item label="电子邮箱">
                <el-input v-model="editStudent.email"/>
            </el-form-item>
            <el-form-item label="状态">
                <el-select v-model="editStudent.status">
                    <el-option label="在校" :value="1"/>
                    <el-option label="离校" :value="0"/>
                </el-select>
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button type="danger" plain @click="editVisible=false">取消</el-button>
                <el-button type="primary" @click="confirmEdit()">
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
</template>

<script setup>
import {onMounted, reactive, ref} from 'vue'
import {Plus, Refresh, Search} from '@element-plus/icons-vue'
import {add,drop,edit,listStudentVOByPage} from "@/api/contest/student.js";
import {ElMessage} from "element-plus";

//表单验证规则
const rules = {
    name: [
        { required: true, message: '请输入学生姓名', trigger: 'blur' },
        { min: 2, max: 20, message: '姓名长度在 2 到 20 个字符之间', trigger: 'blur' }
    ],
    studentId: [
        { required: true, message: '请输入学生学号', trigger: 'blur' },
        { pattern: /^[0-9]{12}$/, message: '学号必须是12位数字', trigger: 'blur' }
    ],
    college: [
        { required: true, message: '请选择二级学院', trigger: 'change' }
    ],
    major: [
        { required: true, message: '请选择专业名称', trigger: 'change' }
    ],
    phone: [
        { required: true, message: '请输入联系方式', trigger: 'blur' },
        { pattern: /^1[3-9]\d{9}$/, message: '请输入有效的手机号', trigger: 'blur' }
    ],
    email: [
        { required: true, message: '请输入电子邮箱', trigger: 'blur' },
        { pattern: /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/, message: '请输入有效的邮箱地址', trigger: 'blur' }
    ]
};

const ruleFormRef = ref()
//表单样式控制
const rowClass = () => ({'text-align': 'center'})
const headClass = () => ({'text-align': 'center', background: '#f8f8f9'})


//搜索条件
const searchContent=reactive({
    name:'',
    studentId:'',
    college:'',
    major:''
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

const search=async ()=>{
    // 检查所有属性是否都为空字符串
    const isSearchContentEmpty = Object.values(searchContent).every(value => value === '');
    if (isSearchContentEmpty) {
        ElMessage.warning("搜索条件为空")
    } else {
        const params = {
            ...searchContent,  // 保留现有的搜索条件
            currentPage: page.currentPage,  // 从page中获取最新的分页信息
            pageSize: page.pageSize
        }
        let response =await listStudentVOByPage(params)
        tableData.value=response.data.records
        total.value=parseInt(response.data.total)
    }

}

//二级学院
const academies = [
    {
        value: '人工智能产业学院',
        label: '人工智能产业学院',
    },
    {
        value: '数统学院',
        label: '数统学院',
    }
]
const specializeds = [
    {
        value: '数字媒体技术',
        label: '数字媒体技术',
    },
    {
        value: '计算机与科学技术',
        label: '计算机与科学技术',
    },
    {
        value: '人工智能',
        label: '人工智能',
    },
    {
        value: '通信工程',
        label: '通信工程',
    },{
        value: '大数据与科学技术',
        label: '大数据与科学技术',
    },{
        value: '信息与计算科学',
        label: '信息计算与科学',
    },
]

//表格数据
const tableData = ref([])

//增加学生
const addVisible = ref(false)
const addStudent=reactive({
    name: '',
    studentId: '',
    phone: '',
    college: '',
    major: '',
    email: ''
})
const confirmAdd=async ()=>{
    ruleFormRef.value.validate(async (valid)=>{
        if(valid){
            let response=await add(addStudent)
            if(response.code===0){
                ElMessage.success("操作成功")
                initData(page.currentPage,page.pageSize)
                Object.keys(addStudent).forEach(key=>{
                    addStudent[key]=" "
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


//删除学生
const deleteVisible=ref(false)
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

//编辑学生
const editVisible=ref(false)
const editStudent=reactive({
    name: '',
    studentId: '',
    phone: '',
    college: '',
    major: '',
    email: ''
})
const handleEdit=(row)=>{
    Object.assign(editStudent,row)
    editVisible.value=true
}
const confirmEdit=async ()=>{
    let response=await edit(editStudent)
    if(response.code===0){
        ElMessage.success("操作成功")
        initData(page.currentPage,page.pageSize,searchContent)
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
    page.pageSize = val
    initData(page.currentPage,page.pageSize,searchContent)
}
//当前页发生改变
const handleCurrentChange = (val) => {
    page.currentPage = val
    initData(page.currentPage,page.pageSize,searchContent)
}

//初始化数据
const initData=async (currentPage,pageSize,searchContent=null)=>{
    let response= await listStudentVOByPage({
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


<style   scoped>
el-form-item{
    margin: 10px
}
.el-table{
    margin-top: 10px;
}
</style>

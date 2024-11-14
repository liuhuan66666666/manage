<template>
    <div style="margin-top: 20px">
        <div class="search">
            <div>
                <el-input v-model="searchContent.name" placeholder="竞赛名称"></el-input>
            </div>
            <div>
                <el-select v-model="searchContent.year" placeholder="竞赛年份" style="width: 240px;">
                    <el-option v-for="item in competitionYears" :key="item.value" :label="item.label" :value="item.value"/>
                </el-select>
            </div>
            <div>
                <el-select v-model="searchContent.status" placeholder="竞赛状态" style="width: 240px;">
                    <el-option v-for="item in competitionStatuses" :key="item.value" :label="item.label" :value="item.value"/>
                </el-select>
            </div>
            <div>
                <el-button type="primary" :icon="Search" @click="search">搜索</el-button>
            </div>
            <div>
                <el-button :icon="Refresh" plain @click="reset">重置</el-button>
            </div>
        </div>
        <el-button type="primary" style="margin-left: 10px;" plain :icon="Plus" @click="addVisible=true" size="default">
            新增
        </el-button>
        <el-table :data="tableData" :cell-style="rowClass" :header-cell-style="headClass">
            <el-table-column prop="name" label="竞赛名称"/>
            <el-table-column prop="year" label="竞赛年份"/>
            <el-table-column prop="type" label="竞赛类型"/>
            <el-table-column prop="status" label="竞赛状态" :formatter="(row) => row.status === 1 ? '进行中' : row.status === 0 ? '已经结束' : '未知'"/>
            <el-table-column prop="registrationDeadline" label="报名截止日期"/>
            <el-table-column prop="project" label="竞赛项目"/>
            <el-table-column prop="teacher" label="指导老师"/>
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

    <!--添加对话框-->
    <el-dialog v-model="addVisible" width="400" center>
        <el-form :model="addCompetition" label-width="auto" style="width: 100%;" :rules="rules" ref="ruleFormRef">
            <el-form-item label="竞赛名称" prop="name">
                <el-input v-model="addCompetition.name" ></el-input>
            </el-form-item>
            <el-form-item label="竞赛年份" prop="year" >
                <el-select v-model="addCompetition.year" prop="year">
                    <el-option label="2022" :value="'2022'"/>
                    <el-option label="2023" :value="'2023'"/>
                    <el-option label="2024" :value="'2024'"/>
                </el-select>
            </el-form-item>
            <el-form-item label="竞赛类型" prop="type">
                <el-select v-model="addCompetition.type">
                    <el-option label="A类" :value="'A类'"/>
                    <el-option label="B类" :value="'B类'"/>
                </el-select>
            </el-form-item>
            <el-form-item label="竞赛状态" prop="status">
                <el-select v-model="addCompetition.status">
                    <el-option label="进行中" :value="1"/>
                    <el-option label="已结束" :value="0"/>
                </el-select>
            </el-form-item>
            <el-form-item label="报名截止日期" prop="registrationDeadline">
                <el-input v-model="addCompetition.registrationDeadline" type="Date"/>
            </el-form-item>
            <el-form-item label="竞赛项目" prop="project">
                <el-select v-model="addCompetition.project">
                    <el-option v-for="project in ProjectNames" :key="project" :label="project" :value="project"/>
                </el-select>
            </el-form-item>
            <el-form-item label="指导老师" prop="teacher">
                <el-select v-model="addCompetition.teacher" multiple>
                    <el-option v-for="teacher in TeacherNames" :key="teacher" :label="teacher" :value="teacher"/>
                </el-select>
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button type="danger" plain @click="addVisible=false">取消</el-button>
                <el-button type="primary" @click="confirmAdd">确认</el-button>
            </div>
        </template>
    </el-dialog>

    <!-- 编辑对话框 -->
    <el-dialog v-model="editVisible" width="400" center>
        <el-form :model="editCompetition" label-width="auto" style="width: 100%;">
            <el-form-item label="竞赛名称">
                <el-input v-model="editCompetition.name"/>
            </el-form-item>
            <el-form-item label="竞赛年份">
                <el-select v-model="editCompetition.year">
                    <el-option label="2022" :value="'2022'"/>
                    <el-option label="2023" :value="'2023'"/>
                    <el-option label="2024" :value="'2024'"/>
                </el-select>
            </el-form-item>
            <el-form-item label="竞赛类型">
                <el-select v-model="editCompetition.type">
                    <el-option label="A类" :value="'A类'"/>
                    <el-option label="B类" :value="'B类'"/>
                </el-select>
            </el-form-item>
            <el-form-item label="竞赛状态">
                <el-select v-model="editCompetition.status">
                    <el-option label="进行中" :value="1"/>
                    <el-option label="已结束" :value="0"/>
                </el-select>
            </el-form-item>
            <el-form-item label="报名截止日期">
                <el-input v-model="editCompetition.registrationDeadline" type="date"/>
            </el-form-item>
            <el-form-item label="竞赛项目">
                <el-select v-model="editCompetition.project">
                    <el-option v-for="project in ProjectNames" :key="project" :label="project" :value="project"/>
                </el-select>
            </el-form-item>
            <el-form-item label="指导老师">
                <el-select v-model="editCompetition.teacher" multiple>
                    <el-option v-for="teacher in TeacherNames" :key="teacher" :label="teacher" :value="teacher"/>
                </el-select>
            </el-form-item>
        </el-form>
        <template #footer>
        
            <div class="dialog-footer">
                <el-button type="danger" plain @click="editVisible = false">取消</el-button>
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
import {Plus, Search} from '@element-plus/icons-vue'
import{ drop, listTeamCompetitionVOByPage,edit,add} from '@/api/contest/teamCompetitoon'
import {getProjectNames} from '@/api/contest/Project.js'
import {ElMessage} from "element-plus";
import {getTeacherNames} from "@/api/contest/teacher.js";


//表格样式
const rowClass = () => ({'text-align': 'center'})
const headClass = () => ({'text-align': 'center', background: '#f8f8f9'})
//表单验证规则
const rules = {
    name: [
        { required: true, message: '请输入竞赛名称', trigger: 'blur' },
        { min: 2, max: 50, message: '竞赛名称长度在 2 到 50 个字符之间', trigger: 'blur' }
    ],
    year: [
        { required: true, message: '请选择竞赛年份', trigger: 'change' }
    ],
    type: [
        { required: true, message: '请选择竞赛类型', trigger: 'change' }
    ],
    status: [
        { required: true, message: '请选择竞赛状态', trigger: 'change' }
    ],
    registrationDeadline: [
        { required: true, message: '请选择报名截止日期', trigger: 'change' }
    ],
    project: [
        { required: true, message: '请选择竞赛项目', trigger: 'change' }
    ],
    teacher: [
        { required: true, message: '请选择指导老师', trigger: 'change' }
    ]
};

const ruleFormRef = ref()

//搜索
const searchContent = reactive({
    name: '',
    year: '',
    status: ''
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
        let response =await listTeamCompetitionVOByPage(params)
        tableData.value=response.data.records
        total.value=parseInt(response.data.total)
    }
}
const competitionYears = [
    {value: '2022', label: '2022'},
    {value: '2023', label: '2023'},
    {value: '2024', label: '2024'}
]
const competitionStatuses = [
    {value: '1', label: '进行中'},
    {value: '0', label: '已结束'}
]
//重置
const reset=()=>{
    Object.keys(searchContent).forEach((key)=>{
        searchContent[key]=""
    })
    page.currentPage=1
    page.pageSize=8
    initData(page.currentPage,page.pageSize)
}


//表格数据
const tableData = ref([])

//增加
const addVisible = ref(false)
const addCompetition = reactive({
    name: '',
    year: '',
    type: '',
    status :'',
    registrationDeadline: '',
    project: '',
    teacher: ''
})
const confirmAdd=async ()=>{
    ruleFormRef.value.validate(async (valid)=>{
        if(valid){
            let response=await add(addCompetition)
            if(response.code===0){
                ElMessage.success("操作成功")
                initData(page.currentPage,page.pageSize)
                Object.keys(addCompetition).forEach(key=>{
                    addCompetition[key]=" "
                })
                console.log(addCompetition)
                addVisible.value=false
            }
            else {
                ElMessage.error(response.message)
            }
        }
        else{
            // 如果验证失败，显示错误消息
            ElMessage.error("表单验证失败，请检查输入项")
        }
    })

}

//删除操作
const deleteVisible = ref(false)
const deleteId=ref(null)
const handleDelete = (row) => {
    deleteVisible.value = true
    deleteId.value = row.id
    console.log(deleteId.value)
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
const editCompetition = reactive({
    name:"",
    year:"",
    type:"",
    status:"",
    registrationDeadline:"",
    project:"",
    teacher:""
})
const handleEdit = (row) => {
    Object.assign(editCompetition, row)
    editVisible.value = true
}
const confirmEdit=async ()=>{
    let response=await edit(editCompetition)
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
const total = ref(20)
//每页展示条数发生改变
const handleSizeChange = (val) => {
  page.pageSize = val
  initData(page.currentPage,page.pageSize)
}
//当前发生改变
const handleCurrentChange = (val) => {
  page.currentPage = val
  initData(page.currentPage,page.pageSize,searchContent)

}

//学生名字列表
const TeacherNames = ref([])
//项目名字列表
const ProjectNames=ref([])
const getProjectName = async () => {
    ProjectNames.value=(await getProjectNames()).data
}
const getTeacherName = async () => {
    TeacherNames.value = (await getTeacherNames()).data
}

//初始化数据
const initData = async (currentPage, pageSize,searchContent) => {
  let response = await listTeamCompetitionVOByPage({
    currentPage,
    pageSize,
    ...searchContent
  })
  tableData.value=response.data.records
  total.value=parseInt(response.data.total)
}

onMounted(async () => {
  initData(page.currentPage,page.pageSize)
  getProjectName()
  getTeacherName()
})


</script>

<style scoped>
el-form-item {
    margin: 10px;
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

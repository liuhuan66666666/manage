<template>
    <div style="margin-top: 20px">
        <div class="search">
            <div>
                <el-select v-model="searchContent.name" placeholder="竞赛名称" style="width: 240px;">
                    <el-option v-for="item in competitionNames" :key="item.value" :label="item.label"
                               :value="item.value"/>
                </el-select>
            </div>
            <div>
                <el-select v-model="searchContent.year" placeholder="竞赛年份" style="width: 240px;">
                    <el-option v-for="item in competitionYears" :key="item.value" :label="item.label"
                               :value="item.value"/>
                </el-select>
            </div>
            <div>
                <el-select v-model="searchContent.status" placeholder="竞赛状态" style="width: 240px;">
                    <el-option v-for="item in competitionStatuses" :key="item.value" :label="item.label"
                               :value="item.value"/>
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
            <el-table-column prop="status" label="竞赛状态"
                             :formatter="(row) => row.status === 1 ? '进行中' : row.status === 0 ? '已经结束' : '未知'"/>
            <el-table-column prop="registrationDeadline" label="报名截止日期"/>
            <el-table-column prop="endDeadline" label="截止日期"/>
            <el-table-column prop="student" label="竞赛人"/>
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

  <!--新增-->
    <el-dialog v-model="addVisible" width="400" center>
        <el-form :model="addCompetition" label-width="auto" style="width: 100%;" :rules="rules" ref="ruleFormRef">
            <el-form-item label="竞赛名称" prop="name">
                <el-input v-model="addCompetition.name"/>
            </el-form-item>
            <el-form-item label="竞赛年份" prop="year">
                <el-select v-model="addCompetition.year" prop="year">
                    <el-option label="2022" :value="2022"/>
                    <el-option label="2023" :value="2023"/>
                    <el-option label="2024" :value="2024"/>
                </el-select>
            </el-form-item>
            <el-form-item label="竞赛类型" prop="type">
                <el-select v-model="addCompetition.type" prop="type">
                    <el-option label="A类" :value="'A类'"/>
                    <el-option label="B类" :value="'B类'"/>
                </el-select>
            </el-form-item>
            <el-form-item label="竞赛状态" prop="status">
                <el-select v-model="addCompetition.status" prop="status">
                    <el-option label="进行中" value="进行中"/>
                    <el-option label="已结束" value="已结束"/>
                </el-select>
            </el-form-item>
            <el-form-item label="报名截止日期" prop="registrationDeadline">
                <el-date-picker v-model="addCompetition.registrationDeadline" type="date"
                                placeholder="请选择报名截止日期"/>
            </el-form-item>
            <el-form-item label="截止日期" prop="endDeadline">
                <el-date-picker v-model="addCompetition.endDeadline" type="date" placeholder="请选择截止日期"/>
            </el-form-item>
            <el-form-item label="竞赛人" prop="student">
                <el-select v-model="addCompetition.student" filterable prop="student">
                    <el-option v-for="student in StudentNames" :key="student" :label="student" :value="student"/>
                </el-select>
            </el-form-item>
            <el-form-item label="指导老师" prop="teacher">
                <el-select v-model="addCompetition.teacher" filterable multiple prop="teacher">
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


    <el-dialog v-model="editVisible" width="400" center>
        <el-form :model="editCompetition" label-width="auto" style="width: 100%;">
            <el-form-item label="竞赛名称" prop="name">
                <el-input v-model="editCompetition.name"/>
            </el-form-item>
            <el-form-item label="竞赛年份">
                <el-input v-model.number="editCompetition.year"/>
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
                <el-input v-model="editCompetition.registrationDeadline" type="Date"/>
            </el-form-item>
            <el-form-item label="截止日期">
                <el-input v-model="editCompetition.endDeadline" type="Date"/>
            </el-form-item>
            <el-form-item label="竞赛人">
                <!--从后端获取竞赛学生列表进行选择-->
                <el-select v-model="editCompetition.student" filterable>
                    <el-option v-for="student in StudentNames" :key="student" :label="student" :value="student"/>
                </el-select>
            </el-form-item>
            <el-form-item label="指导老师">
                <el-select v-model="editCompetition.teacher" filterable multiple>
                    <el-option v-for="teacher in TeacherNames" :key="teacher" :label="teacher" :value="teacher"/>
                </el-select>
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
import {Plus, Refresh, Search} from '@element-plus/icons-vue'
import {getStudentNames} from "@/api/contest/student.js";
import {getTeacherNames} from "@/api/contest/teacher.js";
import {listIndividualCompetitionVOByPage, add, drop, edit} from "@/api/contest/individualCompetition.js";
import {ElMessage} from "element-plus";

//表单验证规则
const rules = {
    name: [
        {required: true, message: '请输入竞赛名称', trigger: 'blur'}
    ],
    year: [
        {required: true, message: '请输入竞赛年份', trigger: 'blur'},
        {type: 'number', message: '年份必须是数字', trigger: 'blur'}
    ],
    type: [
        {required: true, message: '请输入竞赛类型', trigger: 'blur'}
    ],
    status: [
        {required: true, message: '请输入竞赛状态', trigger: 'blur'}
    ],
    registrationDeadline: [
        {required: true, message: '请选择报名截止日期', trigger: 'change'}
    ],
    endDeadline: [
        {required: true, message: '请选择竞赛结束日期', trigger: 'change'}
    ],
    student: [
        {required: true, message: '请选择竞赛人', trigger: 'change'}
    ],
    teacher: [
        {required: true, type: 'array', min: 1, message: '请选择指导老师', trigger: 'change'}
    ]
};
const ruleFormRef = ref()


const rowClass = () => ({'text-align': 'center'})
const headClass = () => ({'text-align': 'center', background: '#f8f8f9'})

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
        let response = await listIndividualCompetitionVOByPage(params)
        tableData.value = response.data.records
        total.value = parseInt(response.data.total)
    }
}

const competitionNames = [
    {value: '计算机设计大赛', label: '计算机设计大赛'},
    {value: '软件杯', label: '软件杯'},
    {value: '蓝桥杯', label: '蓝桥杯'},
]

const competitionYears = [
    {value: 2022, label: '2022'},
    {value: 2023, label: '2023'},
    {value: 2024, label: '2024'},
]

const competitionStatuses = [
    {value: 1, label: '进行中'},
    {value: 0, label: '已结束'}
]

const tableData = ref([])


//增加
const addVisible = ref(false)
const addCompetition = reactive({
    name: '',
    year: '',
    type: '',
    status: '',
    registrationDeadline: '',
    endDeadline: '',
    student: [],
    teacher: []
})
const confirmAdd = async () => {
    // 先进行表单验证
    ruleFormRef.value.validate(async (valid) => {
        if (valid) {
            // 表单验证通过，执行提交操作
            let response = await add(addCompetition)
            if (response.code === 0) {
                ElMessage.success("操作成功")
                initData(page.currentPage, page.pageSize)
                // 重置表单数据
                Object.keys(addCompetition).forEach(key => {
                    addCompetition[key] = " "
                })
                addVisible.value = false
            } else {
                ElMessage.error(response.message)
            }
        } else {
            // 如果验证失败，显示错误消息
            ElMessage.error("表单验证失败，请检查输入项")
        }
    })
}


//编辑
const editVisible = ref(false)
const editCompetition = reactive({
    name: '',
    year: '',
    type: '',
    status: '',
    registrationDeadline: '',
    endDeadline: '',
    student: [],
    teacher: []
})
const handleEdit = (row) => {
    Object.assign(editCompetition, row)
    editVisible.value = true
}
const confirmEdit = async () => {
    let response = await edit(editCompetition)
    if (response.code === 0) {
        ElMessage.success("操作成功")
        initData(page.currentPage, page.pageSize, searchContent)
        editVisible.value = false
    } else {
        ElMessage.error(response.message)
    }
}
//删除操作
const deleteVisible = ref(false)
const deleteId = ref(null)
const handleDelete = (row) => {
    deleteVisible.value = true
    deleteId.value = row.id
}
const confirmDelete = async () => {
    let response = await drop({id: deleteId.value})
    if (response.code === 0) {
        ElMessage.success("操作成功")
        initData(page.currentPage, page.pageSize, searchContent)
        deleteVisible.value = false
    } else {
        ElMessage.error(response.message)
    }
}


//分页
const page = reactive({currentPage: 1, pageSize: 10})
const total = ref(20)
//每页展示条数发生改变
const handleSizeChange = (val) => {
    page.pageSize = val
    initData(page.currentPage, page.pageSize, searchContent)
}

//当前发生改变
const handleCurrentChange = (val) => {
    page.currentPage = val
    initData(page.currentPage, page.pageSize, searchContent)

}


//学生名字列表
const StudentNames = ref([])
//学生教师列表
const TeacherNames = ref([])

const getStudentName = async () => {
    StudentNames.value = (await getStudentNames()).data
}

const getTeacherName = async () => {
    TeacherNames.value = (await getTeacherNames()).data
}

//初始化数据
const initData = async (currentPage, pageSize, searchContent) => {
    let response = await listIndividualCompetitionVOByPage({
        currentPage,
        pageSize,
        ...searchContent
    })
    tableData.value = response.data.records
    total.value = parseInt(response.data.total)
}


onMounted(async () => {
    getStudentName()
    getTeacherName()
    initData(page.currentPage, page.pageSize)
})


//重置

const reset = () => {
    Object.keys(searchContent).forEach((key) => {
        searchContent[key] = ""
    })
    page.currentPage = 1
    page.pageSize = 8
    initData(page.currentPage, page.pageSize)
}


</script>

<style scoped>
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

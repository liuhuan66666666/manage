<template>
    <div style="margin-top: 20px">
        <div style="display: flex; justify-content: space-between">
            <el-form :inline="true" :model="searchContent" style="margin-left: 20px">
                <el-form-item label="项目名称:">
                  <el-input placeholder="请输入项目名称" v-model="searchContent.name" > </el-input>
                </el-form-item>
                <el-form-item label="项目负责人:">
                    <el-select v-model="searchContent.leader" placeholder="请选择项目组长" style="width: 240px;" filterable>
                      <el-option v-for="student in StudentNames" :key="student" :label="student" :value="student"/>
                    </el-select>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" :icon="Search" @click="search">搜索</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button :icon="Refresh" type="primary" plain @click="reset">重置</el-button>
                </el-form-item>
            </el-form>
            <el-button type="primary" style="margin-right: 10px" @click="showAddDialog">添加项目</el-button>
        </div>
        <main>
            <el-table :data="tableData" style="width: 100%" :cell-style="rowClass" :header-cell-style="headClass">
                <el-table-column prop="name" label="项目名称" />
                <el-table-column prop="source_code_path" label="源代码路径"  show-overflow-tooltip />
                <el-table-column prop="status" label="项目状态" :formatter="(row) => row.status === 1 ? '进行中' : row.status === 0 ? '已完成' : '未知'"/>
                <el-table-column prop="startDate" label="项目启动日期" />
                <el-table-column prop="leader" label="项目组长" />
                <el-table-column prop="student" label="项目成员" show-overflow-tooltip />
                <el-table-column prop="teacher" label="项目指导老师" show-overflow-tooltip />
                <el-table-column label="操作">
                    <template #default="scope">
                        <el-button size="small" @click="showEditDialog(scope.row)">编辑</el-button>
                        <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </main>
        <el-dialog title="添加项目" v-model="isAddDialogVisible" width="400" center align-center>
            <el-form :model="newProject" :rules="newProjectRules" label-width="auto" ref="newProjectForm">
                <el-form-item label="项目名称" prop="name">
                    <el-input v-model="newProject.name" />
                </el-form-item>
                <el-form-item label="源代码路径" prop="source_code_path">
                    <el-input v-model="newProject.source_code_path" />
                </el-form-item>
                <el-form-item label="项目状态" prop="status">
                    <el-select v-model="newProject.status" placeholder="请选择项目状态">
                        <el-option label="进行中" :value="1" />
                        <el-option label="已完成" :value="0" />
                    </el-select>
                </el-form-item>
                <el-form-item label="项目启动日期" prop="startDate">
                    <el-date-picker v-model="newProject.startDate" type="date" placeholder="选择日期" />
                </el-form-item>
                <el-form-item label="项目组长" prop="leader">
                    <el-select v-model="newProject.leader" placeholder="请选择项目组长" filterable>
                        <el-option v-for="student in StudentNames" :key="student" :label="student" :value="student"/>
                    </el-select>
                </el-form-item>
                <el-form-item label="项目成员" prop="student">
                    <el-select v-model="newProject.student" placeholder="请选择项目成员" filterable multiple>
                        <el-option v-for="student in StudentNames" :key="student" :label="student" :value="student"/>
                    </el-select>
                </el-form-item>
                <el-form-item label="项目指导老师" prop="teacher">
                    <el-select v-model="newProject.teacher" placeholder="请选择项目指导老师" filterable multiple>
                        <el-option v-for="teacher in TeacherNames" :key="teacher" :label="teacher" :value="teacher"/>
                    </el-select>
                </el-form-item>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button type="danger" plain @click="isAddDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="confirmAdd">确认</el-button>
                </div>
            </template>
        </el-dialog>

        <el-dialog title="编辑项目" v-model="isEditDialogVisible" width="400" center>
            <el-form :model="editProject" label-width="auto">
                <el-form-item label="项目名称">
                    <el-input v-model="editProject.name" />
                </el-form-item>
                <el-form-item label="源代码路径">
                    <el-input v-model="editProject.source_code_path" />
                </el-form-item>
                <el-form-item label="项目状态">
                    <el-select v-model="editProject.status" placeholder="请选择项目状态">
                        <el-option label="进行中" :value="1" />
                        <el-option label="已完成" :value="0" />
                    </el-select>
                </el-form-item>
                <el-form-item label="项目启动日期">
                    <el-date-picker v-model="editProject.startDate" type="date" placeholder="选择日期" />
                </el-form-item>
                <el-form-item label="项目组长">
                    <el-select v-model="editProject.leader" placeholder="请选择项目组长" filterable>
                      <el-option v-for="student in StudentNames" :key="student" :label="student" :value="student"/>
                    </el-select>
                </el-form-item>
                <el-form-item label="项目成员">
                    <el-select v-model="editProject.student" placeholder="请选择项目成员" multiple>
                      <el-option v-for="student in StudentNames" :key="student" :label="student" :value="student"/>
                    </el-select>
                </el-form-item>
                <el-form-item label="项目指导老师">
                    <el-select v-model="editProject.teacher" placeholder="请选择项目成员" multiple>
                      <el-option v-for="teacher in TeacherNames" :key="teacher" :label="teacher" :value="teacher"/>
                    </el-select>
                </el-form-item>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button type="danger" plain @click="isEditDialogVisible = false">取消</el-button>
                    <el-button type="primary"@click="confirmEdit">
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
        <div style="float: right; margin-top: 20px; margin-right: 10px">
            <el-pagination :page-sizes="[8, 10]"
                           background layout="total, sizes, prev, pager, next, jumper"
                           :total="total" :current-page="page.currentPage" :page-size="page.pageSize"
                           @size-change="handleSizeChange"
                           @current-change="handleCurrentChange"
            />
        </div>
    </div>
</template>

<script setup>
import {onMounted, reactive, ref} from "vue";
import {Refresh, Search} from "@element-plus/icons-vue";
import {listProjectVOByPage, edit, drop,add} from "@/api/contest/Project.js";
import {getStudentNames} from "@/api/contest/student.js";
import {getTeacherNames} from "@/api/contest/teacher.js";
import {ElMessage} from "element-plus";

//表单验证
const newProjectForm=ref()
const newProjectRules = {
    name: [
        { required: true, message: '请输入项目名称', trigger: 'blur' }
    ],
    source_code_path: [
        { required: true, message: '请输入源代码路径', trigger: 'blur' }
    ],
    status: [
        { required: true, message: '请选择项目状态', trigger: 'change' }
    ],
    startDate: [
        { required: true, message: '请选择项目启动日期', trigger: 'change' }
    ],
    leader: [
        { required: true, message: '请选择项目组长', trigger: 'change' }
    ],
    student: [
        { required: true, message: '请选择项目成员', trigger: 'change' }
    ],
    teacher: [
        { required: true, message: '请选择项目指导老师', trigger: 'change' }
    ]
};
const rowClass = () => ({ 'text-align': 'center' });
const headClass = () => ({ 'text-align': 'center', background: '#f8f8f9' });

//搜索条件
const searchContent = reactive({
    projectName: '',
    leader: ''
});
//搜索
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
    let response =await listProjectVOByPage(params)
    tableData.value=response.data.records
    total.value=parseInt(response.data.total)
  }
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

//表格数据
const tableData = ref([])

//增加
const isAddDialogVisible = ref(false);
const newProject = reactive({
  name: '',
  source_code_path: '',
  status: '',
  startDate: '',
  leader: '',
  student: '',
  teacher:''
});
const showAddDialog = () => {
  isAddDialogVisible.value = true;
};
// 确认添加项目
const confirmAdd = async () => {
    // 调用表单验证
    await newProjectForm.value.validate(async (valid) => {
        if (valid) {
            let response = await add(newProject);
            if (response.code === 0) {
                ElMessage.success("操作成功");
                initData(page.currentPage, page.pageSize);
                Object.keys(newProject).forEach(key => {
                    newProject[key] = " ";
                });
                isAddDialogVisible.value = false;
            } else {
                ElMessage.error(response.message);
            }
        } else {
            ElMessage.warning("请填写完整信息");
        }
    });
};


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
    initData(page.currentPage,page.pageSize,searchContent)
    deleteVisible.value=false
  }
  else {
    ElMessage.error(response.message)
  }
}


//编辑
const isEditDialogVisible = ref(false);
const editProject = reactive({
  name: '',
  source_code_path: '',
  status: '',
  startDate: '',
  student: '',
  teacher: '',
  leader: ''
});
const showEditDialog = (row) => {
  Object.assign(editProject, row);
  isEditDialogVisible.value = true;
};
const confirmEdit =async () => {
  let response=await edit(editProject)
  if(response.code===0){
    ElMessage.success("操作成功")
    initData(page.currentPage,page.pageSize,searchContent)
  }
  else {
    ElMessage.error(response.message)
  }
  isEditDialogVisible.value = false;
};


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

//分页
const page = reactive({
    currentPage: 1,
    pageSize: 8,
})
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
  let response= await listProjectVOByPage({
    currentPage,
    pageSize,
    ...searchContent
  })
  tableData.value=response.data.records
  total.value=parseInt(response.data.total)
}

onMounted(()=>{
  getTeacherName()
  getStudentName()
  initData(page.currentPage,page.pageSize)
})
</script>


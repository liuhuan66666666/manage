import axios from "axios";

// 创建axios实例
const excelAxios = axios.create({
    baseURL:'/api',
    responseType: 'blob' // 设置默认响应类型为blob
});

// 定义导出Excel的函数

export const exportExcel=(url,data)=>{
    excelAxios.post(url,data).then((res)=>{
        // 创建Blob对象
        const blob = new Blob([res.data], {
            type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
        });

        // 创建URL对象
        const urls = window.URL.createObjectURL(blob);

        // 创建a标签用于下载
        const link = document.createElement('a');
        link.href = urls;
        link.setAttribute('download', '成员数据表.xlsx'); // 文件名

        // 添加链接到文档并触发点击事件
        document.body.appendChild(link);
        link.click();

        // 清理
        link.parentNode.removeChild(link);
        window.URL.revokeObjectURL(urls);
    })
}
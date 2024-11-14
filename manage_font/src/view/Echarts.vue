<template>
    <div id="echarts-container">
        <div class="chart-row">
            <div ref="majorDistributionChart"></div>
            <div ref="departmentDistributionChart"></div>
        </div>
        <div class="chart-row">
            <div ref="classDistributionChart"></div>
            <div ref="integralRankingChart"></div>
        </div>
    </div>
</template>

<script setup>
import * as echarts from 'echarts'
import { ref, onMounted, reactive } from 'vue'
import { getMajorDistribution, getDepartmentDistribution } from "@/api/echarts.js";
import {getListIntegralVO} from "@/api/information/integral.js"

// 定义图表的 DOM 引用
const majorDistributionChart = ref(null)
const classDistributionChart = ref(null)
const integralRankingChart = ref(null)
const departmentDistributionChart = ref(null)

// 人员专业分布（柱状图）
const majorDistributionOption = reactive({
    title: { text: '人员专业分布', x: 'center' },
    tooltip: { trigger: 'axis', axisPointer: { type: 'shadow' } },
    grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
    xAxis: [{ type: 'category', data: [], axisTick: { alignWithLabel: true } }],
    yAxis: [{ type: 'value' }],
    series: [{ name: '人数', type: 'bar', barWidth: '60%', data: [] }]
})

// 工作室积分排行（折线图）
const integralRankingOption = reactive({
    title: { text: '工作室积分排行', x: 'center' },
    xAxis: { type: 'category', data: [] },
    yAxis: { type: 'value' },
    series: [{ data: [], type: 'line', smooth: true }]
})

// 各部门成员比例（饼图）
const departmentDistributionOption = reactive({
    title: { text: '各部门成员比例', x: 'center' },
    tooltip: { trigger: 'item' },
    legend: { top: '5%', left: 'center' },
    series: [{ type: 'pie', radius: ['40%', '70%'], data: [] }]
})

// 人员年级分布（雷达图）
const classDistributionOption = reactive({
    title: { text: '人员年级分布', x: 'center' },
    tooltip: {
        trigger: 'item',
        formatter: function (params) {
            return `${params.name}<br/>人数: ${params.value}`;
        }
    },
    radar: {
        indicator: [
            { name: '2024级', max: 100 },
            { name: '2023级', max: 100 },
            { name: '2022级', max: 100 },
        ]
    },
    series: [{
        name: '班级人数分布',
        type: 'radar',
        data: [
            {
                value: [17, 20, 33],  // 实际数据
                name: '人数'
            }
        ]
    }]
})


// 初始化图表函数
const initChart = (chartRef, option) => {
    const chartInstance = echarts.init(chartRef.value)
    chartInstance.setOption(option)
    return chartInstance
}

onMounted(() => {
    // 人员专业分布图表
    getMajorDistribution().then(res => {
        majorDistributionOption.xAxis[0].data = res.data.categories
        majorDistributionOption.series[0].data = res.data.data
        initChart(majorDistributionChart, majorDistributionOption)
    })

    // 工作室积分排行图表
    getListIntegralVO({ currentPage: 1, pageSize: 7 }).then(res => {
        const names = res.data.records.map(record => record.name)
        const scores = res.data.records.map(record => record.score)
        integralRankingOption.xAxis.data = names
        integralRankingOption.series[0].data = scores
        initChart(integralRankingChart, integralRankingOption)
    })

    // 部门成员比例图表
    getDepartmentDistribution().then(res => {
        const chartData = res.data.data.map(item => ({ name: item.name, value: Number(item.value) }))
        departmentDistributionOption.series[0].data = chartData
        initChart(departmentDistributionChart, departmentDistributionOption)
    })

    // 模拟“人员年级分布”图表的数据并初始化
    initChart(classDistributionChart, classDistributionOption)
})
</script>

<style lang="less" scoped>
#echarts-container {
    .chart-row {
        width: 80vw;
        height: 47vh;
        margin: 10px auto;
        display: flex;
        justify-content: space-between;

        >div {
            width: 49%;
            height: 100%;
        }
    }
}
</style>

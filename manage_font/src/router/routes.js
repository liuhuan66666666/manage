import Access_Enum from "@/access/access_Enum.js";

export const routes = [
    {
        path: "/",
        redirect: "/home",
        meta:{
            hidden: true
        }
    },
    {
        path: '/home',
        component: () => import('@/components/Home.vue'),
        children: [
            {
                path: "/member",
                name: "成员管理",
                icon: 'user',
                component: () => import('@/view/Member.vue'),
                meta: {
                    access: Access_Enum.ADMIN
                }
            }, {
                path: "/department",
                name: "部门管理",
                icon: 'office-building',
                component: () => import('@/view/department.vue'),
                meta: {
                    access: Access_Enum.ADMIN
                }
            },
            {
                path: "/information",
                name: "信息管理",
                icon: 'InfoFilled',
                meta: {
                    access: Access_Enum.USER
                },
                children: [
                    {
                        path: "activity",
                        name: "活动管理",
                        icon: 'Tickets',
                        component: () => import('@/view/information/activity.vue'),
                    }, {
                        path: "announcement",
                        name: "通告通知",
                        icon: 'Notification',
                        component: () => import('@/view/information/announcement.vue'),
                    }, {
                        path: "integral",
                        name: "积分管理",
                        icon: 'Star',
                        component: () => import('@/view/information/integral.vue'),
                    }
                ]
            },
            {
                path: "/contest",
                name: "竞赛管理",
                icon: 'Trophy',
                meta: {
                    access: Access_Enum.USER
                },
                children: [
                    {
                        path: "student",
                        name: "学生管理",
                        icon: 'UserFilled', // 用户管理更适合用UserFilled图标
                        component: () => import('@/view/contest/Student.vue'),
                    },
                    {
                        path: "teacher",
                        name: "教师管理",
                        icon: 'User', // 教师可以用User图标
                        component: () => import('@/view/contest/Teacher.vue'),
                    },
                    {
                        path: "individualCompetition",
                        name: "个人赛",
                        icon: 'Medal', // 比赛相关的项目可以用Medal图标
                        component: () => import('@/view/contest/IndividualCompetition.vue'),
                    },
                    {
                        path: "teamCompetition",
                        name: "团体赛",
                        icon: 'Medal', // 团体赛同样可用Medal图标
                        component: () => import('@/view/contest/TeamCompetition.vue'),
                    },{
                        path: "project",
                        name: "项目管理",
                        icon: 'folder',
                        component: () => import('@/view/contest/project.vue'),
                        meta: {
                            access: Access_Enum.ADMIN
                        }
                    },
                ]

            },

        ]
    }, {
        path: "/login",
        name: "登录",
        component: () => import('@/components/Login.vue'),
        meta:{
            hidden: true
        }
    }
]
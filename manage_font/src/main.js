import { createApp } from 'vue'
//引入根组件
import App from './App.vue'
//引入router
import router from './router'
const app = createApp(App)
//引入pinia
import { createPinia } from 'pinia'
const pinia = createPinia()
//引入element-plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
//引入element-plus图标
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
//引入element-plus中文
import zhCn from 'element-plus/es/locale/lang/zh-cn'
app.use(ElementPlus, {
  locale: zhCn,
})
app.use(router)
app.use(pinia)
app.mount('#app')
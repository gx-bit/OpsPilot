<script setup lang="ts">
import axios from 'axios'
import { onMounted, ref } from 'vue'

type Ticket = {id:number,title:string,description:string,status:string,category:string,priority:string,assignedGroup?:string,aiSummary?:string,aiSuggestion?:string}
const title = ref('')
const description = ref('')
const tickets = ref<Ticket[]>([])
const loading = ref(false)
const error = ref('')

async function load() {
  try { tickets.value = (await axios.get('/api/tickets')).data }
  catch { error.value = '无法连接业务服务，请确认 Docker Compose 已启动。' }
}
async function submit() {
  error.value = ''
  if (!title.value.trim() || !description.value.trim()) { error.value = '请填写标题和故障描述。'; return }
  loading.value = true
  try {
    await axios.post('/api/tickets', {title:title.value, description:description.value})
    title.value = ''; description.value = ''; await load()
  } catch (e:any) { error.value = e.response?.data?.message ?? '提交失败，请稍后重试。' }
  finally { loading.value = false }
}
onMounted(load)
</script>

<template>
  <main>
    <header><div><span class="eyebrow">AI-POWERED IT SERVICE DESK</span><h1>OpsPilot</h1><p>让工单系统保持可靠，让 Agent 负责理解、检索与建议。</p></div><div class="status"><i></i>系统在线</div></header>
    <section class="grid">
      <form class="panel" @submit.prevent="submit">
        <h2>提交故障</h2><p class="muted">用自然语言描述现象、影响范围和期望恢复时间。</p>
        <label>标题<input v-model="title" maxlength="160" placeholder="例如：302 实验室无法访问教务系统"></label>
        <label>详细描述<textarea v-model="description" rows="7" maxlength="5000" placeholder="十几台电脑无法访问教务系统，其他网站正常，下午上课前需要恢复。"></textarea></label>
        <p v-if="error" class="error">{{ error }}</p>
        <button :disabled="loading">{{ loading ? 'Agent 正在分诊…' : '创建并智能分诊' }}</button>
      </form>
      <section class="panel list"><div class="list-head"><div><h2>最近工单</h2><p class="muted">Agent 结果由 Java 业务层校验后保存</p></div><button class="ghost" @click="load">刷新</button></div>
        <article v-for="ticket in [...tickets].reverse()" :key="ticket.id">
          <div class="ticket-top"><strong>#{{ticket.id}} {{ticket.title}}</strong><span :class="['badge', ticket.priority.toLowerCase()]">{{ticket.priority}}</span></div>
          <p>{{ticket.aiSummary || ticket.description}}</p><div class="meta"><span>{{ticket.category}}</span><span>{{ticket.assignedGroup || '待分派'}}</span><span>{{ticket.status}}</span></div>
          <details v-if="ticket.aiSuggestion"><summary>查看 Agent 建议</summary><p>{{ticket.aiSuggestion}}</p></details>
        </article><div v-if="!tickets.length" class="empty">还没有工单，创建第一条演示数据吧。</div>
      </section>
    </section>
  </main>
</template>


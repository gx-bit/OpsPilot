from langgraph.graph import END, START, StateGraph
from langchain_openai import ChatOpenAI
from .config import settings
from .models import AgentState, TriageResult
from .rules import classify, group, priority
from .tools import tools


def analyze(state: AgentState) -> dict:
    request = state["request"]
    text = f"{request.title} {request.description}"
    category = classify(text)
    return {"category": category, "priority": priority(text), "assigned_group": group(category)}


async def retrieve(state: AgentState) -> dict:
    request = state["request"]
    try:
        knowledge = await tools.search_knowledge(state["category"])
        similar = await tools.search_similar_tickets(request.description.split()[0])
        return {"knowledge": knowledge, "similar_tickets": similar}
    except Exception:
        return {"knowledge": [], "similar_tickets": []}


async def synthesize(state: AgentState) -> dict:
    request = state["request"]
    refs = state.get("knowledge", [])
    if settings.llm_api_key:
        model = ChatOpenAI(
            model=settings.llm_model,
            api_key=settings.llm_api_key,
            base_url=settings.llm_base_url,
            temperature=0,
        ).with_structured_output(TriageResult)
        prompt = f"""你是企业 IT 服务台分诊助手。不得执行任何写操作。
工单标题：{request.title}
描述：{request.description}
规则预判分类：{state['category']}，优先级：{state['priority']}，处理组：{state['assigned_group']}
知识库：{refs}
请返回简洁的中文摘要和可验证的排查建议。mode 必须为 llm。"""
        try:
            result = await model.ainvoke(prompt)
            return {"result": result}
        except Exception:
            pass

    knowledge_hint = refs[0]["content"] if refs else "请收集发生时间、影响范围、错误信息和复现步骤。"
    result = TriageResult(
        category=state["category"], priority=state["priority"],
        assignedGroup=state["assigned_group"],
        summary=f"{request.title}：{request.description[:100]}",
        suggestion=f"建议由{state['assigned_group']}处理。{knowledge_hint}", mode="rules")
    return {"result": result}


builder = StateGraph(AgentState)
builder.add_node("analyze", analyze)
builder.add_node("retrieve", retrieve)
builder.add_node("synthesize", synthesize)
builder.add_edge(START, "analyze")
builder.add_edge("analyze", "retrieve")
builder.add_edge("retrieve", "synthesize")
builder.add_edge("synthesize", END)
triage_graph = builder.compile()


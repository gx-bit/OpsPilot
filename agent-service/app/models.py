from typing import Literal, TypedDict
from pydantic import BaseModel, Field

Category = Literal["NETWORK", "ACCOUNT", "SOFTWARE", "HARDWARE", "SERVER", "OTHER"]
Priority = Literal["LOW", "MEDIUM", "HIGH", "URGENT"]


class TriageRequest(BaseModel):
    ticketId: int
    title: str = Field(min_length=1, max_length=160)
    description: str = Field(min_length=1, max_length=5000)


class TriageResult(BaseModel):
    category: Category
    priority: Priority
    assignedGroup: str
    summary: str
    suggestion: str
    mode: Literal["rules", "llm"]


class AgentState(TypedDict, total=False):
    request: TriageRequest
    category: Category
    priority: Priority
    assigned_group: str
    knowledge: list[dict]
    similar_tickets: list[dict]
    result: TriageResult


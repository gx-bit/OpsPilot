import hmac
from fastapi import Depends, FastAPI, Header, HTTPException
from .config import settings
from .graph import triage_graph
from .models import TriageRequest, TriageResult

app = FastAPI(title="OpsPilot Agent Service", version="0.1.0")


def verify_key(x_internal_api_key: str = Header(default="")) -> None:
    if not hmac.compare_digest(x_internal_api_key, settings.internal_api_key):
        raise HTTPException(status_code=401, detail="invalid internal API key")


@app.get("/health")
async def health() -> dict:
    return {"status": "ok", "mode": "llm" if settings.llm_api_key else "rules"}


@app.post("/api/v1/triage", response_model=TriageResult, dependencies=[Depends(verify_key)])
async def triage(request: TriageRequest) -> TriageResult:
    final_state = await triage_graph.ainvoke({"request": request})
    return final_state["result"]


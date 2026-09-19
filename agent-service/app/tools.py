import httpx
from .config import settings


class BusinessTools:
    def __init__(self) -> None:
        self.headers = {"X-Internal-Api-Key": settings.internal_api_key}

    async def search_knowledge(self, category: str) -> list[dict]:
        async with httpx.AsyncClient(timeout=settings.request_timeout_seconds) as client:
            response = await client.get(
                f"{settings.business_api_url}/internal/tools/knowledge",
                params={"category": category}, headers=self.headers)
            response.raise_for_status()
            return response.json()

    async def search_similar_tickets(self, keyword: str) -> list[dict]:
        async with httpx.AsyncClient(timeout=settings.request_timeout_seconds) as client:
            response = await client.get(
                f"{settings.business_api_url}/internal/tools/similar-tickets",
                params={"keyword": keyword[:20]}, headers=self.headers)
            response.raise_for_status()
            return response.json()


tools = BusinessTools()


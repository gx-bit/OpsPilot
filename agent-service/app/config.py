from pydantic_settings import BaseSettings, SettingsConfigDict


class Settings(BaseSettings):
    business_api_url: str = "http://localhost:8080"
    internal_api_key: str = "change-me-in-production"
    llm_api_key: str = ""
    llm_base_url: str = "https://api.openai.com/v1"
    llm_model: str = "gpt-4.1-mini"
    request_timeout_seconds: float = 8.0
    model_config = SettingsConfigDict(env_file=".env", extra="ignore")


settings = Settings()


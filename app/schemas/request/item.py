from pydantic import BaseModel
from uuid import UUID

class ItemCreateRequest(BaseModel):
    name: str
    slug: str

class ItemUpdateRequest(BaseModel):
    name: str | None = None
    slug: str | None = None

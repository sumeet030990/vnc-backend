from pydantic import BaseModel
from uuid import UUID
from typing import Optional


class ItemCreateRequest(BaseModel):
    name: str
    slug: str

class ItemUpdateRequest(BaseModel):
    name: Optional[str] = None
    slug: Optional[str] = None


class ItemResponse(BaseModel):
    id: UUID
    name: str
    slug: str

    class Config:
      from_attributes = True


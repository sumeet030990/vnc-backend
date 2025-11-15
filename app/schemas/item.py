from pydantic import BaseModel
from uuid import UUID
from typing import Optional


class ItemUpsertRequest(BaseModel):
    name: str
    slug: str


class ItemResponse(BaseModel):
    id: UUID
    name: str
    slug: str

    class Config:
      from_attributes = True


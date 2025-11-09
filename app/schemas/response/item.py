from pydantic import BaseModel
from uuid import UUID

class ItemResponse(BaseModel):
    id: UUID
    name: str
    slug: str

    class Config:
      from_attributes = True


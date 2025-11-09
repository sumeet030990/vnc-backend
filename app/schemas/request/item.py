from pydantic import BaseModel
from typing import Optional

class ItemCreateRequest(BaseModel):
    name: str
    slug: str

class ItemUpdateRequest(BaseModel):
    name: Optional[str] = None
    slug: Optional[str] = None

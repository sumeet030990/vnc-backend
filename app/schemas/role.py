from pydantic import BaseModel
from uuid import UUID
from typing import Optional
class RoleResponse(BaseModel):
    id: UUID
    name: str
    slug: str

    class Config:
        from_attributes = True

class RoleCreateRequest(BaseModel):
    name: str
    slug: str

class RoleUpdateRequest(BaseModel):
    name: Optional[str] = None
    slug: Optional[str] = None

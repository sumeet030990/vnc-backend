from pydantic import BaseModel
from typing import Optional

class RoleCreateRequest(BaseModel):
    name: str
    slug: str

class RoleUpdateRequest(BaseModel):
    name: Optional[str] = None
    slug: Optional[str] = None

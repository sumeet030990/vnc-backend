from pydantic import BaseModel
from uuid import UUID

class RoleCreateRequest(BaseModel):
    name: str
    slug: str

class RoleUpdateRequest(BaseModel):
    name: str | None = None
    slug: str | None = None

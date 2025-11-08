from pydantic import BaseModel
from uuid import UUID

class RoleResponse(BaseModel):
    id: UUID
    name: str
    slug: str

    class Config:
        orm_mode = True

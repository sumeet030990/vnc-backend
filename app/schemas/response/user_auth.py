from pydantic import BaseModel
from uuid import UUID

class UserAuthResponse(BaseModel):
    id: UUID
    user_id: UUID
    user_name: str

    class Config:
        orm_mode = True

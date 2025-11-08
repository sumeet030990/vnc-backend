from pydantic import BaseModel
from uuid import UUID

class UserAuthCreateRequest(BaseModel):
    user_id: UUID
    user_name: str
    password: str

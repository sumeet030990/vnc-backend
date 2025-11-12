from pydantic import BaseModel
from uuid import UUID

class UserAuthResponse(BaseModel):
    id: UUID
    user_id: UUID
    user_name: str

    model_config = {"from_attributes": True}


class UserAuthCreateRequest(BaseModel):
    user_id: UUID
    user_name: str
    password: str

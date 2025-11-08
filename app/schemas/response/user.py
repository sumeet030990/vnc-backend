from pydantic import BaseModel
from uuid import UUID
from typing import Optional

class UserResponse(BaseModel):
    id: UUID
    name: Optional[str]
    company_name: Optional[str]
    contact_number: Optional[str]
    address: Optional[str]
    city: Optional[str]
    gst_number: Optional[str]
    role_id: UUID

    class Config:
        orm_mode = True

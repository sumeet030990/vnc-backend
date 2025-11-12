from pydantic import BaseModel
from typing import Optional
from uuid import UUID

class UserCreateRequest(BaseModel):
    name: Optional[str] = None
    company_name: Optional[str] = None
    contact_number: Optional[str] = None
    address: Optional[str] = None
    city: Optional[str] = None
    gst_number: Optional[str] = None
    role_id: UUID
    user_name: Optional[str] = None
    password: Optional[str] = None

    def validate(self):
        if not self.name and not self.company_name:
            raise ValueError("Either name or company_name must be provided.")

class UserUpdateRequest(BaseModel):
    name: Optional[str] = None
    company_name: Optional[str] = None
    contact_number: Optional[str] = None
    address: Optional[str] = None
    city: Optional[str] = None
    gst_number: Optional[str] = None
    role_id: Optional[UUID] = None
    

# Example entity response (keep for import in controllers/routes)
class UserResponseBody(BaseModel):
    id: UUID
    name: Optional[str]
    company_name: Optional[str]
    contact_number: Optional[str]
    address: Optional[str]
    city: Optional[str]
    gst_number: Optional[str]
    role_id: UUID

    model_config = {"from_attributes": True}

class UserWithUserAuthResponse(UserResponseBody):
    user_name: str

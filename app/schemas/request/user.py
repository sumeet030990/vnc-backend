from pydantic import BaseModel
from uuid import UUID
from typing import Optional

class UserCreateRequest(BaseModel):
    name: Optional[str] = None
    company_name: Optional[str] = None
    contact_number: Optional[str] = None
    address: Optional[str] = None
    city: Optional[str] = None
    gst_number: Optional[str] = None
    role_id: UUID

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

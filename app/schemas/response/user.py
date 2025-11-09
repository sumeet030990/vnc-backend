
from pydantic import BaseModel
from typing import Optional
from uuid import UUID


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



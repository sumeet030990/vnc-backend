from pydantic import BaseModel


class UserCreate(BaseModel):
    name: str
    firm_name: str
    contact_number: str
    address: str
    city: str
    gst_number: str
    role_id: int
    user_name: str
    password: str


class UserRead(BaseModel):
    id: int
    name: str
    firm_name: str
    contact_number: str
    address: str
    city: str
    gst_number: str
    role_id: int
    user_name: str

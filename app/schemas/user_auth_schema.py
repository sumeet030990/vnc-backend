from pydantic import BaseModel

class UserAuthCreate(BaseModel):
    user_id: int
    user_name: str
    password: str

class UserAuthRead(BaseModel):
    id: int
    user_id: int
    user_name: str

from pydantic import BaseModel

class LoginAuthRequest(BaseModel):
    user_name: str
    password: str
from pydantic import BaseModel

from app.schemas.user import UserWithUserAuthResponse

class LoginAuthRequest(BaseModel):
    user_name: str
    password: str
    

class AccessToken(BaseModel):
    access_token: str
    user : UserWithUserAuthResponse
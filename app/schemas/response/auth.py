
from pydantic import BaseModel

from app.schemas.response.user import UserWithUserAuthResponse

class AccessToken(BaseModel):
    access_token: str
    user : UserWithUserAuthResponse
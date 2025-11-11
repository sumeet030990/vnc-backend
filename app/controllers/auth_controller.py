
from fastapi import Depends
from app.schemas.response.auth import AccessToken
from app.services.auth_service import AuthService
from sqlalchemy.orm import Session
from app.schemas.request.auth import LoginAuthRequest

class AuthController:
    def __init__(self, db: Session):
        self.auth_service = AuthService(db)
        self.db = db
    
    def login_user(self, payload: LoginAuthRequest) -> AccessToken:
        return self.auth_service.login_user(payload)
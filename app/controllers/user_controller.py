from fastapi import Depends
from app.services.user_service import UserService
from app.schemas.user_schema import UserCreate, UserRead
from typing import List
from sqlalchemy.orm import Session
from config.database import get_db

class UserController:
    def __init__(self, db: Session = Depends(get_db)):
        self.service = UserService(db)

    def get_users(self) -> List[UserRead]:
        return self.service.get_users()

    def create_user(self, user: UserCreate) -> UserRead:
        return self.service.create_user(user)

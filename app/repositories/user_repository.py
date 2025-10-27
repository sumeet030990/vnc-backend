from sqlalchemy.orm import Session
from app.models.user import User
from app.schemas.user_schema import UserCreate, UserRead
from typing import List
from config.database import SessionLocal

class UserRepository:
    def __init__(self, db: Session = None):
        self.db = db or SessionLocal()

    def get_all(self) -> List[UserRead]:
        users = self.db.query(User).all()
        return [UserRead(id=user.id, name=user.name, email=user.email) for user in users]

    def create(self, user_create: UserCreate) -> UserRead:
        user = User(name=user_create.name, email=user_create.email)
        self.db.add(user)
        self.db.commit()
        self.db.refresh(user)
        return UserRead(id=user.id, name=user.name, email=user.email)

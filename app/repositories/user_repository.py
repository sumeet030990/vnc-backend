from db.models.user import User
from sqlalchemy.orm import Session
from uuid import UUID
from typing import Optional

class UserRepository:
    def __init__(self, db: Session):
        self.db = db

    def get_by_id(self, user_id: UUID):
        return self.db.query(User).filter(User.id == user_id).first()

    def create(self, **kwargs):
        user = User(**kwargs)
        self.db.add(user)
        self.db.commit()
        self.db.refresh(user)
        return user

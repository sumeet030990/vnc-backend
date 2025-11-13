from db.models.user import User
from sqlalchemy.orm import Session
from uuid import UUID
from typing import Optional

class UserRepository:
    def __init__(self, db: Session):
        self.db = db

    def get_all_users(self):
        return self.db.query(User).all()
   
    def get_by_id(self, user_id: UUID):
        return self.db.query(User).filter(User.id == user_id).first()

    def create(self, user_fields):
        user = User(**user_fields)
        self.db.add(user)
        self.db.flush()  # Ensure PK is assigned
        return user

from sqlalchemy.orm import Session
from app.models.user import User
from app.models.user_auth import UserAuth
from app.schemas.user_schema import UserCreate, UserRead
from typing import List
from database.database import SessionLocal

class UserRepository:
    def __init__(self, db: Session = None):
        self.db = db or SessionLocal()

    def get_all(self) -> List[UserRead]:
        users = self.db.query(User).all()
        return [UserRead(id=user.id, name=user.name, email=user.email) for user in users]

    def create(self, user_create: UserCreate) -> User:
        user = User(
            name=user_create.name,
            firm_name=user_create.firm_name,
            contact_number=user_create.contact_number,
            address=user_create.address,
            city=user_create.city,
            gst_number=user_create.gst_number,
            role_id=user_create.role_id
        )
        self.db.add(user)
        self.db.flush()  # get user.id before commit
        return user

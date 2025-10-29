from app.repositories.user_repository import UserRepository
from app.schemas.user_schema import UserCreate, UserRead
from typing import List
from sqlalchemy.orm import Session

class UserService:
    def __init__(self, db: Session):
        self.repo = UserRepository(db)

    def get_users(self) -> List[UserRead]:
        return self.repo.get_all()

    def create_user(self, user: UserCreate):
        return self.repo.create(user)

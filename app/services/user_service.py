from app.repositories.user_repository import UserRepository
from sqlalchemy.orm import Session

class UserService:
    def __init__(self, db: Session):
        self.repo = UserRepository(db)

    def get_user(self, user_id):
        return self.repo.get_by_id(user_id)

    def create_user(self, **kwargs):
        return self.repo.create(**kwargs)

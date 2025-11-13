from app.repositories.user_repository import UserRepository
from sqlalchemy.orm import Session

class UserService:
    def __init__(self, db: Session):
        self.repo = UserRepository(db)

    def get_all_users(self):
        return self.repo.get_all_users()
    
    def get_user(self, user_id):
        return self.repo.get_by_id(user_id)

    def create_user(self, user_fields):
        return self.repo.create(user_fields)

from app.repositories.user_auth_repository import UserAuthRepository
from sqlalchemy.orm import Session

class UserAuthService:
    def __init__(self, db: Session):
        self.repo = UserAuthRepository(db)

    def get_user_auth(self, user_auth_id):
        return self.repo.get_by_id(user_auth_id)

    def create_user_auth(self, **kwargs):
        return self.repo.create(**kwargs)

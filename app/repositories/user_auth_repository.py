from db.models.user_auth import UserAuth
from sqlalchemy.orm import Session
from uuid import UUID

class UserAuthRepository:
    def __init__(self, db: Session):
        self.db = db

    def get_by_id(self, user_auth_id: UUID):
        return self.db.query(UserAuth).filter(UserAuth.id == user_auth_id).first()

    def get_by_user_name(self, user_name: str):
        return self.db.query(UserAuth).filter(UserAuth.user_name == user_name).first()

    def create(self, **kwargs):
        user_auth = UserAuth(**kwargs)
        self.db.add(user_auth)
        self.db.commit()
        self.db.refresh(user_auth)
        return user_auth

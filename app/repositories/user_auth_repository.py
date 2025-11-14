from db.models.user_auth import UserAuth
from sqlalchemy.orm import Session
from uuid import UUID

class UserAuthRepository:
    def __init__(self, db: Session):
        self.db = db

    def get_by_id(self, user_auth_id: UUID):
        return self.db.query(UserAuth).filter(UserAuth.id == user_auth_id).first()
    
    def get_by_user_id(self, user_id: UUID):
        return self.db.query(UserAuth).filter(UserAuth.user_id == user_id).first()

    def get_by_user_name(self, user_name: str):
        return self.db.query(UserAuth).filter(UserAuth.user_name == user_name).first()

    def create(self, **user_auth_data):
        user_auth = UserAuth(**user_auth_data)
        self.db.add(user_auth)
        self.db.flush()  # Ensure PK is assigned
        return user_auth
   
    def update(self, user_id, user_name):
        user_auth = self.get_by_user_id(user_id)
        if not user_auth:
            return None
        user_auth.user_name = user_name
        self.db.flush()
        
        return user_auth

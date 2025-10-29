from sqlalchemy.orm import Session
from app.models.user_auth import UserAuth
from app.schemas.user_auth_schema import UserAuthCreate, UserAuthRead

class UserAuthRepository:
    def __init__(self, db: Session):
        self.db = db

    def create(self, user_auth_create: UserAuthCreate) -> UserAuthRead:
        user_auth = UserAuth(
            user_id=user_auth_create.user_id,
            user_name=user_auth_create.user_name,
            password=user_auth_create.password
        )
        self.db.add(user_auth)
        self.db.commit()
        self.db.refresh(user_auth)
        return UserAuthRead(id=user_auth.id, user_id=user_auth.user_id, user_name=user_auth.user_name)

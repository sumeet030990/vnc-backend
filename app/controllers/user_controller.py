from fastapi import Depends
from app.services.user_service import UserService
from app.services.user_auth_service import UserAuthService
from app.schemas.user_schema import UserCreate, UserRead
from typing import List
from sqlalchemy.orm import Session
from database.database import get_db
from app.schemas.user_schema import UserRead
from app.schemas.user_auth_schema import UserAuthCreate

class UserController:
    def __init__(self, db: Session = Depends(get_db)):
        self.service = UserService(db)
        self.auth_service = UserAuthService(db)

    def get_users(self) -> List[UserRead]:
        return self.service.get_users()

    def create_user(self, user: UserCreate) -> UserRead:
        try:
            # Create user
            user_obj = self.service.create_user(user)
            # Create user_auth
            user_auth_data = UserAuthCreate(
                user_id=user_obj.id,
                user_name=user.user_name,
                password=user.password
            )
            self.auth_service.create_user_auth(user_auth_data)
            # Optionally, return a combined response or just user
            return UserRead(
                id=user_obj.id,
                name=user_obj.name,
                firm_name=user_obj.firm_name,
                contact_number=user_obj.contact_number,
                address=user_obj.address,
                city=user_obj.city,
                gst_number=user_obj.gst_number,
                role_id=user_obj.role_id,
                user_name=user.user_name
            )
        except Exception as e:
            self.service.repo.db.rollback()
            raise e

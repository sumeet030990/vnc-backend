from db.models.user import User
from sqlalchemy.orm import Session
from uuid import UUID

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
    
    def update(self, user_id, user_fields):
        user = self.get_by_id(user_id)
        if not user:
            return None
        for key, value in user_fields.items():
            setattr(user, key, value)
        self.db.flush()
        return user

    def delete(self, user_id):
        try:
            user = self.get_by_id(user_id)
            if not user:
                raise Exception("User not found.")
            self.db.delete(user)
            self.db.commit()
            return user
        except Exception as e:
            self.db.rollback()
            raise e
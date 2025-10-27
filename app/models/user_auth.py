from sqlalchemy import Column, Integer, String, ForeignKey
from sqlalchemy.orm import relationship
from config.database import Base
from app.models.user import User

class UserAuth(Base):
    __tablename__ = "user_auth"

    id = Column(Integer, primary_key=True, index=True)
    user_id = Column(Integer, ForeignKey('users.id'), nullable=False, unique=True)
    user_name = Column(String(255), unique=True, nullable=False)
    password = Column(String(255),  nullable=False)

    user = relationship("User", back_populates="auth")

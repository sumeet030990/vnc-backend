from sqlalchemy import Column, String, ForeignKey
from sqlalchemy.dialects.postgresql import UUID
import uuid
from db.base import Base
from sqlalchemy.orm import relationship


class UserAuth(Base):
    __tablename__ = 'user_auth'
    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4, unique=True, nullable=False)
    user_id = Column(UUID(as_uuid=True), ForeignKey('users.id'), nullable=False)
    user_name = Column(String, nullable=False, unique=True)
    password = Column(String, nullable=False)
    user = relationship('User', back_populates='user_auth')

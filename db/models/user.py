from sqlalchemy import Column, String, ForeignKey
from sqlalchemy.dialects.postgresql import UUID
import uuid
from db.base import Base
from sqlalchemy.orm import relationship
from sqlalchemy import DateTime, func

class User(Base):
    __tablename__ = 'users'

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4, unique=True, nullable=False)
    name = Column(String, nullable=True)
    company_name = Column(String, nullable=True)
    contact_number = Column(String, nullable=True)
    address = Column(String, nullable=True)
    city = Column(String, nullable=True)
    gst_number = Column(String, nullable=True)
    role_id = Column(UUID(as_uuid=True), ForeignKey('roles.id'), nullable=False)

    created_at = Column(DateTime(timezone=True), server_default=func.now(), nullable=False)
    updated_at = Column(DateTime(timezone=True), server_default=func.now(), onupdate=func.now(), nullable=False)
  
    role = relationship('Role', back_populates='users')
    user_auth = relationship(
        'UserAuth',
        back_populates='user',
        uselist=False,
        cascade='all, delete-orphan'
    )

from sqlalchemy import Column, String
from sqlalchemy.dialects.postgresql import UUID
import uuid
from db.base import Base

class Item(Base):
    __tablename__ = 'items'
    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4, unique=True, nullable=False)
    name = Column(String, nullable=False)
    slug = Column(String, nullable=False, unique=True)

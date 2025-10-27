from sqlalchemy import Column, Integer, String
from database.database import Base
from sqlalchemy.orm import relationship

class Role(Base):
  __tablename__ = "roles"

  id = Column(Integer, primary_key=True)
  name = Column(String(20))
  slug = Column(String(20))
  
  user = relationship("User", back_populates="role", doc="Users associated with this role")
from sqlalchemy import Column, Integer, String
from database.database import Base
from sqlalchemy.orm import relationship

class Item(Base):
  __tablename__ = "items"

  id = Column(Integer, primary_key=True)
  name = Column(String(20))
  slug = Column(String(20))
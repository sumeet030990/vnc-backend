from sqlalchemy import Column, Integer, String
from sqlalchemy.orm import relationship
from config.database import Base

class User(Base):
    __tablename__ = "users"

    id = Column(Integer, primary_key=True, index=True)
    name = Column(String(255), nullable=False)
    email = Column(String(255), unique=True, index=True, nullable=False)

    # Establishes a one-to-one relationship with the UserAuth model, linking authentication details to the user.
    auth = relationship("UserAuth", uselist=False, back_populates="user")

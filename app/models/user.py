from sqlalchemy import Column, ForeignKey, Integer, String
from sqlalchemy.orm import relationship
from database.database import Base
from app.models.user_auth import UserAuth
from app.models.role import Role
class User(Base):
    """
    Represents a user associated with a firm, including firm details and authentication relationship.
    """
    __tablename__ = "users"

    id = Column(Integer, primary_key=True, index=True)
    name = Column(String(255), nullable=True, doc="Name of the user")
    firm_name = Column(String(255), nullable=True, doc="Name of the firm")
    contact_number = Column(String(255), nullable=True, doc="Contact number of the firm/user")
    address = Column(String(255), nullable=True, doc="Address of the firm")
    city = Column(String(255), nullable=True, doc="City where the firm is located")
    gst_number = Column(String(255), nullable=True, doc="GST number of the firm")
    role_id = Column(Integer,  ForeignKey('roles.id'), nullable=False, doc="Role identifier for the user")
    
    # Establishes a one-to-one relationship with the UserAuth model, linking authentication details to the user.
    auth = relationship("UserAuth", uselist=False, back_populates="user", doc="Authentication details for the user")
    role = relationship("Role", uselist=False, back_populates="user", doc="Role associated with the user")

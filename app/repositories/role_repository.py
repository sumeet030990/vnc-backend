from db.models.role import Role
from sqlalchemy.orm import Session
from uuid import UUID

class RoleRepository:
    def __init__(self, db: Session):
        self.db = db

    def get_by_id(self, role_id: UUID):
        return self.db.query(Role).filter(Role.id == role_id).first()

    def get_by_slug(self, slug: str):
        return self.db.query(Role).filter(Role.slug == slug).first()

    def create(self, name: str, slug: str):
        role = Role(name=name, slug=slug)
        self.db.add(role)
        self.db.commit()
        self.db.refresh(role)
        return role

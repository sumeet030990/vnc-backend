from app.repositories.role_repository import RoleRepository
from sqlalchemy.orm import Session

class RoleService:
    def __init__(self, db: Session):
        self.repo = RoleRepository(db)

    def get_all_roles(self):
        return self.repo.get_all_roles()
    
    def get_role(self, role_id):
        return self.repo.get_by_id(role_id)

    def create_role(self, name, slug):
        return self.repo.create(name, slug)

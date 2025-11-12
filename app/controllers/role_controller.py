from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from app.services.role_service import RoleService
from db.session import get_db
from app.schemas.role import RoleCreateRequest
from app.schemas.role import RoleResponse
from uuid import UUID

router = APIRouter()

@router.post("/roles", response_model=RoleResponse)
def create_role(role: RoleCreateRequest, db: Session = Depends(get_db)):
    service = RoleService(db)
    return service.create_role(role.name, role.slug)

@router.get("/roles/{role_id}", response_model=RoleResponse)
def get_role(role_id: UUID, db: Session = Depends(get_db)):
    service = RoleService(db)
    role = service.get_role(role_id)
    if not role:
        raise HTTPException(status_code=404, detail="Role not found")
    return role

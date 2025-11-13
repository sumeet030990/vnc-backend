from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from app.schemas.globalResponse import SuccessResponse
from app.services.role_service import RoleService
from db.session import get_db
from app.schemas.role import RoleCreateRequest
from app.schemas.role import RoleResponse
from uuid import UUID


class RoleController:
    def __init__(self, db: Session = None):
        self.db = db
        self.role_service = RoleService(db) if db else None

    def get_all_roles(self) -> SuccessResponse[list[RoleResponse]]:
        roles = self.role_service.get_all_roles()
        if not roles:
            raise HTTPException(
                status_code=404,
                detail="No roles found."
            )
        # Convert each role to RoleResponse
        role_responses = [RoleResponse.model_validate(role) for role in roles]
        return SuccessResponse(data=role_responses)
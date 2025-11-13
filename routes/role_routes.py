from fastapi import APIRouter
from app.controllers.role_controller import RoleController
from app.schemas.globalResponse import SuccessResponse, ErrorResponse
from app.schemas.role import RoleResponse
from uuid import UUID
from app.middlewares.auth import get_current_user
from db.session import get_db
from sqlalchemy.orm import Session
from fastapi import Depends

role_router = APIRouter(tags=["Roles"])


@role_router.get(
    "/roles",
    response_model=SuccessResponse[list[RoleResponse]],
    responses={404: {"model": ErrorResponse}}
)
def get_all_roles(
    db: Session = Depends(get_db),
    current_user = Depends(get_current_user)
) -> SuccessResponse[list[RoleResponse]]:
    role_controller = RoleController(db)
    return role_controller.get_all_roles()
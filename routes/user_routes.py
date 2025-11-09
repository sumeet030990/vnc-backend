from fastapi import APIRouter, Depends
from app.controllers.user_controller import UserController
from app.schemas.response.globalResponse import SuccessResponse, ErrorResponse
from app.schemas.response.user import  UserResponseBody
from app.schemas.request.user import UserCreateRequest
from sqlalchemy.orm import Session
from db.session import get_db
from uuid import UUID


user_router = APIRouter(tags=["Users"])

@user_router.post("/users", response_model=SuccessResponse[UserResponseBody], responses={422: {"model":ErrorResponse}, 500: {"model": ErrorResponse}})
def create_user_route(payload: UserCreateRequest, db: Session = Depends(get_db)) -> SuccessResponse[UserResponseBody]:
    user_controller = UserController(db)
    
    return user_controller.store_user(payload)

@user_router.get("/users/{user_id}", response_model=SuccessResponse[UserResponseBody], responses={404: {"model": ErrorResponse}})
def get_user_route(user_id: UUID, db: Session = Depends(get_db)) -> SuccessResponse[UserResponseBody]:
    user_controller = UserController(db)
    return user_controller.show_user(user_id)
  
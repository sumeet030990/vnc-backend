from fastapi import APIRouter, Depends
from app.controllers.user_controller import UserController
from app.schemas.globalResponse import SuccessResponse, ErrorResponse
from app.schemas.user import  UserWithUserAuthResponse, UserCreateRequest
from sqlalchemy.orm import Session
from db.session import get_db
from uuid import UUID
from fastapi import Depends
from app.middlewares.auth import get_current_user


user_router = APIRouter(tags=["Users"])

@user_router.get("/users", response_model=SuccessResponse[list[UserWithUserAuthResponse]], responses={422: {"model":ErrorResponse}, 500: {"model": ErrorResponse}})
def get_all_users(db: Session = Depends(get_db), current_user = Depends(get_current_user)) -> SuccessResponse[UserWithUserAuthResponse]:
    user_controller = UserController(db)
    
    return user_controller.get_all_users()

@user_router.post("/users", response_model=SuccessResponse[UserWithUserAuthResponse], responses={422: {"model":ErrorResponse}, 500: {"model": ErrorResponse}})
def create_user(payload: UserCreateRequest, db: Session = Depends(get_db), current_user = Depends(get_current_user)) -> SuccessResponse[UserWithUserAuthResponse]:
    user_controller = UserController(db)
    return user_controller.store_user(payload)


@user_router.get(
    "/users/{user_id}",
    response_model=SuccessResponse[UserWithUserAuthResponse],
    responses={404: {"model": ErrorResponse}}
)
def show_user(
    user_id: UUID,
    db: Session = Depends(get_db),
    current_user = Depends(get_current_user)
) -> SuccessResponse[UserWithUserAuthResponse]:
    user_controller = UserController(db)
    return user_controller.show_user(user_id, current_user)

@user_router.put(
    "/users/{user_id}",
    response_model=SuccessResponse[UserWithUserAuthResponse],
    responses={404: {"model": ErrorResponse}}
)
def update_user(
    user_id: UUID,
    payload: UserCreateRequest,
    db: Session = Depends(get_db),
    current_user = Depends(get_current_user)
) -> SuccessResponse[UserWithUserAuthResponse]:
    user_controller = UserController(db)
    return user_controller.update_user(user_id, payload,current_user)

@user_router.delete(
    "/users/{user_id}",
    response_model=SuccessResponse,
    responses={404: {"model": ErrorResponse}}
)
def delete_user(
    user_id: UUID,
    db: Session = Depends(get_db),
    current_user = Depends(get_current_user)
) -> SuccessResponse:
    user_controller = UserController(db)
    return user_controller.delete_user(user_id)
  
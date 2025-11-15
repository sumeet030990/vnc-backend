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

@user_router.get(
    "/users",
    response_model=SuccessResponse[list[UserWithUserAuthResponse]],
    responses={422: {"model":ErrorResponse}, 500: {"model": ErrorResponse}},
    summary="List All Users",
    description="Get a list of all users. Returns user details including role and authentication info."
)
def get_all_users(db: Session = Depends(get_db), current_user = Depends(get_current_user)) -> SuccessResponse[UserWithUserAuthResponse]:
    user_controller = UserController(db)
    
    return user_controller.get_all_users()

@user_router.post(
    "/users",
    response_model=SuccessResponse[UserWithUserAuthResponse],
    responses={422: {"model":ErrorResponse}, 500: {"model": ErrorResponse}},
    summary="Create User",
    description="Create a new user. Requires user details and role. Returns the created user with authentication info."
)
def create_user(payload: UserCreateRequest, db: Session = Depends(get_db), current_user = Depends(get_current_user)) -> SuccessResponse[UserWithUserAuthResponse]:
    user_controller = UserController(db)
    return user_controller.store_user(payload)


@user_router.get(
    "/users/{user_id}",
    response_model=SuccessResponse[UserWithUserAuthResponse],
    responses={404: {"model": ErrorResponse}},
    summary="Get User by ID",
    description="Get a user by their unique ID. Returns user details including role and authentication info."
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
    responses={404: {"model": ErrorResponse}},
    summary="Update User",
    description="Update an existing user by their unique ID. Allows updating user details and role. Returns the updated user."
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
    responses={404: {"model": ErrorResponse}},
    summary="Delete User",
    description="Delete a user by their unique ID. Returns a success message if the user is deleted."
)
def delete_user(
    user_id: UUID,
    db: Session = Depends(get_db),
    current_user = Depends(get_current_user)
) -> SuccessResponse:
    user_controller = UserController(db)
    return user_controller.delete_user(user_id)
  
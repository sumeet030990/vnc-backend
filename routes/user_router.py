from fastapi import APIRouter, Depends
from app.controllers.user_controller import UserController
from app.schemas.user_schema import UserCreate, UserRead
from typing import List

router = APIRouter(prefix="/users", tags=["users"])


@router.get("/", response_model=List[UserRead])
def get_users(controller: UserController = Depends()):
    return controller.get_users()

@router.post("/", response_model=UserRead)
def create_user(user: UserCreate, controller: UserController = Depends()):
    return controller.create_user(user)

from fastapi import APIRouter, Depends
from app.controllers.user_controller import create_user_controller, get_user_controller
from app.schemas.response.user import UserResponse
from app.schemas.request.user import UserCreateRequest
from sqlalchemy.orm import Session
from db.session import get_db
from uuid import UUID

user_router = APIRouter()

@user_router.post("/users", response_model=UserResponse)
def create_user_route(payload: dict, db: Session = Depends(get_db)):
    return create_user_controller(payload, db)

@user_router.get("/users/{user_id}", response_model=UserResponse)
def get_user_route(user_id: UUID, db: Session = Depends(get_db)):
    return get_user_controller(user_id, db)

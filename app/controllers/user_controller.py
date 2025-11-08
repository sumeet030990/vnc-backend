from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from app.services.user_service import UserService
from db.session import get_db
from app.schemas.request.user import UserCreateRequest
from app.schemas.response.user import UserResponse
from uuid import UUID

router = APIRouter()

@router.post("/users", response_model=UserResponse)
def create_user(user: UserCreateRequest, db: Session = Depends(get_db)):
    if not user.name and not user.company_name:
        raise HTTPException(status_code=400, detail="Either name or company_name must be provided.")
    service = UserService(db)
    return service.create_user(**user.dict())

@router.get("/users/{user_id}", response_model=UserResponse)
def get_user(user_id: UUID, db: Session = Depends(get_db)):
    service = UserService(db)
    user = service.get_user(user_id)
    if not user:
        raise HTTPException(status_code=404, detail="User not found")
    return user

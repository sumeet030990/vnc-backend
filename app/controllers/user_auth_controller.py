from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from app.services.user_auth_service import UserAuthService
from db.session import get_db
from app.schemas.user_auth import UserAuthResponse,UserAuthCreateRequest
from uuid import UUID

router = APIRouter()

@router.post("/user-auth", response_model=UserAuthResponse)
def create_user_auth(user_auth: UserAuthCreateRequest, db: Session = Depends(get_db)):
    service = UserAuthService(db)
    return service.create_user_auth(**user_auth.dict())

@router.get("/user-auth/{user_auth_id}", response_model=UserAuthResponse)
def get_user_auth(user_auth_id: UUID, db: Session = Depends(get_db)):
    service = UserAuthService(db)
    user_auth = service.get_user_auth(user_auth_id)
    if not user_auth:
        raise HTTPException(status_code=404, detail="UserAuth not found")
    return user_auth

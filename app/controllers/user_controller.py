from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from app.services.user_service import UserService
from app.services.user_auth_service import UserAuthService
from db.session import get_db
from app.schemas.request.user import UserCreateRequest
from app.schemas.response.user import UserResponse
from uuid import UUID


router = APIRouter()

# Controller function for creating a user and user_auth in a transaction
def create_user_controller(payload: dict, db: Session):
    required_fields = ["role_id"]
    for field in required_fields:
        if field not in payload:
            raise HTTPException(status_code=422, detail=f"Missing required field: {field}")
    if not payload.get("name") and not payload.get("company_name"):
        raise HTTPException(status_code=422, detail="Either name or company_name must be provided.")
    try:
        with db.begin():
            # Prepare user data (exclude user_name, password)
            user_fields = {key: value for key, value in payload.items() if key not in ["user_name", "password"]}
            # Support company_name as company_name
            if "company_name" in user_fields:
                user_fields["company_name"] = user_fields.pop("company_name")
            user_service = UserService(db)
            user = user_service.create_user(user_fields)

            if payload.get("user_name") and payload.get("password"):
              user_auth_service = UserAuthService(db)
              user_auth_service.create_user_auth(
                user_id=user.id,
                user_name=payload["user_name"],
                password=payload["password"]
              )

            return UserResponse.model_validate(user)
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"User creation failed: {str(e)}")

# Controller function for getting a user
def get_user_controller(user_id: UUID, db: Session):
    user_service = UserService(db)
    user = user_service.get_user(user_id)
    if not user:
        raise HTTPException(status_code=404, detail="User not found")
    return UserResponse.model_validate(user)

@router.post("/users", response_model=UserResponse)
def create_user(user: UserCreateRequest, db: Session = Depends(get_db)):
    return create_user_controller(user.dict(), db)

@router.get("/users/{user_id}", response_model=UserResponse)
def get_user(user_id: UUID, db: Session = Depends(get_db)):
    return get_user_controller(user_id, db)

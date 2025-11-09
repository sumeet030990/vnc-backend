from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session
from app.schemas.response.globalResponse import ErrorResponse, SuccessResponse
from app.schemas.response.user import UserResponseBody
from app.services.user_service import UserService
from app.services.user_auth_service import UserAuthService
from db.session import get_db
from app.schemas.request.user import UserCreateRequest
from uuid import UUID


router = APIRouter()
UserResponse = SuccessResponse[UserResponseBody]

# Controller function for creating a user and user_auth in a transaction
def store_user(payload: UserCreateRequest, db: Session):
    required_fields = ["role_id"]
    for field in required_fields:
        if field not in payload:
            return ErrorResponse(status=422, error=True, data=f"Missing required field: {field}")
    if not payload.get("name") and not payload.get("company_name"):
        return ErrorResponse(status=422, error=True, data="Either name or company_name must be provided.")
    try:
        with db.begin():
            user_fields = {key: value for key, value in payload.items() if key not in ["user_name", "password"]}
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

            return UserResponse(data=UserResponse.model_validate(user))
    except Exception as e:
        return ErrorResponse(status=500, error=True, data=f"User creation failed: {str(e)}")

# Controller function for getting a user
def show_user(user_id: UUID, db: Session):
    user_service = UserService(db)
    user = user_service.get_user(user_id)
    if not user:
        return ErrorResponse(status=404, error=True, data="User not found")
    return  UserResponse(data=UserResponse.model_validate(user))



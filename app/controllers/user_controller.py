from sqlalchemy.orm import Session
from fastapi import HTTPException
from app.schemas.response.globalResponse import ErrorResponse, SuccessResponse
from app.schemas.response.user import UserResponseBody
from app.services.auth_service import AuthService
from app.services.user_service import UserService
from app.services.user_auth_service import UserAuthService
from app.schemas.request.user import UserCreateRequest
from uuid import UUID

UserResponse = SuccessResponse[UserResponseBody]

# Controller class for user operations
class UserController:
    def __init__(self, db: Session = None):
        self.db = db
        self.user_service = UserService(db) if db else None
        self.user_auth_service = UserAuthService(db) if db else None
        self.auth_service = AuthService()

    def store_user(self, payload: UserCreateRequest):
        required_fields = ["role_id"]
        for field in required_fields:
            print(f"Checking field: {field}")
            if getattr(payload, field, None) is None:
                raise HTTPException(
                    status_code=422,
                    detail=ErrorResponse(status=422, data=f"Missing required field: {field}").model_dump()
                )
        if not getattr(payload, "name", None) and not getattr(payload, "company_name", None):
                raise HTTPException(
                    status_code=422,
                    detail=ErrorResponse(status=422, data="Either name or company_name must be provided.").model_dump()
                )
        try:
            with self.db.begin():
                payload_dict = payload.model_dump()
                user_fields = {key: value for key, value in payload_dict.items() if key not in ["user_name", "password"]}
                if "company_name" in user_fields:
                    user_fields["company_name"] = user_fields.pop("company_name")
                user = self.user_service.create_user(user_fields)

                if payload_dict.get("user_name") and payload_dict.get("password"):
                    hashed_password = self.auth_service.hash_password(payload_dict["password"])
                    self.user_auth_service.create_user_auth(
                        user_id=user.id,
                        user_name=payload_dict["user_name"],
                        password=hashed_password
                    )

                return UserResponse(data=UserResponseBody.model_validate(user))
        except Exception as e:
                raise HTTPException(
                    status_code=500,
                    detail=ErrorResponse(status=500, data=f"User creation failed: {str(e)}").model_dump()
                )

    def show_user(self, user_id: UUID):
        user = self.user_service.get_user(user_id)
        if not user:
            raise HTTPException(
                status_code=404,
                detail=ErrorResponse(status=404, data="User not found").model_dump()
            )
        return UserResponse(data=UserResponseBody.model_validate(user))



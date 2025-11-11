from fastapi import APIRouter, Depends
from app.schemas.response.auth import AccessToken
from app.schemas.response.globalResponse import ErrorResponse
from app.schemas.request.auth import LoginAuthRequest
from sqlalchemy.orm import Session
from db.session import get_db
from app.controllers.auth_controller import AuthController

auth_router = APIRouter(tags=["Auth"])

@auth_router.post("/login", response_model=AccessToken, responses={401: {"model": ErrorResponse}})
def login_route(payload: LoginAuthRequest, db: Session = Depends(get_db)):
  auth_controller = AuthController(db)
  return auth_controller.login_user(payload)
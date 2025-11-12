
from dotenv import load_dotenv
from pwdlib import PasswordHash
from sqlalchemy.orm import Session
from app.schemas.auth import LoginAuthRequest
from app.repositories.user_auth_repository import UserAuthRepository
from app.repositories.user_repository import UserRepository
from app.schemas.user import UserResponseBody
from app.schemas.auth import AccessToken, UserWithUserAuthResponse
import jwt
from datetime import datetime, timedelta, timezone
from typing import Optional
from fastapi import HTTPException
import os
from app.utils.helper_functions import convert_uuids

load_dotenv()  # Load environment variables from .env file
SECRET_KEY = os.getenv("SECRET_KEY")  # Replace with your actual secret key
ACCESS_TOKEN_EXPIRE_DAYS = int(os.getenv("ACCESS_TOKEN_EXPIRE_DAYS"))

ALGORITHM = "HS256"  # Replace with your desired algorithm

class AuthService:
	"""Service for authentication-related utilities."""

	def __init__(self, db: Session):
		self.password_hash = PasswordHash.recommended()
		self.user_auth_repo = UserAuthRepository(db)
		self.user_repo = UserRepository(db)

	def hash_password(self, password: str) -> str:
		"""Hash a password using Argon2."""
		return self.password_hash.hash(password)

	def verify_password(self, password: str, hashed_password: str) -> bool:
		"""Verify a password against a given Argon2 hash."""
		return self.password_hash.verify(password, hashed_password)

	def create_access_token(self, data: dict, expires_delta: Optional[timedelta] = None):
		to_encode = data.copy()
		if expires_delta:
			expire = datetime.now(timezone.utc) + expires_delta
		else:
			expire = datetime.now(timezone.utc) + timedelta(minutes=15)
		to_encode.update({"exp": expire})
		encoded_jwt = jwt.encode(to_encode, SECRET_KEY, algorithm=ALGORITHM)
		return encoded_jwt
  
	def login_user(self, payload: LoginAuthRequest) -> AccessToken:
		user_auth = self.user_auth_repo.get_by_user_name(payload.user_name)
		if not user_auth:
			raise HTTPException(status_code=401, detail="Invalid username or password")
		if not self.verify_password(payload.password, user_auth.password):
			raise HTTPException(status_code=401, detail="Invalid username or password")

		user = self.user_repo.get_by_id(user_auth.user_id)
		access_token_expires = timedelta(days=ACCESS_TOKEN_EXPIRE_DAYS)

		# Add role to user_data
		role = user.role  # SQLAlchemy relationship
		user_data = UserWithUserAuthResponse(
			**UserResponseBody.model_validate(user).model_dump(),
			user_name=user_auth.user_name,
			role=role
		).model_dump()
  
		user_data = convert_uuids(user_data)
		access_token = self.create_access_token(
			data={
				"user": user_data,
			},
			expires_delta=access_token_expires
		)

		# Return user info (and token if needed)
		return AccessToken.model_validate({"access_token": access_token, "user": user_data})

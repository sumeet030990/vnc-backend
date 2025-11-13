from typing import Annotated
from fastapi import Depends, HTTPException, status
from jwt import InvalidTokenError
import jwt
from fastapi.security import OAuth2PasswordBearer
import os
from dotenv import load_dotenv
from app.schemas.user import UserWithUserAuthResponse

load_dotenv()  # Load environment variables from .env file
SECRET_KEY = os.getenv("SECRET_KEY")
ALGORITHM = os.getenv("ALGORITHM")
oauth2_scheme = OAuth2PasswordBearer(tokenUrl="token")

async def get_current_user(token: Annotated[str, Depends(oauth2_scheme)])->UserWithUserAuthResponse:
	credentials_exception = HTTPException(
		status_code=status.HTTP_401_UNAUTHORIZED,
		detail="Could not validate credentials",
		headers={"WWW-Authenticate": "Bearer"}
	)
	try:
		payload = jwt.decode(token, SECRET_KEY, algorithms=[ALGORITHM])
		user = payload.get("user")

		if user is None:
			raise credentials_exception
	except InvalidTokenError:
		raise credentials_exception
	return UserWithUserAuthResponse.model_validate(user).model_dump()

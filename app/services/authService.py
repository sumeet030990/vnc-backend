from pwdlib import PasswordHash
from fastapi.security import OAuth2PasswordBearer

class AuthService:
  def __init__(self):
    self.password_hash = PasswordHash.recommended()
    self.oauth2_scheme = OAuth2PasswordBearer(tokenUrl="token")

  def get_password_hash(self, password):
      return self.password_hash.hash(password)

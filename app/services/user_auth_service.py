from app.repositories.user_auth_repository import UserAuthRepository
from app.schemas.user_auth_schema import UserAuthCreate, UserAuthRead
from app.services.authService import AuthService

class UserAuthService:
    def __init__(self, db):
        self.repo = UserAuthRepository(db)
        self.authService = AuthService()

    def create_user_auth(self, user_auth: UserAuthCreate) -> UserAuthRead:
        user_auth.password = self.authService.get_password_hash(user_auth.password)
        return self.repo.create(user_auth)

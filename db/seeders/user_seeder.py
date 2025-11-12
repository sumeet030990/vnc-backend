from db.models.user import User
from db.models.user_auth import UserAuth
from app.repositories.user_repository import UserRepository
from app.repositories.user_auth_repository import UserAuthRepository
from app.repositories.role_repository import RoleRepository
from sqlalchemy.orm import Session
from app.services.auth_service import AuthService

def seed_users_and_auth(session: Session):
    user_repo = UserRepository(session)
    user_auth_repo = UserAuthRepository(session)
    role_repo = RoleRepository(session)
    auth_service = AuthService(session)

    # Fetch admin role id
    admin_role = role_repo.get_by_slug("admin")
    if not admin_role:
        raise Exception("Admin role not found. Please ensure the 'admin' role exists in the roles table.")
    admin_role_id = admin_role.id

    # Example user data (customize as needed)
    users = [
        {
            "name": "Sumeet Jadhav",
            "company_name": None,
            "contact_number": "8408880505",
            "address": "Nagpur",
            "city": "Nagpur",
            "gst_number": None,
            "role_id": admin_role_id,
            "user_name": "sumeet",
            "password": "password"
        },
        {
            "name": "Deepak Ingle",
            "company_name": "VNC",
            "contact_number": "9423683996",
            "address": "Nagpur",
            "city": "Nagpur",
            "gst_number": "GST123456",
            "role_id": admin_role_id,
            "user_name": "deepak",
            "password": "password"
        },
    ]

    for user_fields in users:
      # Create user
      user_data = {key: value for key, value in user_fields.items() if key not in ["user_name", "password"]}
      user = user_repo.create(user_data)
      # Create user auth with provided user_name and password
      user_auth_repo.create(
          user_id=user.id,
          user_name=user_fields["user_name"],
          password=auth_service.hash_password(user_fields["password"])  # Replace with actual hashed password in production
      )
    session.commit()

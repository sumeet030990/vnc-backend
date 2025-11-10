
from pwdlib import PasswordHash

class AuthService:
	"""Service for authentication-related utilities."""

	def __init__(self):
		self.password_hash = PasswordHash.recommended()


	def hash_password(self, password: str) -> str:
		"""Hash a password using Argon2."""
		return self.password_hash.hash(password)

	def verify_password(self, password: str, hashed_password: str) -> bool:
		"""Verify a password against a given Argon2 hash."""
		return self.password_hash.verify(password, hashed_password)
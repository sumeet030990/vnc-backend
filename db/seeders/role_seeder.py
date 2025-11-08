from sqlalchemy.orm import Session
from db.models.role import Role
import uuid

def seed_roles(session: Session):
    roles = [
        {"name": "Admin", "slug": "admin"},
        {"name": "Buyers", "slug": "buyers"},
        {"name": "Sellers", "slug": "sellers"},
        {"name": "Transporter", "slug": "transporter"},
    ]
    for role in roles:
        exists = session.query(Role).filter_by(slug=role["slug"]).first()
        if not exists:
            session.add(Role(id=uuid.uuid4(), name=role["name"], slug=role["slug"]))
    session.commit()

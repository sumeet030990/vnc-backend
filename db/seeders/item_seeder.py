from sqlalchemy.orm import Session
from db.models.item import Item
import uuid

def seed_items(session: Session):
    items = [
        {"name": "Toor Dall", "slug": "toor_dall"},
    ]
    for item in items:
        exists = session.query(Item).filter_by(slug=item["slug"]).first()
        if not exists:
            session.add(Item(id=uuid.uuid4(), name=item["name"], slug=item["slug"]))
    session.commit()

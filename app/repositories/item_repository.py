from db.models.item import Item
from sqlalchemy.orm import Session
from uuid import UUID

class ItemRepository:
    def __init__(self, db: Session):
        self.db = db

    def get_by_id(self, item_id: UUID):
        return self.db.query(Item).filter(Item.id == item_id).first()

    def get_by_slug(self, slug: str):
        return self.db.query(Item).filter(Item.slug == slug).first()

    def create(self, name: str, slug: str):
        item = Item(name=name, slug=slug)
        self.db.add(item)
        self.db.commit()
        self.db.refresh(item)
        return item

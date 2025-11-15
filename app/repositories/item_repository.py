from db.models.item import Item
from sqlalchemy.orm import Session
from uuid import UUID

class ItemRepository:
    def __init__(self, db: Session):
        self.db = db

    def get_all_items(self)->list[Item]:
        return self.db.query(Item).all()
    
    def get_item_by_id(self, item_id: UUID)->Item:
        return self.db.query(Item).filter(Item.id == item_id).first()

    def get_by_slug(self, slug: str)->Item:
        return self.db.query(Item).filter(Item.slug == slug).first()

    def create(self, payload)->Item:
        item = Item(name=payload.name, slug=payload.slug)
        self.db.add(item)
        self.db.commit()
        self.db.refresh(item)
        return item
  
    def update(self, item, payload)->Item:
        item.name = payload.name
        item.slug = payload.slug
        self.db.commit()
        self.db.refresh(item)
        return item
  
    def delete(self, item)->bool:
        try:
            self.db.commit()
            self.db.delete(item)
            return True
        except:
            return False

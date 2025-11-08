from app.repositories.item_repository import ItemRepository
from sqlalchemy.orm import Session

class ItemService:
    def __init__(self, db: Session):
        self.repo = ItemRepository(db)

    def get_item(self, item_id):
        return self.repo.get_by_id(item_id)

    def create_item(self, name, slug):
        return self.repo.create(name, slug)

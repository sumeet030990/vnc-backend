from app.repositories.item_repository import ItemRepository
from sqlalchemy.orm import Session

class ItemService:
    def __init__(self, db: Session):
        self.repo = ItemRepository(db)

    def get_all_items(self):
        return self.repo.get_all_items()
    
    def get_item_by_id(self, item_id):
        return self.repo.get_item_by_id(item_id)

    def create_item(self, payload):
        return self.repo.create(payload)
  
    def update_item(self, item_id, payload):
        item = self.repo.get_item_by_id(item_id)
        if not item:
            raise Exception("Item not found")
        item_updated = self.repo.update(item, payload)
        
        return item_updated
   
    def delete_item(self, item_id):
        item = self.repo.get_item_by_id(item_id)
        if not item:
            raise Exception("Item not found")
        result = self.repo.delete(item)
        return result
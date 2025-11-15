from fastapi import HTTPException
from sqlalchemy.orm import Session
from app.schemas.globalResponse import ErrorResponse, SuccessResponse
from app.services.item_service import ItemService
from db.session import get_db
from app.schemas.item import ItemUpsertRequest
from app.schemas.item import ItemResponse
from uuid import UUID

class ItemController:
    def __init__(self, db: Session = None):
        self.db = db
        self.item_service = ItemService(db) if db else None

    def get_all_items(self) -> SuccessResponse[list[ItemResponse]]:
        items = self.item_service.get_all_items()
        if not items:
            raise HTTPException(
                status_code=404,
                detail="No items found."
            )
        # Convert each item to ItemResponse
        item_responses = [ItemResponse.model_validate(item) for item in items]
        return SuccessResponse(data=item_responses)
    
    def get_item_by_id(self, item_id: UUID) -> SuccessResponse[ItemResponse]:
        item = self.item_service.get_item_by_id(item_id)
        if not item:
            raise HTTPException(
                status_code=404,
                detail="Item not found."
            )
        return SuccessResponse(data=item)

    def create_item(self, payload: ItemUpsertRequest) -> SuccessResponse[ItemResponse]:
        item = self.item_service.create_item(payload)
        return SuccessResponse(data=ItemResponse.model_validate(item))
   
    def update_item(self, item_id: UUID, payload: ItemUpsertRequest) -> SuccessResponse[ItemResponse]:
        try:
            item = self.item_service.update_item(item_id, payload)
            return SuccessResponse(data=ItemResponse.model_validate(item))
        except:
            raise HTTPException(
                status_code=500,
                detail="Failed to update item."
            )

   
    def delete_item(self, item_id: UUID) -> SuccessResponse[str]:
        try:
            item = self.item_service.delete_item(item_id)
            return SuccessResponse(data="Item deleted successfully")
        except  Exception as e:
            raise ErrorResponse(
                data=e
            )
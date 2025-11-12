from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from app.services.item_service import ItemService
from db.session import get_db
from app.schemas.item import ItemCreateRequest
from app.schemas.item import ItemResponse
from uuid import UUID

router = APIRouter()

@router.post("/items", response_model=ItemResponse)
def create_item(item: ItemCreateRequest, db: Session = Depends(get_db)):
    service = ItemService(db)
    return service.create_item(item.name, item.slug)

@router.get("/items/{item_id}", response_model=ItemResponse)
def get_item(item_id: UUID, db: Session = Depends(get_db)):
    service = ItemService(db)
    item = service.get_item(item_id)
    if not item:
        raise HTTPException(status_code=404, detail="Item not found")
    return item

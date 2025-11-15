from fastapi import APIRouter, Depends
from app.controllers.item_controller import ItemController
from app.schemas.globalResponse import SuccessResponse, ErrorResponse
from app.schemas.item import ItemResponse, ItemUpsertRequest
from sqlalchemy.orm import Session
from db.session import get_db
from uuid import UUID
from app.middlewares.auth import get_current_user

item_router = APIRouter(tags=["Items"])


@item_router.get('/items', response_model=SuccessResponse[list[ItemResponse]], summary="List All Items", description="Get a list of all items.")
def get_all_items(db: Session = Depends(get_db),
                  current_user = Depends(get_current_user)) -> SuccessResponse[list[ItemResponse]]:
    item_controller = ItemController(db)
    return item_controller.get_all_items()

@item_router.get('/items/{item_id}', response_model=SuccessResponse[ItemResponse], summary="Get Item by ID", description="Get a single item by its ID.")
def get_item_by_id(item_id: UUID, db: Session = Depends(get_db),
                   current_user = Depends(get_current_user)) -> SuccessResponse[ItemResponse]:
    item_controller = ItemController(db)
    return item_controller.get_item_by_id(item_id)
  
@item_router.post(
    "/items",
    response_model=SuccessResponse[ItemResponse],
    summary="Create Item",
    description="Create a new item. Requires item details. Returns the created item.",
    responses={422: {"model": ErrorResponse}, 500: {"model": ErrorResponse}}
)
def create_item(payload: ItemUpsertRequest, db: Session = Depends(get_db),
                current_user = Depends(get_current_user)) -> SuccessResponse[ItemResponse]:
    item_controller = ItemController(db)
    return item_controller.create_item(payload)

@item_router.put(
    "/items/{item_id}",
    response_model=SuccessResponse[ItemResponse],
    summary="Update Item",
    description="Update an existing item by its ID. Allows updating item details. Returns the updated item.",
    responses={404: {"model": ErrorResponse}, 422: {"model": ErrorResponse}, 500: {"model": ErrorResponse}}
)
def update_item(item_id: UUID, payload: ItemUpsertRequest, db: Session = Depends(get_db),
                current_user = Depends(get_current_user)) -> SuccessResponse[ItemResponse]:
    item_controller = ItemController(db)
    return item_controller.update_item(item_id, payload)


@item_router.delete(
    "/items/{item_id}",
    response_model=SuccessResponse[ItemResponse],
    summary="Delete Item",
    description="Delete an existing item by its ID. Returns the deleted item.",
    responses={404: {"model": ErrorResponse}, 422: {"model": ErrorResponse}, 500: {"model": ErrorResponse}}
)
def delete_item(item_id: UUID, db: Session = Depends(get_db),
                current_user = Depends(get_current_user)) -> SuccessResponse[str]:
    item_controller = ItemController(db)
    return item_controller.delete_item(item_id)
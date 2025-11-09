from typing import Generic, TypeVar 
from pydantic import BaseModel

T = TypeVar("T")

class SuccessResponse(BaseModel, Generic[T]):
    status: int = 200
    error: bool = False
    data: T

class ErrorResponse(BaseModel, Generic[T]):
    status: int = 500
    error: bool = True
    data: T

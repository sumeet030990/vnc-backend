
from fastapi import FastAPI
from routes.role_routes import role_router
from routes.item_routes import item_router
from routes.user_routes import user_router
from routes.user_auth_routes import user_auth_router

app = FastAPI()

app.include_router(role_router, prefix="/api")
app.include_router(item_router, prefix="/api")
app.include_router(user_router, prefix="/api")
app.include_router(user_auth_router, prefix="/api")

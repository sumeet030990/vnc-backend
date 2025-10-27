from fastapi import FastAPI
from routes import user_router

app = FastAPI()

app.include_router(user_router.router)

@app.get("/")
def read_root():
    return {"message": "Welcome to the FastAPI MVC Project!"}

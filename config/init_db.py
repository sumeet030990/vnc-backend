from config.database import engine, Base
from app.models import user  # Import all models here

if __name__ == "__main__":
    print("************* Creating all tables *************")
    Base.metadata.create_all(bind=engine)
    print("************* Done *************")

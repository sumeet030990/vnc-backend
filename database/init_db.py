from .database import engine, Base

# Import all models here
from app.models import role  
from app.models import user
from app.models import user_auth
from app.models import item

if __name__ == "__main__":
    print("************* Creating all tables *************")
    Base.metadata.create_all(bind=engine)
    print("************* Done *************")

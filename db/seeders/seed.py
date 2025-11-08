import os
from dotenv import load_dotenv
from db.session import SessionLocal
from db.seeders.role_seeder import seed_roles
from db.seeders.item_seeder import seed_items
# Add more imports as you create more seeders

load_dotenv()
session = SessionLocal()

def run_all_seeders():
    seed_roles(session)
    seed_items(session)
    # Call more seeders here as needed
    print("All seeders ran successfully.")

if __name__ == "__main__":
    run_all_seeders()

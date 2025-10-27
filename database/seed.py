from database.database import SessionLocal
from app.models.item import Item
from app.models.role import Role
from app.models.user import User
from app.models.user_auth import UserAuth

def seed():
    session = SessionLocal()
    try:
        # Seed Roles
        admin_role = Role(name="Admin", slug="admin")
        seller_role = Role(name="Seller", slug="seller")
        buyer_role = Role(name="Buyer", slug="buyer")
        transporter_role = Role(name="Transporter", slug="transporter")
        session.add_all([admin_role, seller_role, buyer_role, transporter_role])
        session.commit()  # Commit to assign IDs

        # Seed Items
        item1 = Item(name="Toor Dall", slug="toor-dall")
        session.add_all([item1])
        session.commit()

        # Seed Users (use role_id instead of role object)
        user1 = User(name="Sumeet", city="Nagpur", role_id=admin_role.id)
        user2 = User(name="Deepak", city="Nagpur", role_id=admin_role.id)
        user3 = User(name="Manohar Bhojwanu", firm_name="Dayalu Dall Mill", city="Nagpur", role_id=seller_role.id)
        user4 = User(name="Buyers", city="Vishakapatnam", role_id=buyer_role.id)
        session.add_all([user1, user2, user3, user4])
        session.commit()

        # Seed UserAuth
        auth1 = UserAuth(user_name=user1.name, user_id=user1.id, password="password")
        auth2 = UserAuth(user_name=user2.name, user_id=user2.id, password="password")
        session.add_all([auth1, auth2])
        session.commit()

        print("Database seeded successfully.")
    except Exception as e:
        session.rollback()
        print(f"Seeding failed: {e}")
    finally:
        session.close()

if __name__ == "__main__":
    seed()
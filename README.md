# FastAPI MVC Project

This project is a scalable FastAPI application following the MVC architecture with service and repository layers. It is structured for industry standards and future growth.

## Project Structure

```
be-vnc/
├── app/
│   ├── config/
│   ├── models/
│   ├── repositories/
│   ├── schemas/
│   ├── services/
│   └── routes/
├── tests/
├── main.py
├── requirements.txt
└── README.md
```

## Getting Started

1. **Install dependencies:**
   ```sh
   pip install -r requirements.txt
   ```
2. **Run the application:**
   ```sh
   uvicorn main:app --reload
   ```
3. **API Docs:**
   Visit [http://127.0.0.1:8000/docs](http://127.0.0.1:8000/docs)

## Testing

Run tests with:
```sh
pytest
```

## Database Initialization

To create the MySQL tables, run:
```sh
python3 -m app.config.init_db
```

## Notes
- The project uses in-memory storage for demonstration. Replace with a database for production.
- Update config and add more models, services, and repositories as needed.

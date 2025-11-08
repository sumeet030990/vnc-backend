# FastAPI Project

Project structure scaffolded.

## Project Folder Structure

```
be/
├── routes/                     # API route definitions (modularized by feature)
├── db/                         # Database models, session, migrations
│   └── alembic/                # DB migrations (inside db)
├── app/
│   ├── controllers/            # Controller layer (handles request/response logic)
│   ├── services/               # Business logic/services layer
│   ├── repositories/           # Data access layer (DB queries)
│   ├── core/                   # Core settings, config, security, main app
│   │   ├── exception_handlers.py   # Global error handling
│   │   ├── middleware.py           # Request/response normalization middleware
│   │   ├── logger.py               # Logger setup
│   │   └── ... (settings, config, etc.)
│   ├── schemas/
│   │   ├── request/            # Request DTOs
│   │   └── response/           # Response DTOs
│   ├── dependencies/           # Dependency injection modules
│   ├── utils/                  # Utility/helper functions (e.g., date/time, string, etc.)
│   │   ├── datetime_utils.py
│   │   └── ... (other helpers)
│   ├── constants/              # Application-wide constants
│   │   └── app_constants.py
│   └── main.py                 # FastAPI entrypoint
├── tests/                      # Unit/integration tests
├── requirements.txt            # Python dependencies
├── .env                        # Environment variables
├── .env.example                # Example env file
├── .gitignore
├── README.md
├── pyproject.toml              # Linting, formatting, and tool configs
```

### Folder/Module Details
- **routes/**: Contains all API route definitions, organized by feature or domain.
- **db/**: Database models, session management, and migrations. `alembic/` for migration scripts.
- **app/controllers/**: Handles HTTP request/response logic, calls services.
- **app/services/**: Business logic, orchestrates repositories and other services.
- **app/repositories/**: Data access layer, handles all DB queries and persistence.
- **app/core/**: Core app settings, configuration, security, exception handling, middleware, and logging.
- **app/schemas/request/**: Pydantic models for request validation (Request DTOs).
- **app/schemas/response/**: Pydantic models for response serialization (Response DTOs).
- **app/dependencies/**: Dependency injection modules for reusable dependencies.
- **app/utils/**: Utility/helper functions (e.g., date/time, string manipulation).
- **app/constants/**: Application-wide constants.
- **app/main.py**: FastAPI application entrypoint.
- **tests/**: Unit and integration tests.
- **requirements.txt**: Python dependencies.
- **pyproject.toml**: Linting, formatting, and tool configurations.
- **.env / .env.example**: Environment variable files.
- **README.md**: Project documentation.

# Convert UUIDs to strings for JSON serialization
		# Recursively convert UUIDs to strings in user_data (including nested role)
import uuid


def convert_uuids(obj):
  if isinstance(obj, dict):
    return {k: convert_uuids(v) for k, v in obj.items()}
  elif isinstance(obj, list):
    return [convert_uuids(i) for i in obj]
  elif isinstance(obj, uuid.UUID):
    return str(obj)
  else:
    return obj
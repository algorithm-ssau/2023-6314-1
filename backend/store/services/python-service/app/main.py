from fastapi import FastAPI

class Post:
    
    def __init__(self, title, description):
        self.title = title
        self.description = description

app = FastAPI()

@app.get("/")
def index():
    return {"title": "Hello"}

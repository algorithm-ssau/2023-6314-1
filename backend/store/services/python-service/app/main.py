from fastapi import FastAPI

class Post:
    
    def __init__(self, title, description):
        self.title = title
        self.description = description

app = FastAPI()

posts = [
    Post('Открытие', 'Наш магазин открылся!'),
    Post('Скидки', 'Вскоре добавится функционал со скидками!'),
    Post('Пригласи друга', 'Пригласи друга и получи мерч в подарок!')
]

@app.get("/")
def index():
    return posts

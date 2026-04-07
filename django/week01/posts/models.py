from django.db import models

# Create your models here.

class Post(models.Model):
    image=models.ImageField(verbose_name="이미지")
    content=models.TextField(verbose_name="내용")
    created_at=models.DateTimeField(verbose_name="작성일", auto_now_add=True)
    view_count=models.PositiveIntegerField(verbose_name="조회수", default=0)
    
class Comment(models.Model):
    post=models.ForeignKey(Post, on_delete=models.CASCADE) # 게시글이 삭제되면 댓글도 같이 삭제됨
    content=models.TextField(verbose_name="내용")
    created_at=models.DateTimeField(verbose_name="작성일", auto_now_add=True)
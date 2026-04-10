from django.db import models
from posts.models import Post

# Create your models here.

class Like(models.Model):
    post=models.ForeignKey(Post, on_delete=models.CASCADE) # 게시글이 삭제되면 좋아요도 같이 삭제됨
    user=models.CharField(verbose_name="사용자", max_length=20)
    created_at=models.DateTimeField(verbose_name="좋아요 누른 시간", auto_now_add=True)
    
    def __str__(self):
        return f"{self.user}님이 {self.post.id}번 게시글에 좋아요를 눌렀습니다."
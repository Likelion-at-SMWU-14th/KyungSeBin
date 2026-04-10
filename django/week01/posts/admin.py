from django.contrib import admin
from .models import Post, Comment
from likes.models import Like

# Register your models here.
# admin.site.register(Post)

class CommentInline(admin.TabularInline):
    model=Comment
    extra=5
    min_num=1
    max_num=5
    verbose_name="댓글"
    verbose_name_plural="댓글들"
    
class LikeInline(admin.TabularInline):
    model=Like
    extra=0
    
@admin.register(Post)
class PostModelAdmin(admin.ModelAdmin):
    list_display=['id', 'content', 'created_at', 'view_count']
    list_editable=['content', 'view_count']
    list_filter=['created_at']
    search_fields=['id']
    search_help_text='게시글 번호나 내용으로 검색이 가능합니다.'
    inlines=[CommentInline, LikeInline]
    actions=['report'] # admin 페이지에서 선택한 게시글을 신고하는 액션
    
    def report(self, request, queryset): #queryset: 선택한 게시글들
        for item in queryset:
            item.content='운영규칙 위반으로 인한 게시글 삭제 처리'
            item.save()
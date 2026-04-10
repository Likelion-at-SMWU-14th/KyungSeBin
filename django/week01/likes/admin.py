from django.contrib import admin
from .models import Like
# Register your models here.

@admin.register(Like)
class LikeModelAdmin(admin.ModelAdmin):
    list_display=['id', 'post', 'user', 'created_at']
    list_filter=['created_at']
    search_fields=['user']
    search_help_text='사용자 이름으로 검색이 가능합니다.'
    list_display_links=['id', 'post']
    ordering=['-created_at']
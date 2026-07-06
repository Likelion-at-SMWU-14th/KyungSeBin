#권한 설정

from rest_framework import permissions

class IsOwnerOrReadOnly(permissions.BasePermission):
    def has_object_permission(self, request, view, obj):
        if request.method in permissions.SAFE_METHODS: #GET,HEAD,OPTIONS : 항상 허용
            return True
        return obj.writer == request.user #수정 삭제 -> 작성자만 허용
    
    
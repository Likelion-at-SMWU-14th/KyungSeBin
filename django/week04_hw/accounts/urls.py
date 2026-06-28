from django.urls import path
from . import views

urlpatterns=[
    path('users/',views.signup), #POST : 회원가입
    path('users/<int:user_id>/',views.get_user), #GET : 회원정보 조회
    path('users/<int:user_id>/',views.update_user), #PUT : 회원정보 수정
    path('users/<int:user_id>/',views.delete_user), #DELETE : 탈퇴
]
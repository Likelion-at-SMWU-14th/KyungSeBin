from django.urls import path, include
from rest_framework import routers
from rest_framework.authtoken.views import obtain_auth_token

from .views import PostListView, PostRetrieveView, PostModelViewSet

app_name = 'posts'

router_post = routers.DefaultRouter()
router_post.register('', PostModelViewSet)

urlpatterns = [
    path('login/', obtain_auth_token),

    # path('', PostListView.as_view()),
    # path('<int:pk>/', PostRetrieveView.as_view()),

    path('', include(router_post.urls)),
]
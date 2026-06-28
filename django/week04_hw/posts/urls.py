from django.urls import path,include
from rest_framework import routers

from .views import PostModelViewSet

app_name='posts'

router=routers.DefaultRouter()
router.register('', PostModelViewSet, basename='post')

urlpatterns=[
    path('', include(router.urls)),
]
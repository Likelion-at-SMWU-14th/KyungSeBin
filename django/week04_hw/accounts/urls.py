from django.urls import path
from . import views

urlpatterns=[
    path('users/',views.signup), #POST,GET
    path('users/<int:user_id>/',views.user_detail), #GET,PUT,DELETE
]
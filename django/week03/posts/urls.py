from django.urls import path

from .views import post_list_view, post_form_view,post_model_form_view

app_name='posts'

urlpatterns = [
    path('', post_list_view, name='post-list'),
    path('form/', post_form_view, name="post-form"),
    path('modelform/',post_model_form_view, name="post-model-form"),
]
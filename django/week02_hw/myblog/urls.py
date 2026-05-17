"""
URL configuration for myblog project.

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/5.2/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path
from posts.views import url_view,url_parameter_view,function_view,class_view,class_view2,home_view
from blog import views

urlpatterns = [
    path('admin/', admin.site.urls),
    path('url/',url_view),
    path('url/<str:username>/',url_parameter_view),
    path('fbv/',function_view),
    path('cbv/',class_view.as_view()),
    path('cbv2/',class_view2.as_view()),
    # path('', home_view), #'' : default url -> 화면 들어가자마자 우리가 만든 화면이 뜸 ( 기존 : 404 에러가 남 )
    path('about/',views.introduction, name='introduction'),
    path('diary/',views.DiaryListView.as_view(), name='diary-list'),
    path('diary/create/', views.DiaryCreateView.as_view(),name='diary-create'),
]

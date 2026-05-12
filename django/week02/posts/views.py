from django.shortcuts import render
from django.http import HttpResponse,JsonResponse
from django.views import View
from django.views.generic import ListView
from .models import Post

# Create your views here.

def url_view(request):
    data={'code':200, 'message':'url_view'}
    return JsonResponse(data)

def url_parameter_view(request,username):
    age=request.GET.get('age', None)
    print(username)
    print(request.GET)
    return HttpResponse(f"사용자 이름은 {username}입니다. 나이는 {age}세 입니다")

def function_view(request):
    print(f'request.method: {request.method}')
    print(f'request.GET: {request.GET}')
    print(f'request.POST: {request.POST}')
    
    context={
        "view_type": "Function Based View",
    }

    return render(request,'view.html', context)

class class_view(View):

    context = {
            "view_type": "Class Based View",
        }

    def get(self, request):
        print(f'request.method: {request.method}')
        print(f'request.GET: {request.GET}')
        return render(request,"view.html" ,self.context)

    def post(self, request):
        print(f'request.method: {request.method}')
        print(f'request.POST: {request.POST}')
        return render(request,"view.html", self.context)
    
class class_view2(ListView):
    model=Post
    template_name='cbv_view.html'
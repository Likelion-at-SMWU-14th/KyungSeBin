from django.shortcuts import render
from django.http import HttpResponse,JsonResponse

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

    return render(request,'view.html')
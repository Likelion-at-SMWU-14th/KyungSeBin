from django.shortcuts import render,redirect
from django.http import HttpResponse,JsonResponse
from django.views import View
from django.views.generic import ListView
from .models import Post
from .forms import PostBaseForm

# Create your views here.

def url_view(request):
    data={'code':200, 'message':'url_view'}
    return JsonResponse(data)

def home_view(request):
    context={
        "name":"lion"
    }
    return render(request, "home.html",context)

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

def post_form_view(request):
    if request.method=="GET":
        form=PostBaseForm()
        context={'form': form}
        return render(request,'post_form.html', context)
    else:
        form=PostBaseForm(request.POST, request.FILES)
        if form.is_valid():
            Post.objects.create(
                image=form.cleaned_data['image'],
                content=form.cleaned_data['content']
            )
        else:
            print(form.errors)
            return render(request,'post_form.html', {'form': form})
        return redirect('posts:post-list')
    
def post_list_view(request):
    posts= Post.objects.all()
    context={'posts': posts}
    return render(request,'post_list.html', context)
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
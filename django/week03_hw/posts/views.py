from django.shortcuts import render,redirect, get_object_or_404
from django.http import HttpResponse,JsonResponse
from django.views import View
from django.views.generic import ListView
from .models import Post, Comment
from .forms import PostBaseForm,PostModelForm,CommentForm

# Create your views here.s

def url_view(request):
    data={'code':200, 'message':'url_view'}
    return JsonResponse(data)

def home_view(request):
    return render(request, "home.html")

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

def post_model_form_view(request):
    if request.method=="GET":
        form=PostModelForm()
        context={'form': form}
        return render(request,'post_model_form.html',context)
    else:
        form=PostModelForm(request.POST, request.FILES)
        if form.is_valid():
            form.save()
        else:
            print(form.errors)
            return render(request,'post_model_form.html', {'form': form})
        return redirect('posts:post-list')
    
def post_detail_view(request,id):
    post=Post.objects.get(id=id)
    comment_form=CommentForm()
    context={'post': post, 'comment_form': comment_form}
    return render(request,'post_detail.html', context)

def post_update_view(request, id):
    post = Post.objects.get(id=id)    
    if request.method == "GET":
        form = PostModelForm(instance=post)
        context = {'form' : form, 'post': post}
        return render(request, 'post_update.html', context)
    else:
        form = PostModelForm(request.POST, request.FILES, instance=post)
        if form.is_valid():
            form.save()
        else:
            print(form.errors)
            return render(request, 'post_update.html', {'form' : form})
        return redirect('posts:post-detail', id=id)
    
def post_delete_view(request, id):
    post = get_object_or_404(Post, id=id)
    if request.method == "POST":
        post.delete()
        return redirect('posts:post-list')
    context = {'post' : post}
    return render(request, 'post_delete_confirm.html', context)

def comment_create_view(request, post_id):
    post=get_object_or_404(Post, id=post_id)
    if request.method=="POST":
        form=CommentForm(request.POST)
        if form.is_valid():
            comment=form.save(commit=False)
            comment.post=post
            comment.save()
        return redirect('posts:post-detail', id=post_id)
    return redirect('posts:post-detail', id=post_id)

def comment_update_view(request, post_id, comment_id):
    comment=get_object_or_404(Comment, id=comment_id)
    if request.method=="POST":
        form=CommentForm(request.POST, instance=comment)
        if form.is_valid():
            form.save()
        return redirect('posts:post-detail', id=post_id)
    else:
        form=CommentForm(instance=comment)
    return render(request,'comment_update.html', {'form': form, 'comment': comment})

    
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
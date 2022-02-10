# Git操作

## 将本地代码与远程仓库绑定

> 适用场景：有了本地代码，想要将本地代码放到远程仓库保存，同时能够保证自己更改本地代码后可以更新到远程仓库，此时可以将代码放到GitHub仓库，具体步骤如下。

1. 初始化本地仓库 

   ```bash
   git init
   ```

   

2. 建立对应的远程仓库

   ![image-20220110195403104](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/image-20220110195403104.png)

3. 关联本地仓库与远程仓库

   ```bash
   # url看下图
   git remote add origin <远程仓库url>
   ```

   ![image-20220110200941538](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/image-20220110200941538.png)

4. 如果远程仓库与本地仓库文件不一致（含有初始化文件或其他提交分支导致）时，需要先拉取当前分支

   ```bash
   # origin 是远程仓库的意思 
   # main 是远程仓库的默认分支（看你自己的，上图也可以看到）
   git pull origin main
   ```

   

5. 将代码添加到本地仓库

   ```bash
   git add .
   ```

   

6. 将代码提交到本地仓库，并可以添加提交备注

   ```bash
   git commit -m "提交备注"
   ```

   

7. 将本地代码推到远程仓库

   ```bash
   git push origin main
   ```

   


## 将本地代码提交到远程仓库步骤

1. 将待更新文件添加到本地仓库

    ```bash
    git add .
    ```

    

2. 提交本地仓库（携带注释信息）

    ```bash
    # m message 提交信息
    git commit -m "注释信息"
    ```

    

3. 拉取所在分支的远程仓库

    ```bash
    git pull origin <分支名>
    ```

    

4. 将本地仓库推到远程仓库

    ```bash
    git push origin <分支名>
    ```

    
    

## 当出现冲突时该怎么做

> 适用场景：两台电脑A、B同时对一个文件进行了操作，A进行了提交，B知道A已经提交了，但是他不想放弃自己的更改，此时就需要解决这种冲突。

1. 首先将自己修改的文件提交到本地仓库

   ```bash
   # 先提交到
   git add .
   # z
   git commit -m ""
   ```

   

1. 拉取远程仓库



## 分支相关

### 创建分支

```bash
git branch <branchName>
```



### 切换分支

```bash
# 注意，此处branchName是本地分支名称
git checkout <branchName>
```



### 查看分支

```bash
# 查看本地分支,带*号说明是当前分支
git branch

# 查看本地+远程分支
git branch -a 
```

![image-20220210082728848](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220210082728.png)

### 重命名

```bash
git branch -m <oldBranchName> <newBranchName>
```

![image-20220210082706854](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220210082715.png)

### 同步远程仓库分支

> 在有些情况下，与远程仓库绑定之后，看不到远程分支，原因目前还不知道是为啥。
>
> 这时候，我们就需要使用命令同步远程分支

```bash
git fetch
```

![image-20220210082759583](https://masuo-github-image.oss-cn-beijing.aliyuncs.com/image/20220210082759.png)

### 合并分支

> 分支合并是我们经常使用的，也是很容易出问题的。
>
> 以下场景为我们新建了一个dev的分支，开发完之后，将其合并到main分支。

```bash
# 新建dev分支
git branch dev

# 拉取main分支的代码
git pull origin main

# 开发。。。

# 将dev分支合并到main分支

# 首先需要切换到main分支
git checkout main

# 合并dev
git merge dev

# 查看状态
git 
```

### 删除分支

```bash
# 删除本地分支
git branch -d <branchName>

# 删除远程分支
git branch -r -d origin/
```



# target

```markdown
框架，中间件，数据库，缓存，微服务，分布式这些原理都要有了解，可以说的不完整，但是不能不知道
常规的技术必须要储备扎实，不单单是应用，原理要了解，项目的话，准备一个微服务的，一个普通的，基本够用了，
操作系统，网络，机组，这部分属于基础素质，一般面试问不到，高级的才会问道，有时间最好能过一下，至于高薪，就是会点别人不会的东西，原理源码之类的，你觉得难的东西一定要学，你觉得难别人也觉得难
```


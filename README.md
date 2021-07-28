# Spring Logging with Custom AOP Annotation (Asynchronous)

This project has three main goals => Logging with AOP, processing asynchronous and thread safe.

Outputs are located at the bottom of the page.

### Why did I use Aspect? (Cross Cutting)
 
Cause I want to get log parameter when before  the method return and i want get log return type when after returning. (with timestamp)

I use AspectJ for that. (@Before and @AfterReturning)

### Why did I use Custom Annotation?

Cause If I want to ignore parameter or return type for log, I can add a value of annotation and DONE!

So I added ignoreparam and ignorereturn values for the annotation. Aspect is wired to the annotation.

### Asynchronous Programming

Maybe asynchronous logging is not good idea for this project but this is only demo. 

I used GitHub API for getting users. Threads are working in asynchronous. Also Aspect Logger doing the job in asynchronous.

# GitHub User Service

Logger and RestTemplate defined final for thread safe. Threads are getting in Thread Pool (threadPoolTaskExecutor).

![image](https://user-images.githubusercontent.com/38990648/127248696-6c44eba0-3f23-48e7-98d2-126d1b2610f8.png)


# Custom Annotation

This annotations target is method level and works runtime.

ignorereturn is default false and ignoreparam is default empty string.  

![image](https://user-images.githubusercontent.com/38990648/127249096-7aef89ea-ac6d-4cd0-bed2-6478d650a91a.png)


# Aspect

Defined pointcut for annotation. Getting parameter name and annotation with reflection and joinpoint. 

Then checking string in the service methods parameter and getting logs. Same thing for the return type.

![image](https://user-images.githubusercontent.com/38990648/127249315-b039af07-6cf9-4b33-9baf-261abc6dd5d0.png)

![image](https://user-images.githubusercontent.com/38990648/127249736-f7e6fe3a-6a74-4de4-b63f-fc77cf12f544.png)


# Outputs

This one has parameter name and return type. You can see the asynchronous threads. They are working diffrent order.

![async](https://user-images.githubusercontent.com/38990648/127252645-350c07d1-a0c3-430e-8504-a1c3983ed3fc.png)


### 1- Logging parameters before the method returns => Processes queue is 3,1,2,4
### 2- Getting user with GitHub API => Processes queue is 3,2,4,1
### 3- Logging return type after the metod returns => Processes queue is 4,2,3,1

This one has ignoreparam and ignorereturn values. Same processes are asynchronous again but log is not getting return type and parameter.

![image](https://user-images.githubusercontent.com/38990648/127251858-1156c437-b77e-4073-85d9-9d02517eb62f.png)

![image](https://user-images.githubusercontent.com/38990648/127252385-0ebbb24b-e722-4464-abad-678fdb5d20d4.png)


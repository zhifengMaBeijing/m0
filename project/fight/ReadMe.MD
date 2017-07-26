# what?

## 社交媒体网络

意见领袖
```

```

互相转帖

相互点赞

## 反恐
同行

同住


# how?

## 数据描述 (data)
```

```

## 模型描述 (model, dsl)
https://ms-ati.github.io/docile/

```


```

## 参数描述 (params)
```
模型参数
全局参数
```

# 离线模式


## 任务创建
创建、编辑和存储任务

## 任务调度

Job Service
同步/异步？
调度任务： 算法包的动态加载

## 任务

算法包 (java pacakge)




# 在线模式
领域建模语言

## ruby
## scala

* ideas borrows from below vedio
https://www.youtube.com/watch?v=yNCjZIqxP8E
```
DSL in actions: covers both internal & externals

break solution/problem down
understand the relationship

create domain abstraction
implement logic structures

```
* tech point?

```
1. execution plan
call compiled Scala code from Java is easy, how to evaluate a Scala expression on the fly?

2. execution of DSL, in time or compile and exec later?
how does one excute an dsl other than couple it with source code?

用户提交脚本，后台编译脚本。应用程序动态调用脚本。
应用程序classpath中有dsl实现库

In 2.10.0 we expose Scala reflection API, which among everything else includes a runtime compilation facility. 


3. Erros and exceptions

```

* DSL platforms

```
traditional way: compiler(include parser), debugger, ides

how does one integrate a dsl and a host language?

```


https://blog.scalac.io/2015/05/07/encog-dsl.html
```
The Palantir Script Helper allows advanced users to programmatically interact with the Palantir workspace through a Ruby scripting interface. Users can control the graph, map, 
heatmap, and browser, and write scripts that interact with their data in endless ways.


```

# english chinese
```
predicate

fight?

data mining with knowledge graphy

blur workbench	

```




# scala
FP + OOP

it is not a script language!

higher order functions, by-name parameters, implicits, traits,...
operator overloading

scala itself like a DSL!
10x-1000x slowdown

```
abstraction penalty
general purpose compiler

program generally, run specialized

```

# meta programming
programming to generate program


# expose a script interface?

## Groovy

## java script

## ruby? 


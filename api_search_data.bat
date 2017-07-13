links:
relationship
关系相对静态、稳定。如描述业务领域的实体关系图。

events
1）事件揭示实体间隐含关系。

事件具有随机性，很多没有固定关系的实体可以参予到一个事件。参与到一个时间中的实体可能由于偶然因素，也有可能是
隐含某种关系。挖掘这种关系具有一定价值。

2）

Elasticsearch 设计摘要

chapter 1. schema and schema configurations

文档内容构成：系统字段，数据字段
全文检索字段

facet字段：
用于分组，如doc_type, time range等。注意：首选可枚举字段, 
time range 分组用于实间轴分析
1）时间格式

2）分组粒度
支持分钟，小时，日，年。
默认为年
按默认粒度请求数据。当界面变换时间粒度时，重新申请。

3). doc type/ontology type and doc_type用于统计分析

schema更新
需要删除已有index然后重新生成新index


chapter 2 a simple model for unit testing

jack is a 10 years old boy, he lives in beijing, china, he visit website a lot. he works for a netease.
john is a 11 years old boy, he lives in tianjin, china, he visit website a lot. his hobby is reading books and swimming.
emily 45 years old, she is a lady, she lives in beijing, china, she does not work. she does not have hobby besided housework.


john and jack are friends, they study in the same high school.
emily is jack's Mom

on 2017-06-090, jack send an email to john, the email title is "hello" and the body contains a long story about 反恐.
on 2017-06-090, jack send an email to john, the email title is "hello" and the body contains a long story about 爱情观.
on 2017-06-090, emily called jack and asked him to come back home ealier, because it is jack's birthday tmr.

注意：如果事件存储在neo4j中，则需查询neo4j



chapter 3. es在业务中的应用
1.查询单个本体的属性
返回"jack"的属性
1)id为key的查询

2.批量查询几个本体的属性
返回"jack" 和"john"的属性
2)id列表为key的查询

3.查询和单个本体相关的事件/文档，如和“jack”相关的事件。
返回包含"jack"的事件
1）精确匹配：性能好，没有打分过程。对于text类型要求设置 "index" : "not_analyzed",否者不能返回正确结果
2）包含即可：全文检索
返回所有关于“jack”的事件，事件要包含“反恐”字段并且发生在最近一个月以内
3）组合查询：逻辑组合，与、或、非
4）时间段过滤：？

4.查询与多个本体都相关的事件/文档，如和“jack”和“john”相关的事件。
返回所有关于“jack”和“john”的事件，事件要发生在最近一个月以内
1)精确匹配
2）全文检索

chapter 4. es不适用什么业务？

chapter 5. API


1. API介绍
联合搜索和情报分析API（简称US&IA API）提供实体、事件、文档、关系的查询。应用程序可以参照API参考手册调用相关API（HTTP协议），返回结果支持JSON格式。

API命名规范

/v1/api/search/

/v1/api/admin/

1.1 Athorization
授权240

2. API参考手册

下面列出了所有API支持的方法

1）API名称 GET /search.json
描述

2）调用例子
http://api.ontology.org/v1/search.json

3）参数
  参数类型及取值区间

4）返回结果
描述 - 格式（JSON)


5）API状态
开发阶段
Alpha阶段
产品阶段

3. API状态
最新稳定版本是

4. 异常情况下的返回值
参考：
http://build.kiva.org/docs/conventions/errors
{
  "timestamp": 1497180870000,
  "status": 405,
  "error": "Method Not Allowed",
  "exception": "org.springframework.web.HttpRequestMethodNotSupportedException",
  "message": "Request method 'POST' not supported",
  "path": "/v1/api/get.json/jarr"
}

5. 返回数据处理
http://build.kiva.org/docs/data
1）标准对象

2）简单类型

3）数据速率和限流
n request/minutes
如果超过限制会报错：403 Forbidden (Rate Limit Exceeded)
Exponential backoff 算法帮我们探测服务器的限流策略

x-ratelimit-limit: 5000 per minute/per user?


4）代码示例和SDKs


n）多媒体数据
图像数据的存储和查询

视频数据
Youtube API

6. 流量控制
为避免返回大量数据，限制每次返回的记录条数。对于要求返回大量数据的请求，需要发送多次请求。


http://build.kiva.org/docs/conventions/paging

7. 权限控制

记录userID,APP_ID来统计每个请求源的访问量





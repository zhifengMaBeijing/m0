links:
relationship
��ϵ��Ծ�̬���ȶ���������ҵ�������ʵ���ϵͼ��

events
1���¼���ʾʵ���������ϵ��

�¼���������ԣ��ܶ�û�й̶���ϵ��ʵ����Բ��赽һ���¼������뵽һ��ʱ���е�ʵ���������żȻ���أ�Ҳ�п�����
����ĳ�ֹ�ϵ���ھ����ֹ�ϵ����һ����ֵ��

2��

Elasticsearch ���ժҪ

chapter 1. schema and schema configurations

�ĵ����ݹ��ɣ�ϵͳ�ֶΣ������ֶ�
ȫ�ļ����ֶ�

facet�ֶΣ�
���ڷ��飬��doc_type, time range�ȡ�ע�⣺��ѡ��ö���ֶ�, 
time range ��������ʵ�������
1��ʱ���ʽ

2����������
֧�ַ��ӣ�Сʱ���գ��ꡣ
Ĭ��Ϊ��
��Ĭ�������������ݡ�������任ʱ������ʱ���������롣

3). doc type/ontology type and doc_type����ͳ�Ʒ���

schema����
��Ҫɾ������indexȻ������������index


chapter 2 a simple model for unit testing

jack is a 10 years old boy, he lives in beijing, china, he visit website a lot. he works for a netease.
john is a 11 years old boy, he lives in tianjin, china, he visit website a lot. his hobby is reading books and swimming.
emily 45 years old, she is a lady, she lives in beijing, china, she does not work. she does not have hobby besided housework.


john and jack are friends, they study in the same high school.
emily is jack's Mom

on 2017-06-090, jack send an email to john, the email title is "hello" and the body contains a long story about ����.
on 2017-06-090, jack send an email to john, the email title is "hello" and the body contains a long story about �����.
on 2017-06-090, emily called jack and asked him to come back home ealier, because it is jack's birthday tmr.

ע�⣺����¼��洢��neo4j�У������ѯneo4j



chapter 3. es��ҵ���е�Ӧ��
1.��ѯ�������������
����"jack"������
1)idΪkey�Ĳ�ѯ

2.������ѯ�������������
����"jack" ��"john"������
2)id�б�Ϊkey�Ĳ�ѯ

3.��ѯ�͵���������ص��¼�/�ĵ�����͡�jack����ص��¼���
���ذ���"jack"���¼�
1����ȷƥ�䣺���ܺã�û�д�ֹ��̡�����text����Ҫ������ "index" : "not_analyzed",���߲��ܷ�����ȷ���
2���������ɣ�ȫ�ļ���
�������й��ڡ�jack�����¼����¼�Ҫ���������֡��ֶβ��ҷ��������һ��������
3����ϲ�ѯ���߼���ϣ��롢�򡢷�
4��ʱ��ι��ˣ���

4.��ѯ�������嶼��ص��¼�/�ĵ�����͡�jack���͡�john����ص��¼���
�������й��ڡ�jack���͡�john�����¼����¼�Ҫ���������һ��������
1)��ȷƥ��
2��ȫ�ļ���

chapter 4. es������ʲôҵ��

chapter 5. API


1. API����
�����������鱨����API�����US&IA API���ṩʵ�塢�¼����ĵ�����ϵ�Ĳ�ѯ��Ӧ�ó�����Բ���API�ο��ֲ�������API��HTTPЭ�飩�����ؽ��֧��JSON��ʽ��

API�����淶

/v1/api/search/

/v1/api/admin/

1.1 Athorization
��Ȩ240

2. API�ο��ֲ�

�����г�������API֧�ֵķ���

1��API���� GET /search.json
����

2����������
http://api.ontology.org/v1/search.json

3������
  �������ͼ�ȡֵ����

4�����ؽ��
���� - ��ʽ��JSON)


5��API״̬
�����׶�
Alpha�׶�
��Ʒ�׶�

3. API״̬
�����ȶ��汾��

4. �쳣����µķ���ֵ
�ο���
http://build.kiva.org/docs/conventions/errors
{
  "timestamp": 1497180870000,
  "status": 405,
  "error": "Method Not Allowed",
  "exception": "org.springframework.web.HttpRequestMethodNotSupportedException",
  "message": "Request method 'POST' not supported",
  "path": "/v1/api/get.json/jarr"
}

5. �������ݴ���
http://build.kiva.org/docs/data
1����׼����

2��������

3���������ʺ�����
n request/minutes
����������ƻᱨ��403 Forbidden (Rate Limit Exceeded)
Exponential backoff �㷨������̽�����������������

x-ratelimit-limit: 5000 per minute/per user?


4������ʾ����SDKs


n����ý������
ͼ�����ݵĴ洢�Ͳ�ѯ

��Ƶ����
Youtube API

6. ��������
Ϊ���ⷵ�ش������ݣ�����ÿ�η��صļ�¼����������Ҫ�󷵻ش������ݵ�������Ҫ���Ͷ������


http://build.kiva.org/docs/conventions/paging

7. Ȩ�޿���

��¼userID,APP_ID��ͳ��ÿ������Դ�ķ�����





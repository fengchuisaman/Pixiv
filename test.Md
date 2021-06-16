#hello

## 介绍
> 这个里面有的全部都是web页面的ajax请求，所以可能会接口失效，



##redis存储部分

> 1. 数据库分别存储，0号库设置关注人列表，1号库设置关注人的作品列表，2号库设置图片信息
> 2. 0号库 key值为：字符串  ${authId} ，value为 ${authName}
> 3. 1号库 key值为：字符串  ${authId} ，value为 List&lt;String:${picId}&gt;
> 4. 2号库 key值为：字符串  ${picId} &nbsp;&nbsp; ，value为 HashMap&lt;"picPopularPermanent":picPopularPermanent
   ,"author":author',"tag",tag> 固定长度,固定key值的3个Map
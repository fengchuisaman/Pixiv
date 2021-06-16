package cn.youngbear.service;

import cn.youngbear.pojo.Author;
import cn.youngbear.pojo.PicPopularPermanent;
import cn.youngbear.pojo.Tag;
import cn.youngbear.utils.util.RedisUtils;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

import java.util.*;

public class RedisService {

    private Gson gson = new Gson();
    /**
     * 保存 所有关注者Id Name 到redis
     * @param authMap Map<authId,authName>
     */
    public void saveFollowPerson(Map<String,String> authMap){
        Jedis jedis = RedisUtils.getJedis();
        jedis.select(0);
        for(String authId : authMap.keySet()){
            jedis.set(authId,authMap.get(authId));
        }
        jedis.close();
    }

    /**
     * 保存 所有 作者下的所有作品Id
     * @param picMap<authId,List<picList>>
     */
    public void saveAuthorPicId(Map<String, List<String>> picMap){
        Jedis jedis = RedisUtils.getJedis();
        jedis.select(1);
        for(String authId : picMap.keySet()){
            for(String picId:picMap.get(authId)){
                jedis.lpush(authId,picId);
            }
        }
        jedis.close();
    }

    /**
     * 保存所有图片信息
     * @param picPopularPermanent
     * @param author
     * @param tagList
     */
    public void savePicInfo(PicPopularPermanent picPopularPermanent, Author author, List<Tag> tagList){
        Jedis jedis = RedisUtils.getJedis();
        jedis.select(2);
        HashMap<String, String> redisMap = new HashMap<>();
        redisMap.put("picPopularPermanent",gson.toJson(picPopularPermanent));
        redisMap.put("author",gson.toJson(author));
        redisMap.put("tag",gson.toJson(tagList));
        jedis.hmset(picPopularPermanent.getPicId(),redisMap);
        jedis.close();
    }

    /**
     * 恢复 所有关注者Id Name
     * @param isDelete 取出后是否删除
     * @return Map<authId,authName>
     */
    public Map resumeFollowPerson(boolean isDelete){
        Map<String,String> resultMap = new HashMap<>();
        Jedis jedis = RedisUtils.getJedis();
        jedis.select(0);
        Set<String> authIdList = jedis.keys("*");
        for (String authId : authIdList) {
            String authName = jedis.get(authId);
            if(isDelete &&authName!=null && !"".equals(authName)) {jedis.del(authId);}
            resultMap.put(authId,authName);
        }
        jedis.close();
        return resultMap;
    }

    /**
     * 恢复 所有关注者者 的所有作品Id
     * @param isDelete 取出后是否删除
     * @return Map<authId,List<picId> >
     */
    public Map<String, List<String>>  resumeAuthorPicId(boolean isDelete){
        Map<String, List<String>> resultMap = new HashMap<>();
        Jedis jedis = RedisUtils.getJedis();
        jedis.select(1);
        Set<String> authIdList = jedis.keys("*");
        for (String authId : authIdList) {
            List picIdList = jedis.lrange(authId, 0, -1 );
            resultMap.put(authId,picIdList);
            if(isDelete && picIdList!=null && picIdList.size()>0){jedis.del(authId);}
        }
        jedis.close();
        return resultMap;
    }


    /**
     * 恢复 所有作品信息
     * @param isDelete 取出后是否删除
     * @return List<PicPopularPermanent,Author,Tag >
     */
    public List<Object>  resumePicInfo(boolean isDelete){
        List<Object> resultMap = new LinkedList<>();
        Jedis jedis = RedisUtils.getJedis();
        jedis.select(2);
        Set<String> authIdList = jedis.keys("*");
        for (String authId : authIdList) {
            List picInfoList = jedis.mget(authId);

            PicPopularPermanent picPopularPermanent = new PicPopularPermanent().getPic((cn.youngbear.pojo.PicPopularPermanent) JSON.parse(String.valueOf(picInfoList.get(0))));
            Author author = new Author().getAuthor((cn.youngbear.pojo.Author) JSON.parse(String.valueOf(picInfoList.get(1))));
            Tag tag = new Tag().getTag((cn.youngbear.pojo.Tag) JSON.parse(String.valueOf(picInfoList.get(2))));
            if(isDelete && picPopularPermanent!=null && author!=null && tag!=null){jedis.del(authId);}
        }
        jedis.close();
        return resultMap;
    }

}

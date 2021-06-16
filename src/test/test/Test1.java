import cn.youngbear.pojo.PicTag;
import cn.youngbear.pojo.Tag;
import cn.youngbear.service.MysqlService;
import cn.youngbear.service.RedisService;
import com.google.gson.Gson;
import org.junit.Test;

import java.util.*;

public class Test1 {

    @Test
    public void t1(){
        Map<String,String> authMap = new HashMap<>();
        authMap.put("1","hello");
        authMap.put("2","word");
        new RedisService().saveFollowPerson(authMap);
        Map<String, List<String>> picMap = new HashMap<>();
        picMap.put("1", Arrays.asList(new String[]{"122","123","12354"}));
        picMap.put("2", Arrays.asList(new String[]{"122","123","12354","111111111","33333333"}));
//        new RedisService().savePicMap(picMap);
    }

    @Test
    public void t2(){
        List list = new ArrayList();
        List list1 = new ArrayList();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        list1.add(4);
        list1.add(5);
        list1.add(6);
        list.addAll(list1.subList(1,6));
        System.out.println(list.get(0));
    }

    @Test
    public void t3(){
        ArrayList picList = new ArrayList(Arrays.asList("1","2","3"));
        System.out.println(picList.size());
        ArrayList currentList = new ArrayList(Arrays.asList("4","5","6"));
        picList = this.limitDownloadNumber(picList,currentList);
        System.out.println(picList);
    }

    @Test
    public void t4(){
        PicTag picTag = new PicTag();
        picTag.setPicId("1");
        ArrayList<Tag> arrayList = new ArrayList();

        for(int i=0;i<3;i++){
            Tag tag = new Tag();
            tag.setTagNameZh(java.lang.String.valueOf(i));
            tag.setTagName(java.lang.String.valueOf(i));
            arrayList.add(tag);
        }

        picTag.setTagList(arrayList);
        new MysqlService().insertPicTag(picTag);
    }

    @Test
    public void  testGson(){
        String a ="{en=\"精灵宝 可梦\"}";
        Gson gson = new Gson();
        Map list = gson.fromJson(a,Map.class);
        System.out.println(list);
    }

    public ArrayList<java.lang.String> limitDownloadNumber(ArrayList<java.lang.String> picList, List<java.lang.String> currentList){
        int downloadLimitForLove = Integer.parseInt("5");
        if((picList.size()+currentList.size()) > downloadLimitForLove){
            int currentListSize = downloadLimitForLove-picList.size();
            currentList =  currentList.subList(0,currentListSize);
        }
        picList.addAll(currentList);
        return picList;
    }
}

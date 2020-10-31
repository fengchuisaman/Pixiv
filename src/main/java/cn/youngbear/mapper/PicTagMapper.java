package cn.youngbear.mapper;
import cn.youngbear.pojo.PicTag;

import java.util.List;

public interface PicTagMapper {
    int insert(PicTag record);

    int insertSelective(PicTag record);

    int insertWithList(List<PicTag> list);

    int selctCountWithTagPic(PicTag picTag);

    int selecCountPicTag();
}
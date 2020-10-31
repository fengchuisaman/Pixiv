package cn.youngbear.mapper;

import cn.youngbear.pojo.Tag;

import java.util.List;

public interface TagMapper {
    int deleteByPrimaryKey(Long tagId);

    int insert(Tag record);

    int insertSelective(Tag record);
    int insertTags(List<Tag> list);

    Tag selectByPrimaryKey(Long tagId);
    Long selectTagIdByName(String tagName);

    int updateByPrimaryKeySelective(Tag record);

    int updateByPrimaryKey(Tag record);

    Long selectMaxTagId();
}
package cn.youngbear.mapper;

import cn.youngbear.pojo.PicPopularPermanent;

public interface PicPopularPermanentMapper {
    int deleteByPrimaryKey(String picId);

    int insert(PicPopularPermanent record);

    int insertSelective(PicPopularPermanent record);

    PicPopularPermanent selectByPrimaryKey(String picId);

    int updateByPrimaryKeySelective(PicPopularPermanent record);

    int updateByPrimaryKey(PicPopularPermanent record);
}
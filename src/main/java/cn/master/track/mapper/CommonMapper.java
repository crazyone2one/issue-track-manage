package cn.master.track.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Created by 11's papa on 2021/08/11
 * @version 1.0.0
 */
@Repository
public interface CommonMapper {
    /**
     * 执行sql
     *
     * @param sqlStr: sql
     * @return java.util.List<java.util.LinkedHashMap < java.lang.String, java.lang.Object>>
     */
    @Select("${sqlStr}")
    List<Map<String, Object>> findMapBySql(@Param(value = "sqlStr") String sqlStr);

    /**
     * 执行sql
     *
     * @param sqlStr: 自定义sql
     * @return java.util.List<java.lang.String>
     */
    @Select("${sqlStr}")
    List<String> findListBySql(@Param(value = "sqlStr") String sqlStr);

    /**
     * 查询数量
     *
     * @param sqlStr:
     * @return long
     */
    @Select("${sqlStr}")
    long findCountBySql(@Param(value = "sqlStr") String sqlStr);
}

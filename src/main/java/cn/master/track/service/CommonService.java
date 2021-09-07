package cn.master.track.service;

/**
 * @author Created by 11's papa on 2021/08/04
 * @version 1.0.0
 */
public interface CommonService {
    /**
     * 数据字典缓存
     */
    void initTypeGroup();

    void typeMap();

    void refreshTypeMap();

    void refreshTypeGroup();

    /**
     * 初始化当前年月
     */
    void monthListCurrentYear();

    /**
     * 初始化项目id数据
     */
    void initProjectId();

    /**
     * 刷新项目id数据
     *
     */
    void refreshProjectId();
}

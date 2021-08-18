package cn.master.track.config;

import cn.master.track.entity.TypeItem;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Created by 11's papa on 2021/08/04
 * @version 1.0.0
 */
public class Constants {

    public static Map<String, List<TypeItem>> allTypes = new LinkedHashMap<>();

    public static List<String> MONTH_LIST = new LinkedList<>();
    public static List<String> PROJECT_ID_LIST = new LinkedList<>();

    public static final String SEVERITY_LEVEL = "severity_level";
    public static final String OWNER_LIST = "owner_list";
    public static final String ISSUE_STATUS = "issue_status";
    public static final String JOB_STATUS = "job_status";

    public static final String BUG_LEVEL_1 = "1";
    public static final String BUG_LEVEL_2 = "2";
    public static final String BUG_LEVEL_3 = "3";
    public static final String BUG_LEVEL_4 = "4";
}

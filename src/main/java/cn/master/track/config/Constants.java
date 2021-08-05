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

    public static final List<String> monthList = new LinkedList<String>() {{
        add("2021-01");
        add("2021-02");
        add("2021-03");
        add("2021-04");
        add("2021-05");
        add("2021-06");
        add("2021-07");
        add("2021-08");
        add("2021-09");
        add("2021-10");
        add("2021-11");
        add("2021-12");
    }};

    public static final String SEVERITY_LEVEL = "severity_level";
    public static final String OWNER_LIST = "owner_list";
    public static final String ISSUE_STATUS = "issue_status";
}

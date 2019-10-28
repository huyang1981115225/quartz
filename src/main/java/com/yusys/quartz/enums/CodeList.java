package com.yusys.quartz.enums;

import java.util.Map;

/**
 * Created by huyang on 2019/10/28.
 */
public interface CodeList {

    Map<String, String> getMap(String bizType);

    Map<String, String> toMap();
}

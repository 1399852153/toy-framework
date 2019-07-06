package com.xiongyx.pattern;

import java.util.regex.Pattern;

/**
 * @author xiongyx
 * on 2019/7/6.
 */
public class Patterns {

    /**
     * #{}正则匹配
     * */
    public static Pattern param_pattern = Pattern.compile("#\\{([^\\{\\}]*)\\}");
}

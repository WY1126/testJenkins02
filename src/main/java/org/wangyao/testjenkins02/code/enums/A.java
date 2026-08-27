package org.wangyao.testjenkins02.code.enums;

import lombok.Getter;

/**
 *
 *
 * @author wangyao
 * @Date 2025/11/27 15:18
 *
 */
@Getter
public enum A implements CodeAndName {

    WANG("1", "wang"),
    ss("2", "yao"),
    ;
    private final String code;
    private final String name;

    A( String code, String name) {
        this.code = code;
        this.name = name;
    }

    public  static A getByCode(String code) {
        for (A a : A.values()) {
            if (a.getCode().equals(code)) {
                return a;
            }
        }
        return null;
    }

    public static A getByName(String name) {
        for (A a : A.values()) {
            if (a.getName().equals(name)) {
                return a;
            }
        }
        return null;
    }

}

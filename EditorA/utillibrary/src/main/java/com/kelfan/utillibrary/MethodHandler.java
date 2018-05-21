package com.kelfan.utillibrary;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class MethodHandler {

    public static Object run(Object object, String method, List args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method m = object.getClass().getMethod(method,List.class);
        return  m.invoke(null, args);
    }

}

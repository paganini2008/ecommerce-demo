package com.demo.ecommerce.utils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import lombok.experimental.UtilityClass;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;

/**
 * 
 * @Description: BeanCopyUtils
 * @Author: Fred Feng
 * @Date: 20/06/2025
 * @Version 1.0.0
 */
@UtilityClass
public class BeanCopyUtils {

    static {
        mapperFactory = new DefaultMapperFactory.Builder().build();
    }

    private static final MapperFactory mapperFactory;

    public <X, Y> void mapClass(Class<X> x, Class<Y> y, Map<String, String> fieldMapper) {
        mapClass(x, y, builder -> {
            if (MapUtils.isNotEmpty(fieldMapper)) {
                fieldMapper.entrySet().forEach(e -> {
                    builder.field(e.getKey(), e.getValue());
                });
            }
            builder.byDefault().register();
        });
    }

    public <X, Y> void mapClass(Class<X> x, Class<Y> y, Consumer<ClassMapBuilder<X, Y>> consumer) {
        ClassMapBuilder<X, Y> builder = mapperFactory.classMap(x, y);
        if (consumer != null) {
            consumer.accept(builder);
        }
    }

    public <S, T> T copyBean(S source, Class<T> resultClass) {
        if (source == null) {
            return null;
        }
        try {
            return mapperFactory.getMapperFacade().map(source, resultClass);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public <S, T> List<T> copyBeanList(List<S> sources, Class<T> resultClass) {
        if (CollectionUtils.isEmpty(sources)) {
            return Collections.emptyList();
        }
        try {
            return mapperFactory.getMapperFacade().mapAsList(sources, resultClass);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

}

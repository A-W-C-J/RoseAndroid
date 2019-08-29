package com.rose.android;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.alibaba.fastjson.JSON;

/**
 * Created by xiaohuabu on 17/10/10.
 */

@Route(path = "/service/json")
public class JsonServiceImpl implements SerializationService {

  @Override
  public void init(Context context) {
  }

  @Override
  public <T> T json2Object(String text, Class<T> clazz) {
    return JSON.parseObject(text, clazz);
  }

  @Override
  public String object2Json(Object instance) {
    return JSON.toJSONString(instance);
  }
}
